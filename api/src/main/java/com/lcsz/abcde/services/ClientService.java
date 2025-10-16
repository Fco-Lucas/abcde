package com.lcsz.abcde.services;

import com.lcsz.abcde.AppProperties;
import com.lcsz.abcde.dtos.auditLog.AuditLogCreateDto;
import com.lcsz.abcde.dtos.clients.ClientCreateDto;
import com.lcsz.abcde.dtos.clients.ClientResponseDto;
import com.lcsz.abcde.dtos.clients.ClientUpdateDto;
import com.lcsz.abcde.dtos.clients.ClientUpdatePasswordDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserResponseDto;
import com.lcsz.abcde.dtos.email.EmailCreateDto;
import com.lcsz.abcde.enums.auditLog.AuditAction;
import com.lcsz.abcde.enums.auditLog.AuditProgram;
import com.lcsz.abcde.enums.client.ClientStatus;
import com.lcsz.abcde.enums.email.EmailType;
import com.lcsz.abcde.exceptions.customExceptions.EntityExistsException;
import com.lcsz.abcde.exceptions.customExceptions.EntityNotFoundException;
import com.lcsz.abcde.mappers.ClientMapper;
import com.lcsz.abcde.models.Client;
import com.lcsz.abcde.repositorys.ClientRepository;
import com.lcsz.abcde.repositorys.projection.ClientProjection;
import com.lcsz.abcde.security.AuthenticatedUserProvider;
import com.lcsz.abcde.security.JwtToken;
import com.lcsz.abcde.security.JwtUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {
    private final ClientRepository repository;
    private final ClientUserService clientUserService;
    private final PasswordEncoder passwordEncoder;
    private final AuditLogService auditLogService;
    private final AuthenticatedUserProvider provider;
    private final AppProperties appProperties;
    private final EmailService emailService;

    ClientService(ClientRepository repository, ClientUserService clientUserService, PasswordEncoder passwordEncoder, AuditLogService auditLogService, AuthenticatedUserProvider provider, AppProperties appProperties, EmailService emailService) {
        this.repository = repository;
        this.clientUserService = clientUserService;
        this.passwordEncoder = passwordEncoder;
        this.auditLogService = auditLogService;
        this.provider = provider;
        this.appProperties = appProperties;
        this.emailService = emailService;
    }

    public String formatClientForLog(ClientResponseDto dto) {
        return String.format("{id='%s', nome='%s', cnpj='%s', email='%s', cliente_computex='%s', numero_do_contrato='%s', url_do_post='%s', quantidade_de_dias_que_a_imagem_ficará_salva='%s'}",
                dto.getId().toString(), dto.getName(), dto.getCnpj(), dto.getEmail(), dto.getCustomerComputex(), dto.getNumberContract(), dto.getUrlToPost(), dto.getImageActiveDays());
    }

    @Transactional(readOnly = true)
    private Optional<Client> findClientIfExists(String cnpj) {
        return repository.findByCnpj(cnpj);
    }

    @Transactional(readOnly = true)
    private Client findClientIfExistsByNumberContract(Integer numberContract) {
        return repository.findByNumberContract(numberContract).orElse(null);
    }

    public ClientResponseDto create(ClientCreateDto dto) {
        // Verifica se já existe um cliente com o CNPJ informado
        if (findClientIfExists(dto.getCnpj()).isPresent()) {
            throw new EntityExistsException(String.format("Cliente com cnpj '%s' já cadastrado no sistema", dto.getCnpj()));
        }

        // Verifica se já existe um cliente com o número do contrato informado
        if(dto.getCustomerComputex() == true) {
            if(dto.getNumberContract() == null) throw new RuntimeException("O campo 'numberContract' deve ser informado, pois se trata de um cliente COMPUTEX");
            Client existsByNumberContract = findClientIfExistsByNumberContract(dto.getNumberContract());
            if(existsByNumberContract != null) throw new EntityExistsException(String.format("Cliente com número do contrato '%s' já cadastrado no sistema", dto.getNumberContract()));
        }

        // Converte o DTO recebido para entidade
        Client client = new Client();
        client.setName(dto.getName());
        client.setCnpj(dto.getCnpj());
        client.setEmail(dto.getEmail());
        client.setCustomerComputex(dto.getCustomerComputex());
        client.setNumberContract(dto.getNumberContract());
        String encryptedPassword = dto.getPassword() != null ? passwordEncoder.encode(dto.getPassword()) : null;
        client.setPassword(encryptedPassword);
        client.setUrlToPost(dto.getUrlToPost());
        client.setStatus(ClientStatus.ACTIVE);

        // Define quantos dias a imagem ficará ativa
        String authUserRole = this.provider.getAuthenticatedUserRole();
        Integer imageActiveDays = authUserRole != null && authUserRole.equals("COMPUTEX")
                ? dto.getImageActiveDays()
                : 30; // valor padrão
        client.setImageActiveDays(imageActiveDays);

        // Salva o cliente
        Client savedClient = this.repository.save(client);

        // Mapeia para DTO de resposta
        ClientResponseDto responseDto = ClientMapper.toDto(savedClient);
        responseDto.setUsers(this.clientUserService.getUsersByClientId(client.getId()));

        // Log de criação do cliente
        String details = String.format(
                "Novo cliente cadastrado no sistema | Dados do cliente cadastrado: %s",
                this.formatClientForLog(responseDto)
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(
                AuditAction.CREATE,
                savedClient.getId(),
                AuditProgram.CLIENT,
                details
        );
        this.auditLogService.create(logDto);

        // Cria o e-mail para definição de senha (só grava no banco, ainda não envia)
        if (client.getPassword() == null && client.getEmail() != null && !client.getEmail().isBlank()) {
            createDefinePasswordEmail(savedClient);
        }

        // dispara o envio real do e-mail
        emailService.sendForClient(client);

        return responseDto;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void createDefinePasswordEmail(Client client) {
        // Gera um JWT válido por 10 minutos
        String cnpjComputex = "12302493000101";
        String role = client.getCnpj().equals(cnpjComputex) ? "COMPUTEX" : "CLIENT";

        JwtToken onDefinePasswordToken = JwtUtils.createToken(
                client.getId(),
                client.getId(),
                client.getCnpj(),
                role,
                Duration.ofMinutes(10)
        );

        // Cria o email de definição de senha
        Map<String, Object> templateFields = Map.of(
                "pre_header", "Esse e-mail será válido pelos próximos 10 minutos para definição da nova senha",
                "subject", "Defina sua nova senha",
                "user_name", client.getName(),
                "program_name", "ABCDE",
                "button_url", appProperties.getWebUrl() + "definePassword?key=" + onDefinePasswordToken.getToken()
        );

        EmailCreateDto emailCreateDto = new EmailCreateDto(
                client.getId(),
                null,
                EmailType.DEFINE_PASSWORD,
                "Defina sua nova senha",
                client.getEmail(),
                client.getName(),
                null,
                "d-0ea23c0ca74946cfb7a0437e430f7d82",
                templateFields,
                LocalDateTime.now()
        );

        emailService.create(emailCreateDto);
    }

    @Transactional(readOnly = true)
    public Page<ClientResponseDto> getAllPageable(Pageable pageable, String cnpj, ClientStatus filterStatus) {
        String cnpjParam = (cnpj == null || cnpj.isBlank()) ? null : "%" + cnpj + "%";

        Page<ClientProjection> clients = this.repository.findAllPageable(pageable, cnpjParam, filterStatus);
        return clients.map(client -> {
            List<ClientUserResponseDto> users = this.clientUserService.getUsersByClientId(client.getId());
            Client clientEntity = this.getClientById(client.getId());
            ClientResponseDto responseDto = ClientMapper.toDto(clientEntity);
            responseDto.setUsers(users);
            return responseDto;
        });
    }

    @Transactional(readOnly = true)
    public List<Client> getAll () {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Client getClientById(UUID id) {
        return this.repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Cliente com id '%s' não encontrado", id))
        );
    }

    @Transactional(readOnly = true)
    public ClientResponseDto getClientByIdDto(UUID id) {
        Client client = this.getClientById(id);
        ClientResponseDto clientResponse = ClientMapper.toDto(client);
        clientResponse.setUsers(this.clientUserService.getUsersByClientId(id));
        return clientResponse;
    }

    @Transactional(readOnly = false)
    public ClientResponseDto updateClient(
            UUID id,
            ClientUpdateDto dto
    ) {
        Client client = this.getClientById(id);

        if(dto.getName() != null) client.setName(dto.getName());
        if(dto.getCnpj() != null) {
            // Verifica se há outro cliente com o mesmo CNPJ
            Optional<Client> clientExists = findClientIfExists(dto.getCnpj());
            if (clientExists.isPresent() && clientExists.get().getId() != client.getId())
                throw new EntityExistsException(String.format("Cliente com cnpj '%s' já cadastrado no sistema", dto.getCnpj()));

            client.setCnpj(dto.getCnpj());
        }
        if(dto.getEmail() != null && !dto.getEmail().equals(client.getEmail())) client.setEmail(dto.getEmail());
        if(dto.getCustomerComputex() != null && !dto.getCustomerComputex().equals(client.getCustomerComputex())) {
            client.setCustomerComputex(dto.getCustomerComputex());

            // Se atualizou para false, atualiza o numero do contrato para null
            if(!dto.getCustomerComputex()) client.setNumberContract(null);

            // Se atualizou para true, obriga o cliente a informar um número do contrato
            if(dto.getCustomerComputex() && dto.getNumberContract() == null) throw new RuntimeException("O campo 'numberContract' deve ser informado, pois se trata de um cliente COMPUTEX");
        }
        // Só altera se o campo customerComputex for true
        if(client.getCustomerComputex() && dto.getNumberContract() != null && !dto.getNumberContract().equals(client.getNumberContract())) client.setNumberContract(dto.getNumberContract());
        if(dto.getUrlToPost() != null) client.setUrlToPost(dto.getUrlToPost());
        if(dto.getImageActiveDays() != null) {
            String authUserRole = this.provider.getAuthenticatedUserRole();
            if(authUserRole.equals("COMPUTEX")) client.setImageActiveDays(dto.getImageActiveDays());
        }

        Client updated = this.repository.save(client);

        String details = String.format(
            "Cliente atualizado com ID: %s | Novos dados -> Nome: %s | CNPJ: %s | E-mail: %s | Cliente COMPUTEX: %s | Número do contrato: %s | URL do POST: %s | Quantidade de dias que a imagem ficará salva: %s",
            updated.getId(), updated.getName(), updated.getCnpj(), updated.getEmail(), updated.getCustomerComputex(), updated.getNumberContract(), updated.getUrlToPost(), updated.getImageActiveDays()
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(AuditAction.UPDATE, AuditProgram.CLIENT, details);
        this.auditLogService.create(logDto);

        return ClientMapper.toDto(updated);
    }

    @Transactional(readOnly = false)
    public void deleteClient(UUID id) {
        // Inativa o cliente
        Client client = this.getClientById(id);
        client.setStatus(ClientStatus.INACTIVE);
        Client updated = this.repository.save(client);

        String details = String.format(
            "Cliente com ID: %s teve o status alterado para INATIVO (exclusão lógica).", updated.getId()
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(
                AuditAction.DELETE, AuditProgram.CLIENT, details
        );
        this.auditLogService.create(logDto);

        // Inativa todos os usuários deste cliente
        this.clientUserService.deleteAllUsersByClientId(id);
    }

    @Transactional(readOnly = false)
    public void updatePassword(UUID id, ClientUpdatePasswordDto dto) {
        String newPassword = dto.getNewPassword();
        String confirmNewPassword = dto.getConfirmNewPassword();

        if (!newPassword.equals(confirmNewPassword)) {
            throw new RuntimeException("Nova senha não é igual à confirmação da nova senha");
        }

        Client client = this.getClientById(id);

        client.setPassword(passwordEncoder.encode(newPassword));
        this.repository.save(client);

        String details = String.format(
            "Senha do cliente com ID: %s foi atualizada com sucesso.", client.getId()
        );

        AuditLogCreateDto logDto = new AuditLogCreateDto(
                AuditAction.UPDATE, AuditProgram.CLIENT, details
        );
        this.auditLogService.create(logDto);
    }

    public void restorePassword(UUID id) {
        Client client = this.getClientById(id);

        if (client.getEmail() == null || client.getEmail().isBlank()) throw new RuntimeException("O cliente informado não possui um e-mail definido, defina um e-mail válido e tente novamente");

        String details = String.format(
            "Senha do cliente com ID: %s foi restaurada para o valor padrão.", client.getId()
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(AuditAction.UPDATE, AuditProgram.CLIENT, details);
        this.auditLogService.create(logDto);

        // Cria o e-mail para definição de senha (só grava no banco, ainda não envia)
        createRestorePasswordEmail(client);

        // dispara o envio real do e-mail
        emailService.sendForClient(client);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void createRestorePasswordEmail(Client client) {
        // Gera um JWT válido por 10 minutos
        String cnpjComputex = "12302493000101";
        String role = client.getCnpj().equals(cnpjComputex) ? "COMPUTEX" : "CLIENT";

        JwtToken onDefinePasswordToken = JwtUtils.createToken(
                client.getId(),
                client.getId(),
                client.getCnpj(),
                role,
                Duration.ofMinutes(10)
        );

        // Cria o email de definição de senha
        Map<String, Object> templateFields = Map.of(
                "pre_header", "Esse e-mail será válido pelos próximos 10 minutos para definição da nova senha",
                "subject", "Esqueceu sua senha?",
                "user_name", client.getName(),
                "program_name", "ABCDE",
                "button_url", appProperties.getWebUrl() + "definePassword?key=" + onDefinePasswordToken.getToken()
        );

        EmailCreateDto emailCreateDto = new EmailCreateDto(
                client.getId(),
                null,
                EmailType.RESTORE_PASSWORD,
                "Esqueceu sua senha?",
                client.getEmail(),
                client.getName(),
                null,
                "d-0ea23c0ca74946cfb7a0437e430f7d82",
                templateFields,
                LocalDateTime.now()
        );

        emailService.create(emailCreateDto);
    }

    @Transactional
    public ClientResponseDto restore(UUID id) {
        // Inativa o cliente
        Client client = this.getClientById(id);
        client.setStatus(ClientStatus.ACTIVE);
        Client updated = this.repository.save(client);

        String details = String.format(
                "Cliente com ID: %s teve o status alterado para ATIVO.", updated.getId()
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(
                AuditAction.RESTORE, AuditProgram.CLIENT, details
        );
        this.auditLogService.create(logDto);

        // Ativa todos os usuários deste cliente
        this.clientUserService.restoreAllUsersByClientId(id);

        return ClientMapper.toDto(updated);
    }

    @Transactional(readOnly = true)
    public Client getByCnpj(String cnpj) {
        return this.repository.findByCnpj(cnpj).orElse(null);
    }

//    @Transactional(readOnly = true)
//    public Client getByCnpj(String cnpj, ClientStatus status) {
//        return this.findClientIfExists(cnpj, status).orElse(null);
//    }

    @Transactional(readOnly = true)
    public ClientResponseDto getByCnpjDto(String cnpj) {
        Client client = this.getByCnpj(cnpj);
        if (client == null) throw new EntityNotFoundException(String.format("Cliente com CNPJ: '%s' não encontrado", cnpj));
        return ClientMapper.toDto(client);
    }

    @Transactional(readOnly = true)
    public Client getByIdOrNull(UUID id) {
        return this.repository.findById(id).orElse(null);
    }
}

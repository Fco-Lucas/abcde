package com.lcsz.abcde.services;

import com.lcsz.abcde.dtos.auditLog.AuditLogCreateDto;
import com.lcsz.abcde.dtos.clients.ClientCreateDto;
import com.lcsz.abcde.dtos.clients.ClientResponseDto;
import com.lcsz.abcde.dtos.clients.ClientUpdateDto;
import com.lcsz.abcde.dtos.clients.ClientUpdatePasswordDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserResponseDto;
import com.lcsz.abcde.enums.auditLog.AuditAction;
import com.lcsz.abcde.enums.auditLog.AuditProgram;
import com.lcsz.abcde.enums.client.ClientStatus;
import com.lcsz.abcde.exceptions.customExceptions.EntityExistsException;
import com.lcsz.abcde.exceptions.customExceptions.EntityNotFoundException;
import com.lcsz.abcde.mappers.ClientMapper;
import com.lcsz.abcde.models.Client;
import com.lcsz.abcde.repositorys.ClientRepository;
import com.lcsz.abcde.repositorys.projection.ClientProjection;
import com.lcsz.abcde.security.AuthenticatedUserProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {
    private final ClientRepository repository;
    private final ClientUserService clientUserService;
    private final PasswordEncoder passwordEncoder;
    private final AuditLogService auditLogService;
    private final AuthenticatedUserProvider provider;

    ClientService(ClientRepository repository, ClientUserService clientUserService, PasswordEncoder passwordEncoder, AuditLogService auditLogService, AuthenticatedUserProvider provider) {
        this.repository = repository;
        this.clientUserService = clientUserService;
        this.passwordEncoder = passwordEncoder;
        this.auditLogService = auditLogService;
        this.provider = provider;
    }

    public String formatClientForLog(ClientResponseDto dto) {
        return String.format("{id='%s', nome='%s', cnpj='%s'}",
                dto.getId().toString(), dto.getName(), dto.getCnpj());
    }

    @Transactional(readOnly = true)
    private Optional<Client> findClientIfExists(String cnpj, ClientStatus status) {
        return repository.findByCnpjAndStatus(cnpj, status);
    }

    @Transactional(readOnly = false)
    public ClientResponseDto create(ClientCreateDto dto) {
        // Verifica se já existe um cliente com o CNPJ informado
        if (findClientIfExists(dto.getCnpj(), ClientStatus.ACTIVE).isPresent())
            throw new EntityExistsException(String.format("Cliente com cnpj '%s' já cadastrado no sistema", dto.getCnpj()));

        // Converte o dto recebido para entidade
        Client client = new Client();
        client.setName(dto.getName());
        client.setCnpj(dto.getCnpj());
        client.setPassword(passwordEncoder.encode(dto.getPassword()));
        client.setUrlToPost(dto.getUrlToPost());
        client.setStatus(ClientStatus.ACTIVE);

        // Seta os dias em que a imagem ficará ativa de acordo com o cargo
        String authUserRole = this.provider.getAuthenticatedUserRole();
        Integer imageActiveDays = authUserRole != null && authUserRole.equals("COMPUTEX") ? dto.getImageActiveDays() : 30; // 30 dias valor padrão
        client.setImageActiveDays(imageActiveDays);

        Client savedClient = this.repository.save(client);

        ClientResponseDto responseDto = ClientMapper.toDto(savedClient);
        responseDto.setUsers(this.clientUserService.getUsersByClientId(client.getId()));

        // Log
        String details = String.format(
                "Novo cliente cadastrado no sistema | Dados do cliente cadastrado: %s",
                this.formatClientForLog(responseDto)
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(AuditAction.CREATE, savedClient.getId(), AuditProgram.CLIENT, details);
        this.auditLogService.create(logDto);

        return responseDto;
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
            Optional<Client> clientExists = findClientIfExists(dto.getCnpj(), ClientStatus.ACTIVE);
            if (clientExists.isPresent() && clientExists.get().getId() != client.getId())
                throw new EntityExistsException(String.format("Cliente com cnpj '%s' já cadastrado no sistema", dto.getCnpj()));

            client.setCnpj(dto.getCnpj());
        }
        if(dto.getUrlToPost() != null) client.setUrlToPost(dto.getUrlToPost());
        if(dto.getImageActiveDays() != null) {
            String authUserRole = this.provider.getAuthenticatedUserRole();
            if(authUserRole.equals("COMPUTEX")) client.setImageActiveDays(dto.getImageActiveDays());
        }

        Client updated = this.repository.save(client);

        String details = String.format(
            "Cliente atualizado com ID: %s | Novos dados -> Nome: %s | CNPJ: %s",
            updated.getId(), updated.getName(), updated.getCnpj()
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(AuditAction.UPDATE, AuditProgram.CLIENT, details);
        this.auditLogService.create(logDto);

        return ClientMapper.toDto(updated);
    }

    @Transactional(readOnly = false)
    public void deleteClient(UUID id) {
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
    }

    @Transactional(readOnly = false)
    public void updatePassword(UUID id, ClientUpdatePasswordDto dto) {
        String currentPassword = dto.getCurrentPassword();
        String newPassword = dto.getNewPassword();
        String confirmNewPassword = dto.getConfirmNewPassword();

        if (!newPassword.equals(confirmNewPassword)) {
            throw new RuntimeException("Nova senha não é igual à confirmação da nova senha");
        }

        Client client = this.getClientById(id);

        if (!passwordEncoder.matches(currentPassword, client.getPassword())) {
            throw new RuntimeException("Senha atual inválida");
        }

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

    @Transactional(readOnly = false)
    public void restorePassword(UUID id) {
        Client client = this.getClientById(id);
        String newPassword = passwordEncoder.encode("abcdefgh");
        client.setPassword(newPassword);
        this.repository.save(client);

        String details = String.format(
            "Senha do cliente com ID: %s foi restaurada para o valor padrão.", client.getId()
        );

        AuditLogCreateDto logDto = new AuditLogCreateDto(
            AuditAction.UPDATE, AuditProgram.CLIENT, details
        );
        this.auditLogService.create(logDto);
    }

    @Transactional(readOnly = true)
    public Client getByCnpj(String cnpj, ClientStatus status) {
        return this.findClientIfExists(cnpj, status).orElse(null);
    }

    @Transactional(readOnly = true)
    public ClientResponseDto getByCnpjDto(String cnpj) {
        Client client = this.getByCnpj(cnpj, ClientStatus.ACTIVE);
        if (client == null) throw new EntityNotFoundException(String.format("Cliente com CNPJ: '%s' não encontrado", cnpj));
        return ClientMapper.toDto(client);
    }

    @Transactional(readOnly = true)
    public Client getByIdOrNull(UUID id) {
        return this.repository.findById(id).orElse(null);
    }
}

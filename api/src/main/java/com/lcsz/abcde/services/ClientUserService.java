package com.lcsz.abcde.services;

import com.lcsz.abcde.AppProperties;
import com.lcsz.abcde.dtos.auditLog.AuditLogCreateDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserCreateDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserResponseDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserUpdateDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserUpdatePasswordDto;
import com.lcsz.abcde.dtos.email.EmailCreateDto;
import com.lcsz.abcde.dtos.permissions.PermissionResponseDto;
import com.lcsz.abcde.enums.auditLog.AuditAction;
import com.lcsz.abcde.enums.auditLog.AuditProgram;
import com.lcsz.abcde.enums.clientUser.ClientUserStatus;
import com.lcsz.abcde.enums.email.EmailType;
import com.lcsz.abcde.exceptions.customExceptions.EntityExistsException;
import com.lcsz.abcde.exceptions.customExceptions.EntityNotFoundException;
import com.lcsz.abcde.mappers.ClientUserMapper;
import com.lcsz.abcde.models.Client;
import com.lcsz.abcde.models.ClientUser;
import com.lcsz.abcde.repositorys.ClientUserRepository;
import com.lcsz.abcde.repositorys.projection.ClientUserProjection;
import com.lcsz.abcde.security.JwtToken;
import com.lcsz.abcde.security.JwtUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientUserService {
    private final ClientUserRepository repository;
    private final ClientService clientService;
    private final PermissionService permissionService;
    private final PasswordEncoder passwordEncoder;
    private final AuditLogService auditLogService;
    private final AppProperties appProperties;
    private final EmailService emailService;

    ClientUserService(
        ClientUserRepository repository,
        @Lazy ClientService clientService,
        PermissionService permissionService,
        PasswordEncoder passwordEncoder,
        AuditLogService auditLogService,
        AppProperties appProperties,
        EmailService emailService
    ) {
        this.repository = repository;
        this.clientService = clientService;
        this.permissionService = permissionService;
        this.passwordEncoder = passwordEncoder;
        this.auditLogService = auditLogService;
        this.appProperties = appProperties;
        this.emailService = emailService;
    }

    public String formatClientUserForLog(ClientUserResponseDto dto) {
        return String.format("{id='%s', nome='%s', email='%s', permission='%s'}",
                dto.getId().toString(), dto.getName(), dto.getEmail(), dto.getPermission());
    }

    @Transactional(readOnly = true)
    private Optional<ClientUser> findClientUserIfExists(String email) {
        return this.repository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Client getClientOfClientUser(UUID idClient) {
        return clientService.getClientById(idClient);
    }

    public ClientUserResponseDto create(UUID idClient, ClientUserCreateDto dto) {
        // Verifica se existe o clientId informado, se não existir lança exceção
        Client client = getClientOfClientUser(idClient);

        // Verifica se já existe um usuário do cliente com email informado
        if(this.findClientUserIfExists(dto.getEmail()).isPresent())
            throw new EntityExistsException(String.format("Usuário com email '%s' já cadastrado no sistema", dto.getEmail()));

        // Converte o dto recebido para entidade
        ClientUser clientUser = new ClientUser();
        clientUser.setClientId(idClient);
        clientUser.setName(dto.getName());
        clientUser.setEmail(dto.getEmail());
        String encryptedPassword = dto.getPassword() != null ? passwordEncoder.encode(dto.getPassword()) : null;
        clientUser.setPassword(encryptedPassword);
        clientUser.setPermission(dto.getPermission());
        clientUser.setStatus(ClientUserStatus.ACTIVE);

        ClientUser saved = this.repository.save(clientUser);

        PermissionResponseDto permissions = this.permissionService.getPermissionByIdDto(saved.getPermission());

        ClientUserResponseDto responseDto = ClientUserMapper.toDto(saved);
        responseDto.setPermission(permissions);

        // Log
        String details = String.format(
            "Novo usuário cadastrado no sistema para o cliente '%s' | Dados do usuário cadastrado: %s",
            responseDto.getClientId(), this.formatClientUserForLog(responseDto)
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(AuditAction.CREATE, AuditProgram.CLIENT_USER, details);
        this.auditLogService.create(logDto);

        // Cria o e-mail para definição de senha (só grava no banco, ainda não envia)
        if (clientUser.getPassword() == null && clientUser.getEmail() != null && !clientUser.getEmail().isBlank()) {
            createDefinePasswordEmail(saved, client);
        }

        // dispara o envio real do e-mail
        emailService.sendForClient(client);

        return responseDto;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void createDefinePasswordEmail(ClientUser clientUser, Client client) {
        // Gera um JWT válido por 10 minutos
        String cnpjComputex = "12302493000101";
        String role = client.getCnpj().equals(cnpjComputex) ? "COMPUTEX" : "CLIENT";
        JwtToken onDefinePasswordToken = JwtUtils.createToken(
                clientUser.getId(),
                clientUser.getEmail(),
                role,
                Duration.ofMinutes(10)
        );

        // Cria o email de definição de senha
        Map<String, Object> templateFields = Map.of(
                "pre_header", "Esse e-mail será válido pelos próximos 10 minutos para definição da nova senha",
                "subject", "Defina sua nova senha",
                "user_name", clientUser.getName(),
                "program_name", "ABCDE",
                "button_url", appProperties.getWebUrl() + "definePassword?key=" + onDefinePasswordToken.getToken()
        );

        EmailCreateDto emailCreateDto = new EmailCreateDto(
                client.getId(),
                clientUser.getId(),
                EmailType.DEFINE_PASSWORD,
                "Defina sua nova senha",
                clientUser.getEmail(),
                clientUser.getName(),
                null,
                "d-0ea23c0ca74946cfb7a0437e430f7d82",
                templateFields,
                LocalDateTime.now()
        );

        emailService.create(emailCreateDto);
    }

    public Page<ClientUserResponseDto> getAllPageable(
            Pageable pageable,
            UUID clientId,
            String name,
            String email,
            ClientUserStatus status
    ) {
        String nameParam = (name == null || name.isBlank()) ? null : "%" + name + "%";
        String emailParam = (email == null || email.isBlank()) ? null : "%" + email + "%";

        Page<ClientUserProjection> clientUsers = this.repository.findAllPageable(pageable, clientId, nameParam, emailParam, status);

        return clientUsers.map(clientUser -> {
            ClientUser entity = this.getById(clientUser.getId());
            ClientUserResponseDto responseDto = ClientUserMapper.toDto(entity);
            PermissionResponseDto permission = this.permissionService.getPermissionByIdDto(clientUser.getPermission());
            responseDto.setPermission(permission);
            return responseDto;
        });
    }

    @Transactional(readOnly = true)
    public ClientUser getById(UUID id) {
        return this.repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com id '%s' não encontrado", id))
        );
    }

    @Transactional(readOnly = true)
    public ClientUserResponseDto getByIdDto(UUID id) {
        ClientUser clientUser = this.getById(id);

        PermissionResponseDto permission = this.permissionService.getPermissionByIdDto(clientUser.getPermission());

        ClientUserResponseDto responseDto = ClientUserMapper.toDto(clientUser);
        responseDto.setPermission(permission);

        return responseDto;
    }

    @Transactional(readOnly = false)
    public ClientUserResponseDto update(UUID id, ClientUserUpdateDto dto) {
        ClientUser clientUser = this.getById(id);

        if(dto.getClientId() != null) clientUser.setClientId(dto.getClientId());
        if(dto.getName() != null) clientUser.setName(dto.getName());
        if(dto.getEmail() != null) {
            Optional<ClientUser> clientUserExits = this.findClientUserIfExists(dto.getEmail());
            if(clientUserExits.isPresent() && clientUserExits.get().getId() != clientUser.getId())
                throw new EntityExistsException(String.format("Usuário com e-mail '%s' já existente", dto.getEmail()));

            clientUser.setEmail(dto.getEmail());
        }
        if(dto.getPermission() != null) clientUser.setPermission(dto.getPermission());

        ClientUser updated = this.repository.save(clientUser);

        String details = String.format(
                "Usuário atualizado com ID: %s | Novos dados -> Nome: %s | E-mail: %s",
                updated.getId(), updated.getName(), updated.getEmail()
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(AuditAction.UPDATE, AuditProgram.CLIENT_USER, details);
        this.auditLogService.create(logDto);

        return ClientUserMapper.toDto(updated);
    }

    @Transactional(readOnly = false)
    public void delete(UUID id) {
        ClientUser clientUser = this.getById(id);
        clientUser.setStatus(ClientUserStatus.INACTIVE);
        ClientUser updated = this.repository.save(clientUser);

        String details = String.format(
            "Usuário com ID: %s teve o status alterado para INATIVO (exclusão lógica).", updated.getId()
        );

        AuditLogCreateDto logDto = new AuditLogCreateDto(
                AuditAction.DELETE, AuditProgram.CLIENT_USER, details
        );
        this.auditLogService.create(logDto);
    }

    @Transactional
    public void deleteAllUsersByClientId (UUID clientId) {
        List<ClientUserResponseDto> users = getUsersByClientId(clientId);

        for (ClientUserResponseDto user : users) {
            this.delete(user.getId());
        }
    }

    @Transactional(readOnly = false)
    public void updatePassword(UUID id, ClientUserUpdatePasswordDto dto) {
        String newPassword = dto.getNewPassword();
        String confirmNewPassword = dto.getConfirmNewPassword();

        if(!newPassword.equals(confirmNewPassword)) throw new RuntimeException("Nova senha não é igual a confirmação da nova senha");

        ClientUser clientUser = this.getById(id);
        clientUser.setPassword(passwordEncoder.encode(newPassword));

        this.repository.save(clientUser);

        String details = String.format(
                "Senha do usuário com ID: %s foi atualizada com sucesso.", clientUser.getId()
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(
                AuditAction.UPDATE, AuditProgram.CLIENT_USER, details
        );
        this.auditLogService.create(logDto);
    }

    public void restorePassword(UUID idClient, UUID idClientUser) {
        // Verifica se o cliente desse usuário existe
        Client client = getClientOfClientUser(idClient);

        ClientUser clientUser = this.getById(idClientUser);

        String details = String.format(
                "Senha do usuário com ID: %s foi restaurada para o valor padrão.", clientUser.getId()
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(AuditAction.UPDATE, AuditProgram.CLIENT_USER, details);
        this.auditLogService.create(logDto);

        // Cria o e-mail para definição de senha (só grava no banco, ainda não envia)
        createRestorePasswordEmail(client, clientUser);

        // dispara o envio real do e-mail
        emailService.sendForClient(client);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void createRestorePasswordEmail(Client client, ClientUser clientUser) {
        // Gera um JWT válido por 10 minutos
        String cnpjComputex = "12302493000101";
        String role = client.getCnpj().equals(cnpjComputex) ? "COMPUTEX" : "CLIENT";
        JwtToken onDefinePasswordToken = JwtUtils.createToken(
                clientUser.getId(),
                clientUser.getEmail(),
                role,
                Duration.ofMinutes(10)
        );

        // Cria o email de definição de senha
        Map<String, Object> templateFields = Map.of(
                "pre_header", "Esse e-mail será válido pelos próximos 10 minutos para definição da nova senha",
                "subject", "Esqueceu sua senha?",
                "user_name", clientUser.getName(),
                "program_name", "ABCDE",
                "button_url", appProperties.getWebUrl() + "definePassword?key=" + onDefinePasswordToken.getToken()
        );

        EmailCreateDto emailCreateDto = new EmailCreateDto(
                client.getId(),
                clientUser.getId(),
                EmailType.RESTORE_PASSWORD,
                "Esqueceu sua senha?",
                clientUser.getEmail(),
                clientUser.getName(),
                null,
                "d-0ea23c0ca74946cfb7a0437e430f7d82",
                templateFields,
                LocalDateTime.now()
        );

        emailService.create(emailCreateDto);
    }

    @Transactional
    public ClientUserResponseDto restore(UUID id) {
        ClientUser clientUser = this.getById(id);
        clientUser.setStatus(ClientUserStatus.ACTIVE);
        ClientUser updated = this.repository.save(clientUser);

        String details = String.format(
                "Usuário com ID: %s teve o status alterado para ATIVO.", updated.getId()
        );

        AuditLogCreateDto logDto = new AuditLogCreateDto(
                AuditAction.RESTORE, AuditProgram.CLIENT_USER, details
        );
        this.auditLogService.create(logDto);

        return ClientUserMapper.toDto(updated);
    }

    @Transactional
    public void restoreAllUsersByClientId (UUID clientId) {
        List<ClientUserResponseDto> users = getUsersByClientId(clientId);

        for (ClientUserResponseDto user : users) {
            this.restore(user.getId());
        }
    }

    @Transactional(readOnly = true)
    public ClientUser getByEmail(String email) {
        return this.repository.findByEmail(email).orElse(null);
    }

//    @Transactional(readOnly = true)
//    public ClientUser getByEmail(String email, ClientUserStatus status) {
//        return this.findClientUserIfExists(email, status).orElse(null);
//    }

    @Transactional(readOnly = true)
    public List<ClientUserResponseDto> getUsersByClientId(UUID clientId) {
        List<ClientUser> users = this.repository.findAllByClientIdAndStatus(clientId, ClientUserStatus.ACTIVE);

        return users.stream()
            .map(user -> {
                PermissionResponseDto permissionDto = this.permissionService.getPermissionByIdDto(user.getPermission());

                ClientUserResponseDto dto = ClientUserMapper.toDto(user);
                dto.setPermission(permissionDto);

                return dto;
            })
            .toList();
    }

    @Transactional(readOnly = true)
    public ClientUser getByIdOrNull(UUID id) {
        return this.repository.findById(id).orElse(null);
    }
}

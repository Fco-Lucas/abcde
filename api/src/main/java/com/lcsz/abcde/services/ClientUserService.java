package com.lcsz.abcde.services;

import com.lcsz.abcde.dtos.clients_users.ClientUserCreateDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserResponseDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserUpdateDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserUpdatePasswordDto;
import com.lcsz.abcde.dtos.permissions.PermissionResponseDto;
import com.lcsz.abcde.enums.clientUser.ClientUserStatus;
import com.lcsz.abcde.exceptions.customExceptions.EntityExistsException;
import com.lcsz.abcde.exceptions.customExceptions.EntityNotFoundException;
import com.lcsz.abcde.mappers.ClientUserMapper;
import com.lcsz.abcde.models.ClientUser;
import com.lcsz.abcde.repositorys.ClientUserRepository;
import com.lcsz.abcde.repositorys.projection.ClientUserProjection;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientUserService {
    private final ClientUserRepository repository;
    private final ClientService clientService;
    private final PermissionService permissionService;
    private final PasswordEncoder passwordEncoder;

    ClientUserService(ClientUserRepository repository, @Lazy ClientService clientService, PermissionService permissionService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.clientService = clientService;
        this.permissionService = permissionService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    private Optional<ClientUser> findClientUserIfExists(String email, ClientUserStatus status) {
        return this.repository.findByEmailAndStatus(email, status);
    }

    @Transactional(readOnly = false)
    public ClientUserResponseDto create(ClientUserCreateDto dto) {
        // Verifica se já existe um usuário do cliente com email informado
        if(this.findClientUserIfExists(dto.getEmail(), ClientUserStatus.ACTIVE).isPresent())
            throw new EntityExistsException(String.format("Usuário com email '%s' já cadastrado no sistema", dto.getEmail()));

        // Verifica se existe o clientId informado, se não existir lança exceção
        this.clientService.getClientById(dto.getClientId());

        // Converte o dto recebido para entidade
        ClientUser clientUser = new ClientUser();
        clientUser.setClientId(dto.getClientId());
        clientUser.setName(dto.getName());
        clientUser.setEmail(dto.getEmail());
        clientUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        clientUser.setPermission(dto.getPermission());
        clientUser.setStatus(ClientUserStatus.ACTIVE);

        ClientUser saved = this.repository.save(clientUser);

        PermissionResponseDto permissions = this.permissionService.getPermissionByIdDto(saved.getPermission());

        ClientUserResponseDto responseDto = ClientUserMapper.toDto(saved);
        responseDto.setPermission(permissions);

        return responseDto;
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
    public void update(UUID id, ClientUserUpdateDto dto) {
        ClientUser clientUser = this.getById(id);

        if(dto.getClientId() != null) clientUser.setClientId(dto.getClientId());
        if(dto.getName() != null) clientUser.setName(dto.getName());
        if(dto.getEmail() != null) {
            Optional<ClientUser> clientUserExits = this.findClientUserIfExists(dto.getEmail(), ClientUserStatus.ACTIVE);
            if(clientUserExits.isPresent() && clientUserExits.get().getId() != clientUser.getId())
                throw new EntityExistsException(String.format("Usuário com e-mail '%s' já existente", dto.getEmail()));

            clientUser.setEmail(dto.getEmail());
        }
        if(dto.getPermission() != null) clientUser.setPermission(dto.getPermission());

        this.repository.save(clientUser);
    }

    @Transactional(readOnly = false)
    public void delete(UUID id) {
        ClientUser clientUser = this.getById(id);
        clientUser.setStatus(ClientUserStatus.INACTIVE);
        this.repository.save(clientUser);
    }

    @Transactional(readOnly = false)
    public void updatePassword(UUID id, ClientUserUpdatePasswordDto dto) {
        String currentPassword = dto.getCurrentPassword();
        String newPassword = dto.getNewPassword();
        String confirmNewPassword = dto.getConfirmNewPassword();

        if(!newPassword.equals(confirmNewPassword))
            throw new RuntimeException("Nova senha não é igual a confirmação da nova senha");

        ClientUser clientUser = this.getById(id);
        if(!passwordEncoder.matches(currentPassword, clientUser.getPassword()))
            throw new RuntimeException("Senha atual inválida");
        clientUser.setPassword(passwordEncoder.encode(newPassword));

        this.repository.save(clientUser);
    }

    @Transactional(readOnly = false)
    public void restorePassword(UUID id) {
        ClientUser clientUser = this.getById(id);
        String newPassword = passwordEncoder.encode("abcdefgh");
        clientUser.setPassword(newPassword);
        this.repository.save(clientUser);
    }

    @Transactional(readOnly = true)
    public ClientUser getByEmail(String email, ClientUserStatus status) {
        return this.findClientUserIfExists(email, status).orElse(null);
    }

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

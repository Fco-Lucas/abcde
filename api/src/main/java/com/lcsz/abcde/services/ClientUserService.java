package com.lcsz.abcde.services;

import com.lcsz.abcde.dtos.clients_users.ClientUserCreateDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserResponseDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserUpdateDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserUpdatePasswordDto;
import com.lcsz.abcde.enums.clientUser.ClientUserStatus;
import com.lcsz.abcde.exceptions.customExceptions.EntityExistsException;
import com.lcsz.abcde.exceptions.customExceptions.EntityNotFoundException;
import com.lcsz.abcde.mappers.ClientUserMapper;
import com.lcsz.abcde.models.ClientUser;
import com.lcsz.abcde.repositorys.ClientUserRepository;
import com.lcsz.abcde.repositorys.projection.ClientUserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class ClientUserService {
    private final ClientUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    ClientUserService(ClientUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
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

        // Converte o dto recebido para entidade
        ClientUser clientUser = new ClientUser();
        clientUser.setClientId(dto.getClientId());
        clientUser.setName(dto.getName());
        clientUser.setEmail(dto.getEmail());
        clientUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        clientUser.setPermission(dto.getPermission());
        clientUser.setStatus(ClientUserStatus.ACTIVE);

        ClientUser saved = this.repository.save(clientUser);

        return ClientUserMapper.toDto(saved);
    }

    public Page<ClientUserProjection> getAllPageable(
            Pageable pageable,
            String filterName,
            String filterEmail
    ) {
        return this.repository.findAllPageable(pageable, filterName, filterEmail);
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
        return ClientUserMapper.toDto(clientUser);
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
}

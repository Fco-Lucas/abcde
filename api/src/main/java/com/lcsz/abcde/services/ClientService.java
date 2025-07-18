package com.lcsz.abcde.services;

import com.lcsz.abcde.dtos.clients.ClientCreateDto;
import com.lcsz.abcde.dtos.clients.ClientResponseDto;
import com.lcsz.abcde.dtos.clients.ClientUpdateDto;
import com.lcsz.abcde.dtos.clients.ClientUpdatePasswordDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserResponseDto;
import com.lcsz.abcde.enums.client.ClientStatus;
import com.lcsz.abcde.exceptions.customExceptions.EntityExistsException;
import com.lcsz.abcde.exceptions.customExceptions.EntityNotFoundException;
import com.lcsz.abcde.mappers.ClientMapper;
import com.lcsz.abcde.models.Client;
import com.lcsz.abcde.repositorys.ClientRepository;
import com.lcsz.abcde.repositorys.projection.ClientProjection;
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

    ClientService(ClientRepository repository, ClientUserService clientUserService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.clientUserService = clientUserService;
        this.passwordEncoder = passwordEncoder;
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
        client.setStatus(ClientStatus.ACTIVE);

        Client savedClient = this.repository.save(client);

        ClientResponseDto clientResponse = ClientMapper.toDto(savedClient);
        clientResponse.setUsers(this.clientUserService.getUsersByClientId(client.getId()));

        return clientResponse;
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
    public void updateClient(
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

        this.repository.save(client);
    }

    @Transactional(readOnly = false)
    public void deleteClient(UUID id) {
        Client client = this.getClientById(id);
        client.setStatus(ClientStatus.INACTIVE);
        this.repository.save(client);
    }

    @Transactional(readOnly = false)
    public void updatePassword(UUID id, ClientUpdatePasswordDto dto) {
        String currentPassword = dto.getCurrentPassword();
        String newPassword = dto.getNewPassword();
        String confirmNewPassword = dto.getConfirmNewPassword();

        if(!newPassword.equals(confirmNewPassword))
            throw new RuntimeException("Nova senha não é igual a confirmação da nova senha");

        Client client = this.getClientById(id);

        if(!passwordEncoder.matches(currentPassword, client.getPassword()))
            throw new RuntimeException("Senha atual inválida");
        client.setPassword(passwordEncoder.encode(newPassword));

        this.repository.save(client);
    }

    @Transactional(readOnly = false)
    public void restorePassword(UUID id) {
        Client client = this.getClientById(id);
        String newPassword = passwordEncoder.encode("abcdefgh");
        client.setPassword(newPassword);
        this.repository.save(client);
    }

    @Transactional(readOnly = true)
    public Client getByCnpj(String cnpj, ClientStatus status) {
        return this.findClientIfExists(cnpj, status).orElse(null);
    }
}

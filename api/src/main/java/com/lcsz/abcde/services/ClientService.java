package com.lcsz.abcde.services;

import com.lcsz.abcde.dtos.clients.ClientCreateDto;
import com.lcsz.abcde.dtos.clients.ClientResponseDto;
import com.lcsz.abcde.dtos.clients.ClientUpdateDto;
import com.lcsz.abcde.dtos.clients.ClientUpdatePasswordDto;
import com.lcsz.abcde.enums.client.ClientStatus;
import com.lcsz.abcde.exceptions.customExceptions.EntityExistsException;
import com.lcsz.abcde.exceptions.customExceptions.EntityNotFoundException;
import com.lcsz.abcde.mappers.ClientMapper;
import com.lcsz.abcde.models.Client;
import com.lcsz.abcde.repositorys.ClientRepository;
import com.lcsz.abcde.repositorys.projection.ClientProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {
    private final ClientRepository repository;

    ClientService(ClientRepository repository) {
        this.repository = repository;
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
        client.setPassword(dto.getPassword());
        client.setStatus(ClientStatus.ACTIVE);

        Client savedClient = this.repository.save(client);

        return ClientMapper.toDto(savedClient);
    }

    @Transactional(readOnly = true)
    public Page<ClientProjection> getAllPageable(Pageable pageable, String filterCnpj, ClientStatus filterStatus) {
        return this.repository.findAllPageable(pageable, filterCnpj, filterStatus);
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
        return ClientMapper.toDto(client);
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
        if(!currentPassword.equals(client.getPassword()))
            throw new RuntimeException("Senha atual inválida");

        client.setPassword(newPassword);

        // if(!passwordEncoder.matches(currentPassword, appUser.getPassword())) throw new RuntimeException("Senha atual inválida");
        // appUser.setPassword(passwordEncoder.encode(newPassword));

        this.repository.save(client);
    }

    @Transactional(readOnly = false)
    public void restorePassword(UUID id) {
        Client client = this.getClientById(id);
        // String newPassword = passwordEncoder.encode("123456");
        String newPassword = "abcdefgh";
        client.setPassword(newPassword);
        this.repository.save(client);
    }
}

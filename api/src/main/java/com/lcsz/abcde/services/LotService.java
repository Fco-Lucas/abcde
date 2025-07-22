package com.lcsz.abcde.services;

import com.lcsz.abcde.dtos.lot.LotCreateDto;
import com.lcsz.abcde.dtos.lot.LotResponseDto;
import com.lcsz.abcde.dtos.lot.LotUpdateDto;
import com.lcsz.abcde.enums.lot.LotStatus;
import com.lcsz.abcde.exceptions.customExceptions.EntityExistsException;
import com.lcsz.abcde.exceptions.customExceptions.EntityNotFoundException;
import com.lcsz.abcde.mappers.LotMapper;
import com.lcsz.abcde.models.Client;
import com.lcsz.abcde.models.ClientUser;
import com.lcsz.abcde.models.Lot;
import com.lcsz.abcde.repositorys.LotRepository;
import com.lcsz.abcde.repositorys.projection.LotProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LotService {

    private final LotRepository lotRepository;
    private final ClientService clientService;
    private final ClientUserService clientUserService;

    LotService(LotRepository lotRepository, ClientService clientService, ClientUserService clientUserService) {
        this.lotRepository = lotRepository;
        this.clientService = clientService;
        this.clientUserService = clientUserService;
    }

    @Transactional(readOnly = false)
    public LotResponseDto create(LotCreateDto dto) {
        Client client;

        ClientUser clientUser = this.clientUserService.getByIdOrNull(dto.getUserId());
        client = clientUser == null ? this.clientService.getByIdOrNull(dto.getUserId()) : this.clientService.getByIdOrNull(clientUser.getClientId());

        if(client == null) throw new EntityNotFoundException("Cliente não encontrado com base no id do usuário informado");

        // Verifica se já existe um lote criado com o nome informado pro cliente
        Optional<Lot> lotExistis = this.lotRepository.findByNameAndUserId(dto.getName(), client.getCnpj());
        if(lotExistis.isPresent())
            throw new EntityExistsException(String.format("Lote com nome '%s' já cadastrado", dto.getName()));

        Lot lot = new Lot();
        lot.setUserId(dto.getUserId());
        lot.setUserCnpj(client.getCnpj());
        lot.setName(dto.getName());
        lot.setStatus(LotStatus.INCOMPLETED);

        Lot saved = this.lotRepository.save(lot);

        return LotMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public Page<LotProjection> getAllPageable(
            Pageable pageable,
            String name,
            LotStatus status
    ) {
        String nameParam = (name == null || name.isBlank()) ? null : "%" + name + "%";
        Page<LotProjection> lots = this.lotRepository.findAllPageable(pageable, nameParam, status);
        return lots;
    }

    @Transactional(readOnly = true)
    public Lot getLotById(Long id) {
        return this.lotRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Lote com id '%s' não encontrado", id))
        );
    }

    @Transactional(readOnly = true)
    public LotResponseDto getLotByIdDto(Long id) {
        Lot lot = this.getLotById(id);
        return LotMapper.toDto(lot);
    }

    @Transactional(readOnly = false)
    public void update(Long lotId, LotUpdateDto dto) {
        Lot lot = this.getLotById(lotId);

        if(dto.getName() != null) lot.setName(dto.getName());
        if(dto.getStatus() != null) lot.setStatus(dto.getStatus());

        this.lotRepository.save(lot);
    }

    @Transactional(readOnly = false)
    public void delete(Long lotId) {
        Lot lot = this.getLotById(lotId);
        lot.setStatus(LotStatus.DELETED);
        this.lotRepository.save(lot);
    }
}

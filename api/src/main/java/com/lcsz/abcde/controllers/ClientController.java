package com.lcsz.abcde.controllers;

import com.lcsz.abcde.dtos.PageableDto;
import com.lcsz.abcde.dtos.clients.ClientCreateDto;
import com.lcsz.abcde.dtos.clients.ClientResponseDto;
import com.lcsz.abcde.dtos.clients.ClientUpdateDto;
import com.lcsz.abcde.dtos.clients.ClientUpdatePasswordDto;
import com.lcsz.abcde.enums.client.ClientStatus;
import com.lcsz.abcde.mappers.PageableMapper;
import com.lcsz.abcde.repositorys.projection.ClientProjection;
import com.lcsz.abcde.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/clients")
public class ClientController {
    private final ClientService service;

    ClientController(ClientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ClientResponseDto> createClient(@RequestBody @Valid ClientCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.create(dto));
    }

    @GetMapping
    public ResponseEntity<PageableDto> getClients(
            @RequestParam(required = false) String cnpj,
            @RequestParam(required = false) ClientStatus status,
            Pageable pageable
    ) {
        Page<ClientProjection> clients = this.service.getAllPageable(pageable, cnpj, status);
        return ResponseEntity.ok(PageableMapper.toDto(clients));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getClientByIdDto(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateClient(@PathVariable UUID id, @RequestBody @Valid ClientUpdateDto dto) {
        this.service.updateClient(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
        this.service.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/updatePassword/{id}")
    public ResponseEntity<Void> updatePasswordClient(@PathVariable UUID id, @RequestBody @Valid ClientUpdatePasswordDto dto) {
        this.service.updatePassword(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/restorePassword/{id}")
    public ResponseEntity<Void> restorePasswordClient(@PathVariable UUID id) {
        this.service.restorePassword(id);
        return ResponseEntity.noContent().build();
    }
}

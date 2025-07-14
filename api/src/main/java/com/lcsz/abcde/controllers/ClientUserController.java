package com.lcsz.abcde.controllers;

import com.lcsz.abcde.dtos.PageableDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserCreateDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserResponseDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserUpdateDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserUpdatePasswordDto;
import com.lcsz.abcde.mappers.PageableMapper;
import com.lcsz.abcde.repositorys.projection.ClientUserProjection;
import com.lcsz.abcde.services.ClientUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "ClientUser Controller", description = "Contém todas as operações relacionadas aos recursos dos usuários dos clientes do ABCDE")
@RestController
@RequestMapping("api/v1/clientsUsers")
public class ClientUserController {
    private final ClientUserService service;

    ClientUserController(ClientUserService service) {
        this.service = service;
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<ClientUserResponseDto> createClientUser(@RequestBody @Valid ClientUserCreateDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.create(dto));
    }

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PageableDto> getAllClientsUserPageable(
        Pageable pageable,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String email
    ) {
        Page<ClientUserProjection> clientUsers = this.service.getAllPageable(pageable, name, email);
        return ResponseEntity.status(HttpStatus.OK).body(PageableMapper.toDto(clientUsers));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ClientUserResponseDto> getClientUserById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getByIdDto(id));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENT') or #id == principal.id")
    public ResponseEntity<Void> updateClientUser(@PathVariable UUID id, @RequestBody @Valid ClientUserUpdateDto dto) {
        this.service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<Void> deleteClientUser(@PathVariable UUID id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/updatePassword/{id}")
    @PreAuthorize("hasAuthority('CLIENT') or #id == principal.id")
    public ResponseEntity<Void> updatePasswordClientUser(@PathVariable UUID id, @RequestBody @Valid ClientUserUpdatePasswordDto dto) {
        this.service.updatePassword(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/restorePassword/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<Void> restorePasswordClientUser(@PathVariable UUID id) {
        this.service.restorePassword(id);
        return ResponseEntity.noContent().build();
    }
}

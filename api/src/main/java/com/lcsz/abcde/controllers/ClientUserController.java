package com.lcsz.abcde.controllers;

import com.lcsz.abcde.dtos.PageableDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserCreateDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserResponseDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserUpdateDto;
import com.lcsz.abcde.dtos.clients_users.ClientUserUpdatePasswordDto;
import com.lcsz.abcde.enums.clientUser.ClientUserStatus;
import com.lcsz.abcde.mappers.PageableMapper;
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

@Tag(name = "ClientUser Controller", description = "Contém todas as operações relacionadas aos usuários de clientes do sistema ABCDE")
@RestController
@RequestMapping("${api.basepath}clients/{clientId}/users")
public class ClientUserController {

    private final ClientUserService service;

    public ClientUserController(ClientUserService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('COMPUTEX', 'CLIENT')")
    public ResponseEntity<ClientUserResponseDto> createClientUser(
            @PathVariable UUID clientId,
            @RequestBody @Valid ClientUserCreateDto dto
    ) {
        dto.setClientId(clientId);
        ClientUserResponseDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PageableDto> getAllClientsUserPageable(
            Pageable pageable,
            @PathVariable UUID clientId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) ClientUserStatus status
    ) {
        Page<ClientUserResponseDto> page = service.getAllPageable(pageable, clientId, name, email, status);
        return ResponseEntity.ok(PageableMapper.toDto(page));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ClientUserResponseDto> getClientUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getByIdDto(id));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('COMPUTEX', 'CLIENT') or #id == principal.id")
    public ResponseEntity<ClientUserResponseDto> updateClientUser(
            @PathVariable UUID id,
            @RequestBody @Valid ClientUserUpdateDto dto
    ) {
        ClientUserResponseDto responseDto = service.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('COMPUTEX', 'CLIENT')")
    public ResponseEntity<Void> deleteClientUser(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/restore")
    @PreAuthorize("hasAnyAuthority('COMPUTEX', 'CLIENT')")
    public ResponseEntity<ClientUserResponseDto> restoreClientUser(@PathVariable UUID id) {
        ClientUserResponseDto responseDto = service.restore(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping("/updatePassword/{id}")
    @PreAuthorize("hasAuthority('COMPUTEX') or #id == principal.id")
    public ResponseEntity<Void> updatePasswordClientUser(
            @PathVariable UUID id,
            @RequestBody @Valid ClientUserUpdatePasswordDto dto
    ) {
        service.updatePassword(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/restorePassword/{id}")
    @PreAuthorize("hasAnyAuthority('COMPUTEX', 'CLIENT')")
    public ResponseEntity<Void> restorePasswordClientUser(@PathVariable UUID id) {
        service.restorePassword(id);
        return ResponseEntity.noContent().build();
    }
}

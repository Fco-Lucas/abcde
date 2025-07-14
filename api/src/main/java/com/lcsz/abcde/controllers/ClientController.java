package com.lcsz.abcde.controllers;

import com.lcsz.abcde.dtos.PageableDto;
import com.lcsz.abcde.dtos.clients.ClientCreateDto;
import com.lcsz.abcde.dtos.clients.ClientResponseDto;
import com.lcsz.abcde.dtos.clients.ClientUpdateDto;
import com.lcsz.abcde.dtos.clients.ClientUpdatePasswordDto;
import com.lcsz.abcde.enums.client.ClientStatus;
import com.lcsz.abcde.exceptions.ExceptionMessage;
import com.lcsz.abcde.mappers.PageableMapper;
import com.lcsz.abcde.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Client Controller", description = "Contém todas as operações relacionadas aos recursos dos clientes do ABCDE")
@RestController
@RequestMapping("api/v1/clients")
public class ClientController {
    private final ClientService service;

    ClientController(ClientService service) {
        this.service = service;
    }

    @Operation(
            summary = "Cadastrar cliente",
            description = "Recurso para cadastrar novo cliente no sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Cliente cadastrado com sucesso",
                            content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ClientResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Cliente com CNPJ informado já cadastrado no sistema",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class)
                            )
                    )
            }
    )
    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<ClientResponseDto> createClient(@RequestBody @Valid ClientCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.create(dto));
    }

    @Operation(
            summary = "Buscar clientes com paginação",
            description = "Recurso para buscar os clientes cadastrados no sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cliente resgatados com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PageableDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Cliente com CNPJ informado já cadastrado no sistema",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Usuário sem permissão para acessar este recurso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class)
                            )
                    ),
            }
    )
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PageableDto> getAllClientsPageable(
            @RequestParam(required = false) String cnpj,
            @RequestParam(required = false) ClientStatus status,
            Pageable pageable
    ) {
        Page<ClientResponseDto> clients = this.service.getAllPageable(pageable, cnpj, status);
        return ResponseEntity.ok(PageableMapper.toDto(clients));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getClientByIdDto(id));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<Void> updateClient(@PathVariable UUID id, @RequestBody @Valid ClientUpdateDto dto) {
        this.service.updateClient(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
        this.service.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/updatePassword/{id}")
    @PreAuthorize("hasAuthority('CLIENT') or #id == principal.id")
    public ResponseEntity<Void> updatePasswordClient(@PathVariable UUID id, @RequestBody @Valid ClientUpdatePasswordDto dto) {
        this.service.updatePassword(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/restorePassword/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<Void> restorePasswordClient(@PathVariable UUID id) {
        this.service.restorePassword(id);
        return ResponseEntity.noContent().build();
    }
}

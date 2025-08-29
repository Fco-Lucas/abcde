package com.lcsz.abcde.controllers;

import com.lcsz.abcde.dtos.permissions.PermissionResponseDto;
import com.lcsz.abcde.exceptions.ExceptionMessage;
import com.lcsz.abcde.security.AuthenticatedUserProvider;
import com.lcsz.abcde.services.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Permission Controller", description = "Contém todas as operações relacionadas aos recursos de permissões do ABCDE")
@RestController
@RequestMapping("${api.basepath}permissions")
public class PermissionController {
    private final PermissionService service;
    private final AuthenticatedUserProvider provider;

    PermissionController(PermissionService service, AuthenticatedUserProvider provider) {
        this.service = service;
        this.provider = provider;
    }

    @Operation(
            summary = "Buscar permissões",
            description = "Recurso para buscar as permissões cadastrados no sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Permissões resgatadas com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PermissionResponseDto.class)
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
    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PermissionResponseDto>> getAllPermissions() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPermissions());
    }

    @Operation(
            summary = "Buscar permissão com base no seu ID",
            description = "Recurso para buscar a permissão com base no seu ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Permissão resgatada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PermissionResponseDto.class)
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
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PermissionResponseDto> getPermissionById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPermissionByIdDto(id));
    }

    @GetMapping("/getUserPermissions")
    @PreAuthorize("hasAnyAuthority('COMPUTEX', 'CLIENT') or #userId == authentication.principal.id")
    public ResponseEntity<PermissionResponseDto> getUserPermissions(
            @RequestParam(required = true, name = "userId") UUID userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.provider.getAuthenticatedUserPermissions(userId));
    }
}

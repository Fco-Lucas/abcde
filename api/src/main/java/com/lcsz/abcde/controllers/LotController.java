package com.lcsz.abcde.controllers;

import com.lcsz.abcde.dtos.PageableDto;
import com.lcsz.abcde.dtos.lot.LotCreateDto;
import com.lcsz.abcde.dtos.lot.LotResponseDto;
import com.lcsz.abcde.dtos.lot.LotUpdateDto;
import com.lcsz.abcde.dtos.permissions.PermissionResponseDto;
import com.lcsz.abcde.enums.lot.LotStatus;
import com.lcsz.abcde.mappers.PageableMapper;
import com.lcsz.abcde.repositorys.projection.LotProjection;
import com.lcsz.abcde.security.AuthenticatedUserProvider;
import com.lcsz.abcde.services.LotService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/lots")
public class LotController {
    private final LotService lotService;
    private final AuthenticatedUserProvider userProvider;
    public LotController(LotService lotService, AuthenticatedUserProvider userProvider) {
        this.lotService = lotService;
        this.userProvider = userProvider;
    }

    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LotResponseDto> createLot(@RequestBody @Valid LotCreateDto dto) {
        // Verifica se o usuário possui permissão para criar lote
        PermissionResponseDto userPermission = this.userProvider.getAuthenticatedUserPermissions();
        if(!userPermission.getUpload_files()) throw new RuntimeException("Usuário sem autorização para criar lotes");

        LotResponseDto responseDto = this.lotService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PageableDto> getAllLotsUserPageable(
        Pageable pageable,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String clientUser,
        @RequestParam(required = false) String client,
        @RequestParam(required = false) LotStatus status
    ) {
        // Verifica se o usuário possui permissão para visualizar lotes
        PermissionResponseDto userPermission = this.userProvider.getAuthenticatedUserPermissions();
        if(!userPermission.getRead_files()) throw new RuntimeException("Usuário sem autorização para visualizar lotes");

        UUID authUserId = this.userProvider.getAuthenticatedUserId();

        Page<LotResponseDto> lots = this.lotService.getAllPageable(pageable, name, clientUser, client, status, authUserId);

        return ResponseEntity.status(HttpStatus.OK).body(PageableMapper.toDto(lots));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LotResponseDto> getLotById(
            @PathVariable Long id
    ) {
        // Verifica se o usuário possui permissão para visualizar lotes
        PermissionResponseDto userPermission = this.userProvider.getAuthenticatedUserPermissions();
        if(!userPermission.getRead_files()) throw new RuntimeException("Usuário sem autorização para visualizar lotes");

        return ResponseEntity.status(HttpStatus.OK).body(this.lotService.getLotByIdDto(id));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> updateLot(@PathVariable Long id, @RequestBody @Valid LotUpdateDto dto) {
        // Verifica se o usuário possui permissão para atualizar lote
        PermissionResponseDto userPermission = this.userProvider.getAuthenticatedUserPermissions();
        if(!userPermission.getUpload_files()) throw new RuntimeException("Usuário sem autorização para atualizar lotes");

        this.lotService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteLot(
            @PathVariable Long id
    ) {
        this.lotService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/download-txt")
    public ResponseEntity<byte[]> downloadTxt(@PathVariable Long id) {
        // Verifica se o usuário possui permissão para visualizar lotes
        PermissionResponseDto userPermission = this.userProvider.getAuthenticatedUserPermissions();
        if(!userPermission.getRead_files()) throw new RuntimeException("Usuário sem autorização para visualizar lotes");

        byte[] contentBytes = this.lotService.generateTxt(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"gabarito-lote-" + id + ".txt\"")
                .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                .header(HttpHeaders.PRAGMA, "no-cache")
                .header(HttpHeaders.EXPIRES, "0")
                .contentType(MediaType.TEXT_PLAIN)
                .body(contentBytes);
    }
}

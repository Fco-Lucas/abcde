package com.lcsz.abcde.controllers;

import com.lcsz.abcde.dtos.PageableDto;
import com.lcsz.abcde.dtos.lotImage.LotImageHashResponseDto;
import com.lcsz.abcde.dtos.lotImage.LotImagePageableResponseDto;
import com.lcsz.abcde.dtos.lotImage.LotImageResponseDto;
import com.lcsz.abcde.dtos.lotImage.LotImageUpdateQuestionDto;
import com.lcsz.abcde.dtos.permissions.PermissionResponseDto;
import com.lcsz.abcde.mappers.PageableMapper;
import com.lcsz.abcde.security.AuthenticatedUserProvider;
import com.lcsz.abcde.services.LotImageService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/lots/{lotId}/images")
public class LotImageController {
    private final LotImageService lotImageService;
    private final AuthenticatedUserProvider userProvider;

    LotImageController(LotImageService lotImageService, AuthenticatedUserProvider userProvider) {
        this.lotImageService = lotImageService;
        this.userProvider = userProvider;
    }

    @PostMapping("/process_image")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LotImageResponseDto> processImage(
            @RequestParam("file") MultipartFile file,
            @PathVariable("lotId") Long lotId
    ) {
        // Verifica se o usuário possui permissão para processar lote
        PermissionResponseDto userPermission = this.userProvider.getAuthenticatedUserPermissions();
        if(!userPermission.getUpload_files()) throw new RuntimeException("Usuário sem autorização para processar imagens");

        try {
            LotImageResponseDto lotImage = this.lotImageService.processImage(file, lotId);
            return ResponseEntity.status(HttpStatus.CREATED).body(lotImage);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PageableDto> getAllLotImages(
            Pageable pageable,
            @PathVariable Long lotId,
            @RequestParam(required = false) String student
    ) {
        // Verifica se o usuário possui permissão para visualizar imagens
        PermissionResponseDto userPermission = this.userProvider.getAuthenticatedUserPermissions();
        if(!userPermission.getRead_files()) throw new RuntimeException("Usuário sem autorização para visualizar imagens");

        Page<LotImagePageableResponseDto> lotImages = this.lotImageService.getAllPageable(pageable, lotId, student);
        return ResponseEntity.status(HttpStatus.OK).body(PageableMapper.toDto(lotImages));
    }

    @GetMapping("/{lotImageId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LotImageResponseDto> getLotImageById(
            @PathVariable Long lotImageId
    ) {
        // Verifica se o usuário possui permissão para visualizar imagens
        PermissionResponseDto userPermission = this.userProvider.getAuthenticatedUserPermissions();
        if(!userPermission.getRead_files()) throw new RuntimeException("Usuário sem autorização para visualizar imagens");

        return ResponseEntity.status(HttpStatus.OK).body(this.lotImageService.getByIdDto(lotImageId));
    }

    @PatchMapping("/{lotImageId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> updateLotImageQuestions(
            @PathVariable Long lotImageId,
            @RequestBody @Valid List<LotImageUpdateQuestionDto> dto
    ) {
        // Verifica se o usuário possui permissão para atualizar questões
        PermissionResponseDto userPermission = this.userProvider.getAuthenticatedUserPermissions();

        if(!userPermission.getUpload_files()) throw new RuntimeException("Usuário sem autorização para atualizar as questões");
        this.lotImageService.updateImageQuestions(lotImageId, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{lotImageId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteLotImage(
            @PathVariable Long lotImageId
    ) {
        // Verifica se o usuário possui permissão para excluir imagens
        PermissionResponseDto userPermission = this.userProvider.getAuthenticatedUserPermissions();
        if(!userPermission.getUpload_files()) throw new RuntimeException("Usuário sem autorização para excluir imagens");

        this.lotImageService.delete(lotImageId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getHashs")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<LotImageHashResponseDto>> getHashs(
            @PathVariable("lotId") Long lotId
    ) {
        List<LotImageHashResponseDto> responseDto = this.lotImageService.getAllImagesLotHash(lotId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/download-all")
    @PreAuthorize("isAuthenticated()")
    public void downloadAll(
            @PathVariable("lotId") Long lotId,
            HttpServletResponse response
    ) throws IOException {
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=gabaritos_" + lotId + ".zip");
        lotImageService.downloadAllImages(lotId, response.getOutputStream());
    }
}

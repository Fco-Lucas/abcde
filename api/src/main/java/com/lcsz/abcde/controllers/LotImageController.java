package com.lcsz.abcde.controllers;

import com.lcsz.abcde.dtos.PageableDto;
import com.lcsz.abcde.dtos.lotImage.LotImageResponseDto;
import com.lcsz.abcde.mappers.PageableMapper;
import com.lcsz.abcde.services.LotImageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/lots/{lotId}/images")
public class LotImageController {
    private final LotImageService lotImageService;

    LotImageController(LotImageService lotImageService) {
        this.lotImageService = lotImageService;
    }

    @PostMapping("/process_image")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LotImageResponseDto> processImage(
            @RequestParam("file") MultipartFile file,
            @PathVariable("lotId") Long lotId
    ) {
        try {
            LotImageResponseDto lotImage = this.lotImageService.createImage(file, lotId);
            return ResponseEntity.status(HttpStatus.CREATED).body(lotImage);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PageableDto> getAllLotImages(
            Pageable pageable,
            @PathVariable Long lotId
    ) {
        Page<LotImageResponseDto> lotImages = this.lotImageService.getAllPageable(pageable, lotId);
        return ResponseEntity.status(HttpStatus.OK).body(PageableMapper.toDto(lotImages));
    }

    @GetMapping("/{lotImageId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LotImageResponseDto> getLotImageById(
            @PathVariable Long lotImageId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.lotImageService.getByIdDto(lotImageId));
    }
}

package com.lcsz.abcde.controllers;

import com.lcsz.abcde.dtos.PageableDto;
import com.lcsz.abcde.dtos.lot.LotCreateDto;
import com.lcsz.abcde.dtos.lot.LotResponseDto;
import com.lcsz.abcde.dtos.lot.LotUpdateDto;
import com.lcsz.abcde.enums.lot.LotStatus;
import com.lcsz.abcde.mappers.PageableMapper;
import com.lcsz.abcde.repositorys.projection.LotProjection;
import com.lcsz.abcde.services.LotService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/lots")
public class LotController {
    public LotService lotService;
    public LotController(LotService lotService) {
        this.lotService = lotService;
    }

    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LotResponseDto> createLot(@RequestBody @Valid LotCreateDto dto) {
        LotResponseDto responseDto = this.lotService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PageableDto> getAllLotsUserPageable(
        Pageable pageable,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) LotStatus status
    ) {
        Page<LotResponseDto> lots = this.lotService.getAllPageable(pageable, name, status);
        return ResponseEntity.status(HttpStatus.OK).body(PageableMapper.toDto(lots));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LotResponseDto> getLotById(
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.lotService.getLotByIdDto(id));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> updateLot(@PathVariable Long id, @RequestBody @Valid LotUpdateDto dto) {
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
}

package com.lcsz.abcde.controllers;

import com.lcsz.abcde.dtos.tourScreen.TourScreenResponseDto;
import com.lcsz.abcde.dtos.tourScreen.TourScreenUpdateDto;
import com.lcsz.abcde.enums.tourScreen.TourScreenEnum;
import com.lcsz.abcde.security.AuthenticatedUserProvider;
import com.lcsz.abcde.services.TourScreenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("${api.basepath}tour-screen")
public class TourScreenController {
    private final TourScreenService service;
    private final AuthenticatedUserProvider provider;

    TourScreenController(TourScreenService service, AuthenticatedUserProvider provider) {
        this.service = service;
        this.provider = provider;
    }

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TourScreenResponseDto> getByAuthUserAndScreen(
        @RequestParam(required = true) TourScreenEnum screen
    ) {
        UUID authUserId = this.provider.getAuthenticatedUserId();
        TourScreenResponseDto responseDto = this.service.getByUserIdAndScreen(authUserId, screen);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> updateTourScreen(
        @PathVariable Long id,
        @RequestBody @Valid TourScreenUpdateDto updateDto
    ) {
        this.service.update(id, updateDto);
        return ResponseEntity.noContent().build();
    }
}

package com.lcsz.abcde.controllers;

import com.lcsz.abcde.dtos.permissions.PermissionResponseDto;
import com.lcsz.abcde.services.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/permissions")
public class PermissionController {
    private final PermissionService service;

    PermissionController(PermissionService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<PermissionResponseDto>> getAllPermissions() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPermissions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponseDto> getPermissionById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPermissionByIdDto(id));
    }
}

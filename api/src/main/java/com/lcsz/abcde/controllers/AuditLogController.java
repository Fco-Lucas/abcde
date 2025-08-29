package com.lcsz.abcde.controllers;

import com.lcsz.abcde.dtos.PageableDto;
import com.lcsz.abcde.dtos.auditLog.AuditLogCreateDto;
import com.lcsz.abcde.dtos.auditLog.AuditLogResponseDto;
import com.lcsz.abcde.enums.auditLog.AuditAction;
import com.lcsz.abcde.enums.auditLog.AuditProgram;
import com.lcsz.abcde.mappers.PageableMapper;
import com.lcsz.abcde.repositorys.projection.AuditLogProjection;
import com.lcsz.abcde.services.AuditLogService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.basepath}logs")
public class AuditLogController {
    private final AuditLogService service;
    AuditLogController(AuditLogService service) {
        this.service = service;
    }

    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AuditLogResponseDto> createAuditLog(
            @RequestBody @Valid AuditLogCreateDto dto
    ) {
     AuditLogResponseDto responseDto = this.service.create(dto);
     return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PageableDto> getAllAuditLogPageable(
            Pageable pageable,
            @RequestParam(required = false) AuditAction action,
            @RequestParam(required = false) String client,
            @RequestParam(required = false) String user,
            @RequestParam(required = false) AuditProgram program,
            @RequestParam(required = false) String details,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        Page<AuditLogResponseDto> entries = this.service.getAllPageable(
                pageable,
                action,
                client,
                user,
                program,
                details,
                startDate,
                endDate
        );
        return ResponseEntity.status(HttpStatus.OK).body(PageableMapper.toDto(entries));
    }

//    @GetMapping("/{id}")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<AuditLogResponseDto> getAuditLogById(
//            @PathVariable Long id
//    ) {
//        AuditLogResponseDto responseDto = this.service.getByIdDto(id);
//        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
//    }

}

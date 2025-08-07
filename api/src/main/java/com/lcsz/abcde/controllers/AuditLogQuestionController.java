package com.lcsz.abcde.controllers;

import com.lcsz.abcde.dtos.PageableDto;
import com.lcsz.abcde.dtos.auditLogQuestion.AuditLogQuestionResponseDto;
import com.lcsz.abcde.mappers.PageableMapper;
import com.lcsz.abcde.repositorys.projection.AuditLogQuestionProjection;
import com.lcsz.abcde.services.AuditLogQuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/logs-questions")
public class AuditLogQuestionController {
    private final AuditLogQuestionService service;

    AuditLogQuestionController(AuditLogQuestionService service) {
        this.service = service;
    }

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PageableDto> getAllAuditLogQuestionPageable(
            Pageable pageable,
            @RequestParam(required = true) Long imageId,
            @RequestParam(required = false) String user,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        Page<AuditLogQuestionResponseDto> entries = this.service.getAllPageable(
                pageable,
                imageId,
                user,
                startDate,
                endDate
        );
        return ResponseEntity.status(HttpStatus.OK).body(PageableMapper.toDto(entries));
    }
}

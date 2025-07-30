package com.lcsz.abcde.services;

import com.lcsz.abcde.dtos.auditLog.AuditLogCreateDto;
import com.lcsz.abcde.dtos.auditLog.AuditLogResponseDto;
import com.lcsz.abcde.enums.auditLog.AuditAction;
import com.lcsz.abcde.enums.auditLog.AuditProgram;
import com.lcsz.abcde.exceptions.customExceptions.EntityNotFoundException;
import com.lcsz.abcde.mappers.AuditLogMapper;
import com.lcsz.abcde.models.AuditLog;
import com.lcsz.abcde.repositorys.AuditLogRepository;
import com.lcsz.abcde.repositorys.projection.AuditLogProjection;
import com.lcsz.abcde.security.AuthenticatedUserProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuditLogService {
    private final AuditLogRepository repository;
    private final AuthenticatedUserProvider provider;
    private static final LocalDateTime POSTGRES_MIN = LocalDateTime.of(1900, 1, 1, 0, 0);
    private static final LocalDateTime POSTGRES_MAX = LocalDateTime.of(2999, 12, 31, 23, 59, 59);

    AuditLogService(AuditLogRepository repository, AuthenticatedUserProvider provider) {
        this.repository = repository;
        this.provider = provider;
    }

    @Transactional(readOnly = false)
    public AuditLogResponseDto create(AuditLogCreateDto dto) {
        // Converte o dto recebido para STRING
        AuditLog auditLog = AuditLogMapper.toCreateEntity(dto);
        AuditLog saved = this.repository.save(auditLog);
        return AuditLogMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public Page<AuditLogResponseDto> getAllPageable(
            Pageable pageable,
            AuditAction action,
            String user,
            AuditProgram program,
            String details,
            String startDate,
            String endDate
    ) {
        String filterUser = (user == null || user.isBlank()) ? null : "%" + user + "%";
        String filterDetails = (details == null || details.isBlank()) ? null : "%" + details + "%";
        LocalDateTime start = (startDate == null || startDate.isBlank()) ? POSTGRES_MIN : LocalDateTime.parse(startDate);
        LocalDateTime end = (endDate == null || endDate.isBlank()) ? POSTGRES_MAX : LocalDateTime.parse(endDate);

        Page<AuditLogProjection> entries = this.repository.findAllPageable(
            pageable,
            action,
            filterUser,
            program,
            filterDetails,
            start,
            end
        );

        return entries.map(entrie -> {
            return this.getByIdDto(entrie.getId());
        });
    }

    @Transactional(readOnly = true)
    public AuditLog getById(Long id) {
        return this.repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Registro da auditoria com ID: '%s' não encontrado", id))
        );
    }

    @Transactional(readOnly = true)
    public AuditLogResponseDto getByIdDto(Long id) {
        AuditLog auditLog = this.getById(id);
        String userName = this.provider.getAuthenticatedUserName(auditLog.getUserId());
        AuditLogResponseDto responseDto = AuditLogMapper.toDto(auditLog);
        responseDto.setUserName(userName);
        return responseDto;
    }
}

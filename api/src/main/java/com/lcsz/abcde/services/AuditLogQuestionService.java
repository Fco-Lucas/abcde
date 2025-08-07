package com.lcsz.abcde.services;

import com.lcsz.abcde.dtos.auditLogQuestion.AuditLogQuestionCreateDto;
import com.lcsz.abcde.dtos.auditLogQuestion.AuditLogQuestionResponseDto;
import com.lcsz.abcde.mappers.AuditLogQuestionMapper;
import com.lcsz.abcde.models.AuditLogQuestion;
import com.lcsz.abcde.repositorys.AuditLogQuestionRepository;
import com.lcsz.abcde.repositorys.projection.AuditLogQuestionProjection;
import com.lcsz.abcde.security.AuthenticatedUserProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuditLogQuestionService {
    private final AuditLogQuestionRepository repository;
    private final AuthenticatedUserProvider provider;
    private static final LocalDateTime POSTGRES_MIN = LocalDateTime.of(1900, 1, 1, 0, 0);
    private static final LocalDateTime POSTGRES_MAX = LocalDateTime.of(2999, 12, 31, 23, 59, 59);

    AuditLogQuestionService(AuditLogQuestionRepository repository, AuthenticatedUserProvider provider) {
        this.repository = repository;
        this.provider = provider;
    }

    @Transactional(readOnly = false)
    public void create(AuditLogQuestionCreateDto dto) {
        AuditLogQuestion auditLogQuestion = AuditLogQuestionMapper.toCreateEntity(dto);
        AuditLogQuestion saved = this.repository.save(auditLogQuestion);
    }

    @Transactional(readOnly = true)
    public Page<AuditLogQuestionResponseDto> getAllPageable(
            Pageable pageable,
            Long imageId,
            String user,
            String startDate,
            String endDate
    ) {
        String filterUser = (user == null || user.isBlank()) ? null : "%" + user + "%";
        LocalDateTime start = (startDate == null || startDate.isBlank()) ? POSTGRES_MIN : LocalDateTime.parse(startDate);
        LocalDateTime end = (endDate == null || endDate.isBlank()) ? POSTGRES_MAX : LocalDateTime.parse(endDate);

        Page<AuditLogQuestionProjection> entries = this.repository.getAllPageable(
                pageable,
                imageId,
                filterUser,
                start,
                end
        );

        return entries.map(entrie -> {
            AuditLogQuestionResponseDto dto = AuditLogQuestionMapper.toDtoPageable(entrie);
            if(entrie.getUserName() == null || entrie.getUserName().isBlank()) dto.setUserName(entrie.getClientName());
            return dto;
        });
    }
}

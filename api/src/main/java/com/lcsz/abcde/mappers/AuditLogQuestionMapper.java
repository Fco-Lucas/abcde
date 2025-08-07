package com.lcsz.abcde.mappers;

import com.lcsz.abcde.dtos.auditLogQuestion.AuditLogQuestionCreateDto;
import com.lcsz.abcde.dtos.auditLogQuestion.AuditLogQuestionResponseDto;
import com.lcsz.abcde.models.AuditLogQuestion;
import com.lcsz.abcde.repositorys.projection.AuditLogQuestionProjection;
import org.modelmapper.ModelMapper;

public class AuditLogQuestionMapper {
    private static final ModelMapper mapper = new ModelMapper();

    static {
        mapper.typeMap(AuditLogQuestionCreateDto.class, AuditLogQuestion.class).addMappings(map -> {
            map.skip(AuditLogQuestion::setId);
            map.skip(AuditLogQuestion::setUserId);
            map.skip(AuditLogQuestion::setCreatedAt);
        });
    }

    public static AuditLogQuestion toCreateEntity(AuditLogQuestionCreateDto createDto) {
        return mapper.map(createDto, AuditLogQuestion.class);
    }

    public static AuditLogQuestionResponseDto toDtoPageable(AuditLogQuestionProjection entrie) {
        return mapper.map(entrie, AuditLogQuestionResponseDto.class);
    }
}

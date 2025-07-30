package com.lcsz.abcde.mappers;

import com.lcsz.abcde.dtos.auditLog.AuditLogCreateDto;
import com.lcsz.abcde.dtos.auditLog.AuditLogResponseDto;
import com.lcsz.abcde.models.AuditLog;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class AuditLogMapper {
    private static final ModelMapper mapper = new ModelMapper();

    static {
        mapper.typeMap(AuditLogCreateDto.class, AuditLog.class).addMappings(map -> {
            map.skip(AuditLog::setId);
        });
    }

    public static AuditLog toCreateEntity(AuditLogCreateDto createDto) {
        return mapper.map(createDto, AuditLog.class);
    }

    public static AuditLogResponseDto toDto(AuditLog log) {
        return mapper.map(log, AuditLogResponseDto.class);
    }

    public static List<AuditLogResponseDto> toListDto(List<AuditLog> logs) {
        return logs.stream().map(AuditLogMapper::toDto).collect(Collectors.toList());
    }
}


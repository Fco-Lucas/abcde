package com.lcsz.abcde.mappers;

import com.lcsz.abcde.dtos.permissions.PermissionResponseDto;
import com.lcsz.abcde.models.Permission;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class PermissionMapper {
    public static PermissionResponseDto toDto(Permission permission) {
        return new ModelMapper().map(permission, PermissionResponseDto.class);
    }

    public static List<PermissionResponseDto> toListDto(List<Permission> permissions) {
        return permissions.stream().map(permission -> toDto(permission)).collect(Collectors.toList());
    }
}

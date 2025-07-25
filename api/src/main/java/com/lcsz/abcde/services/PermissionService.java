package com.lcsz.abcde.services;

import com.lcsz.abcde.dtos.permissions.PermissionResponseDto;
import com.lcsz.abcde.exceptions.customExceptions.EntityNotFoundException;
import com.lcsz.abcde.mappers.PermissionMapper;
import com.lcsz.abcde.models.Permission;
import com.lcsz.abcde.repositorys.PermissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionService {
    private final PermissionRepository repository;

    PermissionService(PermissionRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<PermissionResponseDto> getPermissions() {
        List<Permission> permissions = this.repository.findAll();
        return PermissionMapper.toListDto(permissions);
    }

    @Transactional(readOnly = true)
    public Permission getPermissionById(Long id) {
        return this.repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Permissão com id '%s' não encontrada", id))
        );
    }
    @Transactional(readOnly = true)
    public PermissionResponseDto getPermissionByIdDto(Long id) {
        Permission permission = this.getPermissionById(id);
        return PermissionMapper.toDto(permission);
    }

    @Transactional(readOnly = true)
    public PermissionResponseDto getMainPermission() {
        Permission permission = this.repository.findMainPermission().orElseThrow(
                () -> new EntityNotFoundException("Permissão main não encontrada")
        );
        return PermissionMapper.toDto(permission);
    }
}

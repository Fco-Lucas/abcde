package com.lcsz.abcde.repositorys;

import com.lcsz.abcde.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query("SELECT p FROM Permission p WHERE p.upload_files = true AND p.read_files = true")
    Optional<Permission> findMainPermission();
}

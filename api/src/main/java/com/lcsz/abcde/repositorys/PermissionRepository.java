package com.lcsz.abcde.repositorys;

import com.lcsz.abcde.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}

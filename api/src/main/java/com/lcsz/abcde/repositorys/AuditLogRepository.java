package com.lcsz.abcde.repositorys;

import com.lcsz.abcde.enums.auditLog.AuditAction;
import com.lcsz.abcde.enums.auditLog.AuditProgram;
import com.lcsz.abcde.models.AuditLog;
import com.lcsz.abcde.repositorys.projection.AuditLogProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    @Query("""
        SELECT a FROM AuditLog a
            LEFT JOIN Client c ON a.userId = c.id
            LEFT JOIN ClientUser u ON a.userId = u.id
        WHERE (:action IS NULL OR a.action = :action)
          AND (:user IS NULL OR c.name ILIKE :user OR u.name ILIKE :user)
          AND (:program IS NULL OR a.program = :program)
          AND (:details IS NULL OR a.details ILIKE :details)
          AND a.createdAt BETWEEN :startDate AND :endDate
    """)
    Page<AuditLogProjection> findAllPageable(
            Pageable pageable,
            @Param("action") AuditAction action,
            @Param("user") String user,
            @Param("program") AuditProgram program,
            @Param("details") String details,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}

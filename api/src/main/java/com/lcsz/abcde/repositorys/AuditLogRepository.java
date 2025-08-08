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
    @Query(value = """
        SELECT 
            A.id,
            A.action,
            C.id AS clientId,
            C.name AS clientName,
            CU.id AS userId,
            CU.name AS userName,
            A.program,
            A.details AS details,
            A.created_at AS createdAt
        FROM auditlog A
            INNER JOIN clients C ON A.client_id = C.id
            LEFT JOIN clients_users CU ON A.user_id = CU.id
        WHERE (:action IS NULL OR A.action = CAST(:action AS TEXT))
          AND (:program IS NULL OR A.program = CAST(:program AS TEXT))
          AND (:user IS NULL OR C.name ILIKE :user OR CU.name ILIKE :user)
          AND A.created_at BETWEEN :startDate AND :endDate 
          AND (COALESCE(:client, '') = '' OR C.name ILIKE :client) 
        ORDER BY A.id DESC
    """, nativeQuery = true)
    Page<AuditLogProjection> findAllPageable(
            Pageable pageable,
            @Param("action") String action,
            @Param("program") String program,
            @Param("user") String user,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("client") String client,
            @Param("details") String details
    );
}

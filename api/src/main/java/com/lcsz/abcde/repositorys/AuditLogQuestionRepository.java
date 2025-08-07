package com.lcsz.abcde.repositorys;

import com.lcsz.abcde.models.AuditLogQuestion;
import com.lcsz.abcde.repositorys.projection.AuditLogQuestionProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AuditLogQuestionRepository extends JpaRepository<AuditLogQuestion, Long> {
    @Query(value = """
        SELECT 
            A.id,
            C.id AS clientId,
            C.name AS clientName,
            CU.id AS userId,
            CU.name AS userName,
            I.id AS imageId,
            A.details AS details,
            A.created_at AS createdAt
        FROM auditlog_questions A
        INNER JOIN lots_images I ON A.image_id = I.id
        LEFT JOIN clients_users CU ON A.user_id = CU.id
        INNER JOIN clients C ON A.client_id = C.id
        WHERE A.image_id = :imageId
          AND (:user IS NULL OR C.name ILIKE :user OR CU.name ILIKE :user)
          AND A.created_at BETWEEN :startDate AND :endDate
    """, nativeQuery = true)
    Page<AuditLogQuestionProjection> getAllPageable(
        Pageable pageable,
        @Param("imageId") Long imageId,
        @Param("user") String user,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
}

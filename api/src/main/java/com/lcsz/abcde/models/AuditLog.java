package com.lcsz.abcde.models;

import com.lcsz.abcde.enums.auditLog.AuditAction;
import com.lcsz.abcde.enums.auditLog.AuditProgram;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "auditlog")
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private AuditAction action;

    @Column(nullable = false, name = "client_id")
    private UUID clientId;

    @CreatedBy
    @Column(nullable = false, name = "user_id")
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 255)
    private AuditProgram program;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String details;

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    public AuditLog(Long id, AuditAction action, UUID clientId, UUID userId, AuditProgram program, String details, LocalDateTime createdAt) {
        this.id = id;
        this.action = action;
        this.clientId = clientId;
        this.userId = userId;
        this.program = program;
        this.details = details;
        this.createdAt = createdAt;
    }

    public AuditLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuditAction getAction() {
        return action;
    }

    public void setAction(AuditAction action) {
        this.action = action;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public AuditProgram getProgram() {
        return program;
    }

    public void setProgram(AuditProgram program) {
        this.program = program;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AuditLog auditLog = (AuditLog) o;
        return Objects.equals(id, auditLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AuditLog{" +
                "id=" + id +
                ", action=" + action +
                ", clientId=" + clientId +
                ", userId=" + userId +
                ", program=" + program +
                ", details='" + details + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

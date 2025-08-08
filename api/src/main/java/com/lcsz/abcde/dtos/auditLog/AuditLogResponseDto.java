package com.lcsz.abcde.dtos.auditLog;

import com.lcsz.abcde.enums.auditLog.AuditAction;
import com.lcsz.abcde.enums.auditLog.AuditProgram;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuditLogResponseDto {
    private Long id;
    private AuditAction action;
    private UUID clientId;
    private String clientName;
    private UUID userId;
    private String userName;
    private AuditProgram program;
    private String details;
    private LocalDateTime createdAt;

    public AuditLogResponseDto() {
    }

    public AuditLogResponseDto(Long id, AuditAction action, UUID clientId, String clientName, UUID userId, String userName, AuditProgram program, String details, LocalDateTime createdAt) {
        this.id = id;
        this.action = action;
        this.clientId = clientId;
        this.clientName = clientName;
        this.userId = userId;
        this.userName = userName;
        this.program = program;
        this.details = details;
        this.createdAt = createdAt;
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
    public String toString() {
        return "AuditLogResponseDto{" +
                "id=" + id +
                ", action=" + action +
                ", clientId=" + clientId +
                ", clientName=" + clientName +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", program=" + program +
                ", details='" + details + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

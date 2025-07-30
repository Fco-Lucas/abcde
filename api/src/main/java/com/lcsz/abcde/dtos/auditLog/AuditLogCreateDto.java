package com.lcsz.abcde.dtos.auditLog;

import com.lcsz.abcde.enums.auditLog.AuditAction;
import com.lcsz.abcde.enums.auditLog.AuditProgram;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class AuditLogCreateDto {
    @NotNull(message = "O campo 'action' é obrigatório")
    private AuditAction action;
    // O userId é preenchido automaticamente, mas para métodos cujo não necessitam de autenticação (criação de clientes, login e etc) se deve preencher
    private UUID userId;
    @NotNull(message = "O campo 'program' é obrigatório")
    private AuditProgram program;
    @NotBlank(message = "O campo 'details' é obrigatório")
    private String details;

    public AuditLogCreateDto() {
    }

    public AuditLogCreateDto(AuditAction action, UUID userId, AuditProgram program, String details) {
        this.action = action;
        this.userId = userId;
        this.program = program;
        this.details = details;
    }

    public AuditLogCreateDto(AuditAction action, AuditProgram program, String details) {
        this.action = action;
        this.program = program;
        this.details = details;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public AuditAction getAction() {
        return action;
    }

    public void setAction(AuditAction action) {
        this.action = action;
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

    @Override
    public String toString() {
        return "AuditLogCrateDto{" +
                "userId=" + userId +
                ", action=" + action +
                ", program=" + program +
                ", details='" + details + '\'' +
                '}';
    }
}

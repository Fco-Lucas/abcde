package com.lcsz.abcde.repositorys.projection;

import com.lcsz.abcde.enums.auditLog.AuditAction;
import com.lcsz.abcde.enums.auditLog.AuditProgram;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AuditLogProjection {
    Long getId();
    AuditAction getAction();
    UUID getClientId();
    String getClientName();
    UUID getUserId();
    String getUserName();
    AuditProgram getProgram();
    String getDetails();
    LocalDateTime getCreatedAt();
}

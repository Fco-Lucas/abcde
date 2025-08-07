package com.lcsz.abcde.repositorys.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AuditLogQuestionProjection {
    Long getId();
    UUID getClientId();
    String getClientName();
    UUID getUserId();
    String getUserName();
    Long getImageId();
    String getDetails();
    LocalDateTime getCreatedAt();
}

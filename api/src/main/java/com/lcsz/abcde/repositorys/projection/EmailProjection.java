package com.lcsz.abcde.repositorys.projection;

import com.lcsz.abcde.enums.email.EmailType;

import java.time.LocalDateTime;
import java.util.UUID;

public interface EmailProjection {
    Long getId();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
    UUID getIdClient();
    UUID getIdClientUser();
    EmailType getType();
    String getSubject();
    String getOrigin();
    String getDestiny();
    String getDestinyName();
    String getAttachments();
    Integer getStatusCode();
    String getTemplateId();
    String getTemplateFields();
    LocalDateTime getAppointmentDate();
    String getTxMessageId();
}

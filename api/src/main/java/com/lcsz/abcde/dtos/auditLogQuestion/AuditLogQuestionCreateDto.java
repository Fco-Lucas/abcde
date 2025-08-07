package com.lcsz.abcde.dtos.auditLogQuestion;

import java.util.UUID;

public class AuditLogQuestionCreateDto {
    private UUID clientId;
    private Long imageId;
    private String details;

    public AuditLogQuestionCreateDto() {
    }

    public AuditLogQuestionCreateDto(UUID clientId, Long imageId, String details) {
        this.clientId = clientId;
        this.imageId = imageId;
        this.details = details;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "AuditLogQuestionCreateDto{" +
                "clientId=" + clientId +
                ", imageId=" + imageId +
                ", details='" + details + '\'' +
                '}';
    }
}

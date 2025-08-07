package com.lcsz.abcde.dtos.auditLogQuestion;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuditLogQuestionResponseDto {
    private Long id;
    private UUID clientId;
    private String clientName;
    private UUID userId;
    private String userName;
    private Long imageId;
    private String details;
    private LocalDateTime createdAt;

    public AuditLogQuestionResponseDto() {
    }

    public AuditLogQuestionResponseDto(Long id, UUID clientId, String clientName, UUID userId, String userName, Long imageId, String details, LocalDateTime createdAt) {
        this.id = id;
        this.clientId = clientId;
        this.clientName = clientName;
        this.userId = userId;
        this.userName = userName;
        this.imageId = imageId;
        this.details = details;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "AuditLogQuestionResponseDto{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", imageId=" + imageId +
                ", details='" + details + '\'' +
                '}';
    }
}

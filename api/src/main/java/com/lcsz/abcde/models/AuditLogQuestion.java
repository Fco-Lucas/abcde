package com.lcsz.abcde.models;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "auditlog_questions")
@EntityListeners(AuditingEntityListener.class)
public class AuditLogQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "client_id")
    private UUID clientId;
    @CreatedBy
    @Column(nullable = false, name = "user_id")
    private UUID userId;
    @Column(nullable = false, name = "image_id")
    private Long imageId;
    @Column(nullable = false, name = "details")
    private String details;
    @CreatedDate
    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    public AuditLogQuestion() {
    }

    public AuditLogQuestion(Long id, UUID clientId, UUID userId, Long imageId, String details, LocalDateTime createdAt) {
        this.id = id;
        this.clientId = clientId;
        this.userId = userId;
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AuditLogQuestion that = (AuditLogQuestion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AuditLogQuestion{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", userId=" + userId +
                ", imageId=" + imageId +
                ", details='" + details + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

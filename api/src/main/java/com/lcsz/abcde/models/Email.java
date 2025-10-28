package com.lcsz.abcde.models;

import com.lcsz.abcde.enums.email.EmailType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import com.vladmihalcea.hibernate.type.json.JsonType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "emails")
public class Email implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "id_client", nullable = false)
    private UUID idClient;
    @Column(name = "id_client_user")
    private UUID idClientUser;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private EmailType type;
    @Column(nullable = false, length = 150)
    private String subject;
    @Column(nullable = false, length = 150)
    private String origin;
    @Column(nullable = false, length = 150)
    private String destiny;
    @Column(name = "destiny_name", nullable = false, length = 100)
    private String destinyName;
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<String> attachments;
    @Column(name = "status_code")
    private Integer statusCode;
    @Column(name = "template_id", nullable = false, length = 200)
    private String templateId;
    @Type(JsonType.class)
    @Column(name = "template_fields", columnDefinition = "jsonb", nullable = false)
    private Map<String, Object> templateFields;
    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;
    @Column(name = "tx_message_id", length = 36)
    private String txMessageId;

    public Email() {
    }

    public Email(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, UUID idClient, UUID idClientUser, EmailType type, String subject, String origin, String destiny, String destinyName, List<String> attachments, Integer statusCode, String templateId, Map<String, Object> templateFields, LocalDateTime appointmentDate, String txMessageId) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.idClient = idClient;
        this.idClientUser = idClientUser;
        this.type = type;
        this.subject = subject;
        this.origin = origin;
        this.destiny = destiny;
        this.destinyName = destinyName;
        this.attachments = attachments;
        this.statusCode = statusCode;
        this.templateId = templateId;
        this.templateFields = templateFields;
        this.appointmentDate = appointmentDate;
        this.txMessageId = txMessageId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UUID getIdClient() {
        return idClient;
    }

    public void setIdClient(UUID idClient) {
        this.idClient = idClient;
    }

    public UUID getIdClientUser() {
        return idClientUser;
    }

    public void setIdClientUser(UUID idClientUser) {
        this.idClientUser = idClientUser;
    }

    public EmailType getType() {
        return type;
    }

    public void setType(EmailType type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public String getDestinyName() {
        return destinyName;
    }

    public void setDestinyName(String destinyName) {
        this.destinyName = destinyName;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Map<String, Object> getTemplateFields() {
        return templateFields;
    }

    public void setTemplateFields(Map<String, Object> templateFields) {
        this.templateFields = templateFields;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getTxMessageId() {
        return txMessageId;
    }

    public void setTxMessageId(String txMessageId) {
        this.txMessageId = txMessageId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(id, email.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", idClient=" + idClient +
                ", idClientUser=" + idClientUser +
                ", type=" + type +
                ", subject='" + subject + '\'' +
                ", origin='" + origin + '\'' +
                ", destiny='" + destiny + '\'' +
                ", destinyName='" + destinyName + '\'' +
                ", attachments=" + attachments +
                ", statusCode=" + statusCode +
                ", templateId='" + templateId + '\'' +
                ", templateFields=" + templateFields +
                ", appointmentDate=" + appointmentDate +
                ", txMessageId=" + txMessageId +
                '}';
    }
}

package com.lcsz.abcde.dtos.email;

import com.lcsz.abcde.enums.email.EmailType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EmailCreateDto {
    private UUID idClient;
    private UUID idClientUser;
    private EmailType type;
    private String subject;
    private String destiny;
    private String destinyName;
    private List<String> attachments;
    private String templateId;
    private Map<String, Object> templateFields;
    private LocalDateTime appointmentDate;

    public EmailCreateDto() {
    }

    public EmailCreateDto(UUID idClient, UUID idClientUser, EmailType type, String subject, String destiny, String destinyName, List<String> attachments, String templateId, Map<String, Object> templateFields, LocalDateTime appointmentDate) {
        this.idClient = idClient;
        this.idClientUser = idClientUser;
        this.type = type;
        this.subject = subject;
        this.destiny = destiny;
        this.destinyName = destinyName;
        this.attachments = attachments;
        this.templateId = templateId;
        this.templateFields = templateFields;
        this.appointmentDate = appointmentDate;
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

    @Override
    public String toString() {
        return "EmailCreateDto{" +
                "idClient=" + idClient +
                ", idClientUser=" + idClientUser +
                ", type=" + type +
                ", subject='" + subject + '\'' +
                ", destiny='" + destiny + '\'' +
                ", destinyName='" + destinyName + '\'' +
                ", attachments=" + attachments +
                ", templateId='" + templateId + '\'' +
                ", templateFields=" + templateFields +
                ", appointmentDate=" + appointmentDate +
                '}';
    }
}

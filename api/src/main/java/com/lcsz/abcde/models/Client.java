package com.lcsz.abcde.models;

import com.lcsz.abcde.enums.client.ClientStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "clients")
@EntityListeners(AuditingEntityListener.class)
public class Client implements Serializable {
    @Id
    @GeneratedValue
    @Column(length = 36, columnDefinition = "UUID")
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, length = 14)
    private String cnpj;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = true, name = "url_to_post")
    private String urlToPost;
    @Column(nullable = false, name = "image_active_days")
    private Integer imageActiveDays;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClientStatus status = ClientStatus.ACTIVE;
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Client() {
    }

    public Client(UUID id, String name, String cnpj, String email, String password, String urlToPost, Integer imageActiveDays, ClientStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.email = email;
        this.password = password;
        this.urlToPost = urlToPost;
        this.imageActiveDays = imageActiveDays;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrlToPost() {
        return urlToPost;
    }

    public void setUrlToPost(String urlToPost) {
        this.urlToPost = urlToPost;
    }

    public Integer getImageActiveDays() {
        return imageActiveDays;
    }

    public void setImageActiveDays(Integer imageActiveDays) {
        this.imageActiveDays = imageActiveDays;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
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
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", urlToPost='" + urlToPost + '\'' +
                ", imageActiveDays='" + imageActiveDays + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

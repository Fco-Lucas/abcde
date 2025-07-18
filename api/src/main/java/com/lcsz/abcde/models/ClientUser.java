package com.lcsz.abcde.models;

import com.lcsz.abcde.enums.clientUser.ClientUserStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "clients_users")
@EntityListeners(AuditingEntityListener.class)
public class ClientUser implements Serializable {
    @Id
    @GeneratedValue
    @Column(length = 36, columnDefinition = "UUID")
    private UUID id;
    @Column(name = "client_id", nullable = false)
    private UUID clientId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Long permission;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClientUserStatus status = ClientUserStatus.ACTIVE;
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public ClientUser() {
    }

    public ClientUser(UUID id, UUID clientId, String name, String email, String password, Long permission, ClientUserStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.permission = permission;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getPermission() {
        return permission;
    }

    public void setPermission(Long permission) {
        this.permission = permission;
    }

    public ClientUserStatus getStatus() {
        return status;
    }

    public void setStatus(ClientUserStatus status) {
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
        ClientUser that = (ClientUser) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClientUser{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", permission=" + permission +
                ", status=" + status +
                ", clientId=" + clientId +
                '}';
    }
}

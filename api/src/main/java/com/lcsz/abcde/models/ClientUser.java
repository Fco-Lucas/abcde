package com.lcsz.abcde.models;

import com.lcsz.abcde.enums.clientUser.ClientUserStatus;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "clients_users")
public class ClientUser implements Serializable {
    @Id
    @GeneratedValue
    @Column(length = 36, columnDefinition = "UUID")
    private UUID id;
    @Column(nullable = false)
    private UUID client_id;
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

    public ClientUser() {
    }

    public ClientUser(UUID id, UUID client_id, String name, String email, String password, Long permission, ClientUserStatus status) {
        this.id = id;
        this.client_id = client_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.permission = permission;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClient_id() {
        return client_id;
    }

    public void setClient_id(UUID client_id) {
        this.client_id = client_id;
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
                ", client_id=" + client_id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", permission=" + permission +
                ", status=" + status +
                '}';
    }
}

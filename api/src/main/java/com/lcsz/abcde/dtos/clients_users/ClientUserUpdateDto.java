package com.lcsz.abcde.dtos.clients_users;

import java.util.UUID;

public class ClientUserUpdateDto {
    private UUID clientId;
    private String name;
    private String email;
    private Long permission;

    public ClientUserUpdateDto() {
    }

    public ClientUserUpdateDto(UUID clientId, String name, String email, Long permission) {
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.permission = permission;
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

    public Long getPermission() {
        return permission;
    }

    public void setPermission(Long permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "ClientUserUpdateDto{" +
                "clientId=" + clientId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", permission=" + permission +
                '}';
    }
}

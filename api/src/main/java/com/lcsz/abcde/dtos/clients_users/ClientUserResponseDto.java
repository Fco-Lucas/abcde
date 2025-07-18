package com.lcsz.abcde.dtos.clients_users;

import com.lcsz.abcde.dtos.permissions.PermissionResponseDto;
import com.lcsz.abcde.enums.clientUser.ClientUserStatus;
import com.lcsz.abcde.models.Permission;

import java.util.UUID;

public class ClientUserResponseDto {
    private UUID id;
    private UUID clientId;
    private String name;
    private String email;
    private PermissionResponseDto permission;
    ClientUserStatus status;

    public ClientUserResponseDto() {
    }

    public ClientUserResponseDto(UUID id, UUID clientId, String name, String email, PermissionResponseDto permission, ClientUserStatus status) {
        this.id = id;
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.permission = permission;
        this.status = status;
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

    public PermissionResponseDto getPermission() {
        return permission;
    }

    public void setPermission(PermissionResponseDto permission) {
        this.permission = permission;
    }

    public ClientUserStatus getStatus() {
        return status;
    }

    public void setStatus(ClientUserStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClientUserResponseDto{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", permission=" + permission +
                ", status=" + status +
                '}';
    }
}

package com.lcsz.abcde.dtos.clients_users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class ClientUserCreateDto {
    @NotNull(message = "O campo 'clientId' é obrigatório")
    private UUID clientId;
    @NotBlank(message = "O campo 'name' é obrigatório")
    private String name;
    @NotBlank(message = "O campo 'email' é obrigatório")
    private String email;
    @NotBlank(message = "O campo 'password' é obrigatório")
    @Size(min = 6, message = "O campo 'password' deve conter ao menos 6 caracteres")
    private String password;
    @NotNull(message = "O campo 'permission' é obrigatório")
    private Long permission;

    public ClientUserCreateDto() {
    }

    public ClientUserCreateDto(UUID clientId, String name, String email, String password, Long permission) {
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.password = password;
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

    @Override
    public String toString() {
        return "ClientUserCreateDto{" +
                "clientId=" + clientId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", permission=" + permission +
                '}';
    }
}

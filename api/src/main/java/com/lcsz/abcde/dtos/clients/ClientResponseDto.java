package com.lcsz.abcde.dtos.clients;

import com.lcsz.abcde.enums.client.ClientStatus;

import java.util.UUID;

public class ClientResponseDto {
    private UUID id;
    private String name;
    private String cnpj;
    private ClientStatus status;

    public ClientResponseDto() {
    }

    public ClientResponseDto(UUID id, String name, String cnpj, ClientStatus status) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.status = status;
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

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClientResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", status=" + status +
                '}';
    }
}

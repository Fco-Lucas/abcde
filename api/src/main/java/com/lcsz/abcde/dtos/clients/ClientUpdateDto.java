package com.lcsz.abcde.dtos.clients;

public class ClientUpdateDto {
    private String name;
    private String cnpj;

    public ClientUpdateDto() {
    }

    public ClientUpdateDto(String name, String cnpj) {
        this.name = name;
        this.cnpj = cnpj;
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

    @Override
    public String toString() {
        return "ClientUpdateDto{" +
                "name=" + name +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}

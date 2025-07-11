package com.lcsz.abcde.models;

import com.lcsz.abcde.enums.client.ClientStatus;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "clients")
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
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClientStatus status = ClientStatus.ACTIVE;

    public Client() {
    }

    public Client(UUID id, String name, String cnpj, String password, ClientStatus status) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
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
                ", password='" + password + '\'' +
                ", status=" + status +
                '}';
    }
}

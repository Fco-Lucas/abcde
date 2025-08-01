package com.lcsz.abcde.dtos.clients;

import com.lcsz.abcde.dtos.clients_users.ClientUserResponseDto;
import com.lcsz.abcde.enums.client.ClientStatus;

import java.util.List;
import java.util.UUID;

public class ClientResponseDto {
    private UUID id;
    private String name;
    private String cnpj;
    private String urlToPost;
    private Integer imageActiveDays;
    private ClientStatus status;
    private List<ClientUserResponseDto> users;

    public ClientResponseDto() {
    }

    public ClientResponseDto(UUID id, String name, String cnpj, String urlToPost, Integer imageActiveDays, ClientStatus status, List<ClientUserResponseDto> users) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.urlToPost = urlToPost;
        this.imageActiveDays = imageActiveDays;
        this.status = status;
        this.users = users;
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

    public List<ClientUserResponseDto> getUsers() {
        return users;
    }

    public void setUsers(List<ClientUserResponseDto> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "ClientResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", urlToPost='" + urlToPost + '\'' +
                ", imageActiveDays='" + imageActiveDays + '\'' +
                ", status='" + status + '\'' +
                ", users=" + users +
                '}';
    }
}

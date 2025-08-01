package com.lcsz.abcde.dtos.clients;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ClientCreateDto {
    @NotBlank(message = "O campo 'name' é obrigatório")
    private String name;
    @NotBlank(message = "O campo 'cnpj' é obrigatório")
    @Size(min = 14, max = 14, message = "O campo 'cnpj' deve conter 14 caracteres")
    private String cnpj;
    @NotBlank(message = "O campo 'password' é obrigatório")
    @Size(min = 6, message = "O campo 'password' deve conter ao menos 6 caracteres")
    private String password;
    @NotNull(message = "O campo 'urlToPost' deve está presente podendo ser vazio e não NULO")
    private String urlToPost;
    @NotNull(message = "O campo 'imageActiveDays' é obrigatório")
    private Integer imageActiveDays;

    public ClientCreateDto() {
    }

    public ClientCreateDto(String name, String cnpj, String password, String urlToPost, Integer imageActiveDays) {
        this.name = name;
        this.cnpj = cnpj;
        this.password = password;
        this.urlToPost = urlToPost;
        this.imageActiveDays = imageActiveDays;
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

    @Override
    public String toString() {
        return "ClientCreateDto{" +
                "name='" + name + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", password=" + password +
                ", urlToPost='" + urlToPost + '\'' +
                ", imageActiveDays='" + imageActiveDays + '\'' +
                '}';
    }
}

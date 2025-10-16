package com.lcsz.abcde.dtos.clients;

public class ClientUpdateDto {
    private String name;
    private String cnpj;
    private String email;
    private String urlToPost;
    private Integer imageActiveDays;

    public ClientUpdateDto() {
    }

    public ClientUpdateDto(String name, String cnpj, String email, String urlToPost, Integer imageActiveDays) {
        this.name = name;
        this.cnpj = cnpj;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return "ClientUpdateDto{" +
                "name=" + name +
                ", cnpj='" + cnpj + '\'' +
                ", email='" + email + '\'' +
                ", urlToPost='" + urlToPost + '\'' +
                ", imageActiveDays='" + imageActiveDays +
                '}';
    }
}

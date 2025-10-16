package com.lcsz.abcde.dtos.clients;

public class ClientUpdateDto {
    private String name;
    private String cnpj;
    private String email;
    private Boolean customerComputex;
    private Integer numberContract;
    private String urlToPost;
    private Integer imageActiveDays;

    public ClientUpdateDto() {
    }

    public ClientUpdateDto(String name, String cnpj, String email, Boolean customerComputex, Integer numberContract, String urlToPost, Integer imageActiveDays) {
        this.name = name;
        this.cnpj = cnpj;
        this.email = email;
        this.customerComputex = customerComputex;
        this.numberContract = numberContract;
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

    public Boolean getCustomerComputex() {
        return customerComputex;
    }

    public void setCustomerComputex(Boolean customerComputex) {
        this.customerComputex = customerComputex;
    }

    public Integer getNumberContract() {
        return numberContract;
    }

    public void setNumberContract(Integer numberContract) {
        this.numberContract = numberContract;
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
                ", customerComputex='" + customerComputex + '\'' +
                ", numberContract='" + numberContract + '\'' +
                ", imageActiveDays='" + imageActiveDays +
                '}';
    }
}

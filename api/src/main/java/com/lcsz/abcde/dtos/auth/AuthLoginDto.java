package com.lcsz.abcde.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthLoginDto {
    @NotBlank
    private String login;
    @NotBlank
    @Size(min = 6, message = "O campo 'password' deve conter ao menos 6 caracteres", max = 255)
    private String password;

    public AuthLoginDto() {
    }

    public AuthLoginDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthLoginDto{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

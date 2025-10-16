package com.lcsz.abcde.dtos.clients;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ClientUpdatePasswordDto {
    @NotBlank(message = "O campo 'newPassword' é obrigatório")
    @Size(min = 6, message = "O campo 'newPassword' deve conter ao menos 6 caracteres")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).+$",
            message = "A nova senha deve conter ao menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial"
    )
    private String newPassword;
    @NotBlank(message = "O campo 'confirmNewPassword' é obrigatório")
    @Size(min = 6, message = "O campo 'confirmNewPassword' deve conter ao menos 6 caracteres")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).+$",
            message = "A confirmação da nova senha deve conter ao menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial"
    )
    private String confirmNewPassword;

    ClientUpdatePasswordDto() {}

    public ClientUpdatePasswordDto(String newPassword, String confirmNewPassword) {
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    @Override
    public String toString() {
        return "UserUpdatePasswordDto{" +
                "newPassword=" + newPassword +
                ", confirmNewPassword='" + confirmNewPassword + '\'' +
                '}';
    }
}

package com.lcsz.abcde.dtos.lot;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class LotCreateDto {
    @NotNull(message = "O campo 'userId' é obrigatório")
    private UUID userId;
    @NotBlank(message = "O campo 'name' é obrigatório")
    private String name;

    public LotCreateDto() {
    }

    public LotCreateDto(UUID userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LotCreateDto{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}

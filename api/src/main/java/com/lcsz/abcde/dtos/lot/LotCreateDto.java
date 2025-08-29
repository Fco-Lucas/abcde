package com.lcsz.abcde.dtos.lot;

import com.lcsz.abcde.enums.lot.LotType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class LotCreateDto {
    @NotNull(message = "O campo 'userId' é obrigatório")
    private UUID userId;
    @NotBlank(message = "O campo 'name' é obrigatório")
    private String name;
    @NotNull(message = "O campo 'type' é obrigatório")
    private LotType type;

    public LotCreateDto() {
    }

    public LotCreateDto(UUID userId, String name, LotType type) {
        this.userId = userId;
        this.name = name;
        this.type = type;
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

    public LotType getType() {
        return type;
    }

    public void setType(LotType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LotCreateDto{" +
                "userId=" + userId +
                "name=" + name +
                ", type='" + type + '\'' +
                '}';
    }
}

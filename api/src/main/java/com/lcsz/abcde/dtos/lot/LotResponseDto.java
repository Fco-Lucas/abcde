package com.lcsz.abcde.dtos.lot;

import com.lcsz.abcde.enums.lot.LotStatus;

import java.util.UUID;

public class LotResponseDto {
    private Long id;
    private UUID userId;
    private String name;
    private LotStatus status;

    public LotResponseDto() {
    }

    public LotResponseDto(Long id, UUID userId, String name, LotStatus status) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LotStatus getStatus() {
        return status;
    }

    public void setStatus(LotStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LotResponseDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", name=" + name +
                ", status='" + status + '\'' +
                '}';
    }
}

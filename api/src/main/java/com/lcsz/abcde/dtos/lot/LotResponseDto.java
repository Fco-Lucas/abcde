package com.lcsz.abcde.dtos.lot;

import com.lcsz.abcde.enums.lot.LotStatus;
import com.lcsz.abcde.enums.lot.LotType;

import java.time.LocalDateTime;
import java.util.UUID;

public class LotResponseDto {
    private Long id;
    private UUID userId;
    private String userName;
    private String userCnpj;
    private String name;
    private LotType type;
    private LocalDateTime createdAt;
    private Integer numberImages;
    private LotStatus status;
    private Boolean createdByComputex = false;

    public LotResponseDto() {
    }

    public LotResponseDto(Long id, UUID userId, String userName, String userCnpj, String name, LotType type, LocalDateTime createdAt, Integer numberImages, LotStatus status, Boolean createdByComputex) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userCnpj = userCnpj;
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
        this.numberImages = numberImages;
        this.status = status;
        this.createdByComputex = createdByComputex;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCnpj() {
        return userCnpj;
    }

    public void setUserCnpj(String userCnpj) {
        this.userCnpj = userCnpj;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getNumberImages() {
        return numberImages;
    }

    public void setNumberImages(Integer numberImages) {
        this.numberImages = numberImages;
    }

    public LotStatus getStatus() {
        return status;
    }

    public void setStatus(LotStatus status) {
        this.status = status;
    }

    public Boolean getCreatedByComputex() {
        return createdByComputex;
    }

    public void setCreatedByComputex(Boolean createdByComputex) {
        this.createdByComputex = createdByComputex;
    }

    @Override
    public String toString() {
        return "LotResponseDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName=" + userName +
                ", userCnpj=" + userCnpj +
                ", name=" + name +
                ", type=" + type +
                ", createdAt=" + createdAt +
                ", numberImages=" + numberImages +
                ", status=" + status +
                ", createdByComputex='" + createdByComputex + '\'' +
                '}';
    }
}

package com.lcsz.abcde.dtos.lot;

import com.lcsz.abcde.enums.lot.LotStatus;

import java.util.UUID;

public class LotResponseDto {
    private Long id;
    private UUID userId;
    private String userName;
    private String userCnpj;
    private String name;
    private Integer numberImages;
    private LotStatus status;

    public LotResponseDto() {
    }

    public LotResponseDto(Long id, UUID userId, String userName, String userCnpj, String name, Integer numberImages, LotStatus status) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userCnpj = userCnpj;
        this.name = name;
        this.numberImages = numberImages;
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

    @Override
    public String toString() {
        return "LotResponseDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName=" + userName +
                ", userCnpj=" + userCnpj +
                ", name=" + name +
                ", numberImages=" + numberImages +
                ", status='" + status + '\'' +
                '}';
    }
}

package com.lcsz.abcde.dtos.tourScreen;

import com.lcsz.abcde.enums.tourScreen.TourScreenEnum;

import java.util.UUID;

public class TourScreenResponseDto {
    private Long id;
    private UUID userId;
    private TourScreenEnum screen;
    private Boolean completed;

    public TourScreenResponseDto() {
    }

    public TourScreenResponseDto(Long id, UUID userId, TourScreenEnum screen, Boolean completed) {
        this.id = id;
        this.userId = userId;
        this.screen = screen;
        this.completed = completed;
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

    public TourScreenEnum getScreen() {
        return screen;
    }

    public void setScreen(TourScreenEnum screen) {
        this.screen = screen;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "TourScreenResponseDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", screen=" + screen +
                ", completed=" + completed +
                '}';
    }
}

package com.lcsz.abcde.dtos.tourScreen;

import com.lcsz.abcde.enums.tourScreen.TourScreenEnum;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class TourScreenCreateDto {
    @NotNull(message = "O campo userId não pode ser null")
    private UUID userId;
    @NotNull(message = "O campo screen não pode ser null")
    private TourScreenEnum screen;

    public TourScreenCreateDto() {
    }

    public TourScreenCreateDto(UUID userId, TourScreenEnum screen) {
        this.userId = userId;
        this.screen = screen;
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

    @Override
    public String toString() {
        return "TourScreenResponseDto{" +
                "userId=" + userId +
                ", screen=" + screen +
                '}';
    }
}

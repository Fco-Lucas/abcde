package com.lcsz.abcde.dtos.tourScreen;

import jakarta.validation.constraints.NotNull;

public class TourScreenUpdateDto {
    @NotNull(message = "O campo completed não pode ser null")
    private Boolean completed;

    public TourScreenUpdateDto() {
    }

    public TourScreenUpdateDto(Boolean completed) {
        this.completed = completed;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "TourScreenUpdateDto{" +
                "completed=" + completed +
                '}';
    }
}

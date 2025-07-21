package com.lcsz.abcde.dtos.lot;

import com.lcsz.abcde.enums.lot.LotStatus;

public class LotUpdateDto {
    private String name;
    private LotStatus status;

    public LotUpdateDto() {
    }

    public LotUpdateDto(String name, LotStatus status) {
        this.name = name;
        this.status = status;
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
        return "LotUpdateDto{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}

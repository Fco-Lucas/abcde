package com.lcsz.abcde.dtos.lotImage;

public class LotImageCreateDto {
    private Long lotId;

    public LotImageCreateDto() {
    }

    public LotImageCreateDto(Long lotId) {
        this.lotId = lotId;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    @Override
    public String toString() {
        return "LotImageCreateDto{" +
                "lotId=" + lotId +
                '}';
    }
}

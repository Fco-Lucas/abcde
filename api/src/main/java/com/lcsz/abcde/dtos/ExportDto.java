package com.lcsz.abcde.dtos;

import com.lcsz.abcde.dtos.lotImage.LotImageResponseDto;
import com.lcsz.abcde.enums.lot.LotStatus;

import java.util.List;
import java.util.UUID;

public class ExportDto {
    private Long lotId;
    private UUID lotUserId;
    private String lotUserName;
    private String lotName;
    private Integer lotNumberImages;
    private LotStatus lotStatus;
    private List<LotImageResponseDto> lotImages;

    public ExportDto() {
    }

    public ExportDto(Long lotId, UUID lotUserId, String lotUserName, String lotName, Integer lotNumberImages, LotStatus lotStatus, List<LotImageResponseDto> lotImages) {
        this.lotId = lotId;
        this.lotUserId = lotUserId;
        this.lotUserName = lotUserName;
        this.lotName = lotName;
        this.lotNumberImages = lotNumberImages;
        this.lotStatus = lotStatus;
        this.lotImages = lotImages;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public UUID getLotUserId() {
        return lotUserId;
    }

    public void setLotUserId(UUID lotUserId) {
        this.lotUserId = lotUserId;
    }

    public String getLotUserName() {
        return lotUserName;
    }

    public void setLotUserName(String lotUserName) {
        this.lotUserName = lotUserName;
    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public Integer getLotNumberImages() {
        return lotNumberImages;
    }

    public void setLotNumberImages(Integer lotNumberImages) {
        this.lotNumberImages = lotNumberImages;
    }

    public LotStatus getLotStatus() {
        return lotStatus;
    }

    public void setLotStatus(LotStatus lotStatus) {
        this.lotStatus = lotStatus;
    }

    public List<LotImageResponseDto> getLotImages() {
        return lotImages;
    }

    public void setLotImages(List<LotImageResponseDto> lotImages) {
        this.lotImages = lotImages;
    }

    @Override
    public String toString() {
        return "ExportDto{" +
                "lotId=" + lotId +
                ", lotUserId=" + lotUserId +
                ", lotUserName='" + lotUserName + '\'' +
                ", lotName='" + lotName + '\'' +
                ", lotNumberImages=" + lotNumberImages +
                ", lotStatus=" + lotStatus +
                ", lotImages=" + lotImages +
                '}';
    }
}

package com.lcsz.abcde.dtos.exportData;

import com.lcsz.abcde.enums.lot.LotType;

import java.util.List;

public class ExportVtbDataDto {
    private LotType type;
    private List<ExportVtbImagesDto> lotImages;

    public ExportVtbDataDto() {
    }

    public ExportVtbDataDto(LotType type, List<ExportVtbImagesDto> lotImages) {
        this.type = type;
        this.lotImages = lotImages;
    }

    public LotType getType() {
        return type;
    }

    public void setType(LotType type) {
        this.type = type;
    }

    public List<ExportVtbImagesDto> getLotImages() {
        return lotImages;
    }

    public void setLotImages(List<ExportVtbImagesDto> lotImages) {
        this.lotImages = lotImages;
    }

    @Override
    public String toString() {
        return "ExportVtbDataDto{" +
                "type=" + type +
                ", lotImages=" + lotImages +
                '}';
    }
}

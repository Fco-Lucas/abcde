package com.lcsz.abcde.dtos.exportData;

import com.lcsz.abcde.enums.lot.LotType;

import java.util.List;

public class ExportVtbDataDto {
    private String name;
    private LotType type;
    private List<ExportVtbImagesDto> lotImages;

    public ExportVtbDataDto() {
    }

    public ExportVtbDataDto(String name, LotType type, List<ExportVtbImagesDto> lotImages) {
        this.name = name;
        this.type = type;
        this.lotImages = lotImages;
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

    public List<ExportVtbImagesDto> getLotImages() {
        return lotImages;
    }

    public void setLotImages(List<ExportVtbImagesDto> lotImages) {
        this.lotImages = lotImages;
    }

    @Override
    public String toString() {
        return "ExportVtbDataDto{" +
                "name=" + name +
                "type=" + type +
                ", lotImages=" + lotImages +
                '}';
    }
}

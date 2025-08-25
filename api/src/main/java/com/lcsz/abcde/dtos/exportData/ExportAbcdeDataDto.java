package com.lcsz.abcde.dtos.exportData;

import com.lcsz.abcde.enums.lot.LotType;

import java.util.List;

public class ExportAbcdeDataDto {
    private String name;
    private LotType type;
    private List<ExportAbcdeImagesDto> lotImages;

    public ExportAbcdeDataDto() {
    }

    public ExportAbcdeDataDto(String name, LotType type, List<ExportAbcdeImagesDto> lotImages) {
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

    public List<ExportAbcdeImagesDto> getLotImages() {
        return lotImages;
    }

    public void setLotImages(List<ExportAbcdeImagesDto> lotImages) {
        this.lotImages = lotImages;
    }

    @Override
    public String toString() {
        return "ExportDataDto{" +
                "name=" + name +
                "type=" + type +
                ", lotImages=" + lotImages +
                '}';
    }
}

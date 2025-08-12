package com.lcsz.abcde.dtos;


import java.util.List;

public class ExportDataDto {
    private List<ExportDataImagesDto> lotImages;

    public ExportDataDto() {
    }

    public ExportDataDto(List<ExportDataImagesDto> lotImages) {
        this.lotImages = lotImages;
    }

    public List<ExportDataImagesDto> getLotImages() {
        return lotImages;
    }

    public void setLotImages(List<ExportDataImagesDto> lotImages) {
        this.lotImages = lotImages;
    }

    @Override
    public String toString() {
        return "ExportDataDto{" +
                "lotImages=" + lotImages +
                '}';
    }
}

package com.lcsz.abcde.mappers;

import com.lcsz.abcde.dtos.exportData.ExportAbcdeDataDto;
import com.lcsz.abcde.dtos.exportData.ExportAbcdeImagesDto;
import com.lcsz.abcde.dtos.exportData.ExportVtbDataDto;
import com.lcsz.abcde.dtos.exportData.ExportVtbImagesDto;
import com.lcsz.abcde.dtos.imageInfoAbcde.ImageInfoAbcdeResponseDto;
import com.lcsz.abcde.dtos.imageInfoVtb.ImageInfoVtbResponseDto;
import com.lcsz.abcde.dtos.lot.LotResponseDto;
import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionResponseDto;
import com.lcsz.abcde.models.LotImage;

import java.util.List;

public class ExportMapper {
    public static ExportAbcdeDataDto toExportAbcdeDto(LotResponseDto lot, List<ExportAbcdeImagesDto> lotImages) {
        return new ExportAbcdeDataDto(lot.getType(), lotImages);
    }

    public static ExportAbcdeImagesDto toExportAbcdeImageDto(LotImage lotImage, ImageInfoAbcdeResponseDto imageInfo, List<LotImageQuestionResponseDto> questions) {
        return new ExportAbcdeImagesDto(
            lotImage.getMatricula(),
            imageInfo.getCodigoEscola(),
            imageInfo.getAno(),
            imageInfo.getGrauSerie(),
            imageInfo.getTurno(),
            imageInfo.getTurma(),
            imageInfo.getEtapa(),
            imageInfo.getProva(),
            imageInfo.getGabarito(),
            lotImage.getPresenca(),
            questions
        );
    }

    public static ExportVtbDataDto toExportVtbDto(LotResponseDto lot, List<ExportVtbImagesDto> lotImages) {
        return new ExportVtbDataDto(lot.getType(), lotImages);
    }

    public static ExportVtbImagesDto toExportVtbImageDto(LotImage lotImage, ImageInfoVtbResponseDto imageInfo, List<LotImageQuestionResponseDto> questions) {
        return new ExportVtbImagesDto(
                lotImage.getMatricula(),
                imageInfo.getVtbCodigo(),
                imageInfo.getVtbFracao(),
                imageInfo.getFaseGab(),
                imageInfo.getProva(),
                lotImage.getPresenca(),
                questions
        );
    }
}

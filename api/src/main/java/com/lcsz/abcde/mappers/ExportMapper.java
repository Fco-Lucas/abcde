package com.lcsz.abcde.mappers;

import com.lcsz.abcde.dtos.ExportDataDto;
import com.lcsz.abcde.dtos.ExportDataImagesDto;
import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionResponseDto;
import com.lcsz.abcde.models.LotImage;

import java.util.List;

public class ExportMapper {
    public static ExportDataDto toDto(List<ExportDataImagesDto> lotImages) {
        return new ExportDataDto(
                lotImages
        );
    }

    public static ExportDataImagesDto toImageDto(LotImage lotImage, List<LotImageQuestionResponseDto> questions) {
        return new ExportDataImagesDto(
            lotImage.getMatricula(),
            lotImage.getCodigoEscola(),
            lotImage.getAno(),
            lotImage.getGrauSerie(),
            lotImage.getTurno(),
            lotImage.getTurma(),
            lotImage.getEtapa(),
            lotImage.getProva(),
            lotImage.getGabarito(),
            lotImage.getPresenca(),
            questions
        );
    }
}

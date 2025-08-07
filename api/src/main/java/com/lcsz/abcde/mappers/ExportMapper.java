package com.lcsz.abcde.mappers;

import com.lcsz.abcde.dtos.ExportDto;
import com.lcsz.abcde.dtos.lot.LotResponseDto;
import com.lcsz.abcde.dtos.lotImage.LotImageResponseDto;

import java.util.List;

public class ExportMapper {
    public static ExportDto toDto(LotResponseDto lot, List<LotImageResponseDto> lotImages) {
        return new ExportDto(
                lot.getId(),
                lot.getUserId(),
                lot.getUserName(),
                lot.getName(),
                lot.getNumberImages(),
                lot.getStatus(),
                lotImages
        );
    }
}

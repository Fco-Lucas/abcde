package com.lcsz.abcde.mappers;

import com.lcsz.abcde.dtos.lotImage.LotImageResponseDto;
import com.lcsz.abcde.models.LotImage;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class LotImageMapper {
    public static LotImageResponseDto toDto(LotImage lotImage) {
        return new ModelMapper().map(lotImage, LotImageResponseDto.class);
    }

    public static List<LotImageResponseDto> toListDto(List<LotImage> lotImages) {
        return lotImages.stream().map(lotImage -> toDto(lotImage)).collect(Collectors.toList());
    }
}

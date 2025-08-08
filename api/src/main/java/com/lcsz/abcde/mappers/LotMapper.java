package com.lcsz.abcde.mappers;

import com.lcsz.abcde.dtos.lot.LotResponseDto;
import com.lcsz.abcde.models.Lot;
import com.lcsz.abcde.repositorys.projection.LotProjection;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class LotMapper {
    public static LotResponseDto toPageableDto(LotProjection projection) {
        return new ModelMapper().map(projection, LotResponseDto.class);
    }

    public static LotResponseDto toDto(Lot lot) {
        return new ModelMapper().map(lot, LotResponseDto.class);
    }

    public static List<LotResponseDto> toListDto(List<Lot> lots) {
        return lots.stream().map(lot -> toDto(lot)).collect(Collectors.toList());
    }
}

package com.lcsz.abcde.mappers;

import com.lcsz.abcde.dtos.tourScreen.TourScreenCreateDto;
import com.lcsz.abcde.dtos.tourScreen.TourScreenResponseDto;
import com.lcsz.abcde.models.TourScreen;
import org.modelmapper.ModelMapper;

public class TourScreenMapper {
    private static final ModelMapper mapper = new ModelMapper();

    static {
        // Configura o mapper para ignorar o ID ao criar uma nova entidade
        mapper.typeMap(TourScreenCreateDto.class, TourScreen.class)
                .addMappings(mapper -> mapper.skip(TourScreen::setId)
        );
    }

    public static TourScreen toEntityCreate(TourScreenCreateDto createDto) {
        return mapper.map(createDto, TourScreen.class);
    }

    public static TourScreenResponseDto toDto(TourScreen tourScreen) {
        return mapper.map(tourScreen, TourScreenResponseDto.class);
    }
}

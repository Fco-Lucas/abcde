package com.lcsz.abcde.mappers;

import com.lcsz.abcde.dtos.imageInfoAbcde.ImageInfoAbcdeCreateDto;
import com.lcsz.abcde.dtos.imageInfoAbcde.ImageInfoAbcdeResponseDto;
import com.lcsz.abcde.models.ImageInfoAbcde;
import org.modelmapper.ModelMapper;

public class ImageInfoAbcdeMapper {
    private static final ModelMapper mapper = new ModelMapper();

    public static ImageInfoAbcde toEntityCreate(ImageInfoAbcdeCreateDto createDto) {
        return mapper.map(createDto, ImageInfoAbcde.class);
    }

    public static ImageInfoAbcdeResponseDto toDto(ImageInfoAbcde imageInfoAbcde) {
        return mapper.map(imageInfoAbcde, ImageInfoAbcdeResponseDto.class);
    }
}

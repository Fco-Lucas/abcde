package com.lcsz.abcde.mappers;

import com.lcsz.abcde.dtos.imageInfoVtb.ImageInfoVtbCreateDto;
import com.lcsz.abcde.dtos.imageInfoVtb.ImageInfoVtbResponseDto;
import com.lcsz.abcde.models.ImageInfoVtb;
import org.modelmapper.ModelMapper;

public class ImageInfoVtbMapper {
    private static final ModelMapper mapper = new ModelMapper();

    public static ImageInfoVtb toEntityCreate(ImageInfoVtbCreateDto createDto) {
        return mapper.map(createDto, ImageInfoVtb.class);
    }

    public static ImageInfoVtbResponseDto toDto(ImageInfoVtb imageInfoVtb) {
        return mapper.map(imageInfoVtb, ImageInfoVtbResponseDto.class);
    }
}

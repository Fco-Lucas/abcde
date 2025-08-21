package com.lcsz.abcde.mappers;

import com.lcsz.abcde.dtos.imageInfoVtb.ImageInfoVtbCreateDto;
import com.lcsz.abcde.dtos.imageInfoVtb.ImageInfoVtbResponseDto;
import com.lcsz.abcde.models.ImageInfoVtb;
import org.modelmapper.ModelMapper;

public class ImageInfoVtbMapper {
    private static final ModelMapper mapper = new ModelMapper();

    static {
        // Configura o mapper para ignorar o ID ao criar uma nova entidade
        mapper.typeMap(ImageInfoVtbCreateDto.class, ImageInfoVtb.class)
                .addMappings(mapper -> mapper.skip(ImageInfoVtb::setId));
    }

    public static ImageInfoVtb toEntityCreate(ImageInfoVtbCreateDto createDto) {
        return mapper.map(createDto, ImageInfoVtb.class);
    }

    public static ImageInfoVtbResponseDto toDto(ImageInfoVtb imageInfoVtb) {
        return mapper.map(imageInfoVtb, ImageInfoVtbResponseDto.class);
    }
}

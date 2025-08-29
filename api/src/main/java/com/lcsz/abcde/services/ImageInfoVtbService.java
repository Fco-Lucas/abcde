package com.lcsz.abcde.services;

import com.lcsz.abcde.dtos.imageInfoVtb.ImageInfoVtbCreateDto;
import com.lcsz.abcde.dtos.imageInfoVtb.ImageInfoVtbResponseDto;
import com.lcsz.abcde.exceptions.customExceptions.EntityNotFoundException;
import com.lcsz.abcde.mappers.ImageInfoVtbMapper;
import com.lcsz.abcde.models.ImageInfoVtb;
import com.lcsz.abcde.repositorys.ImageInfoVtbRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImageInfoVtbService {
    private final ImageInfoVtbRepository repository;

    public ImageInfoVtbService(ImageInfoVtbRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = false)
    public ImageInfoVtbResponseDto create(ImageInfoVtbCreateDto dto) {
        ImageInfoVtb entity = ImageInfoVtbMapper.toEntityCreate(dto);
        ImageInfoVtb saved = this.repository.save(entity);
        return ImageInfoVtbMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public ImageInfoVtbResponseDto getByImageId(Long lotImageId) {
        ImageInfoVtb imageInfoVtb = this.repository.findByLotImageId(lotImageId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Informações do VTB do gabarito com id %s não encontradas", lotImageId))
        );
        return ImageInfoVtbMapper.toDto(imageInfoVtb);
    }
}

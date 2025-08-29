package com.lcsz.abcde.services;

import com.lcsz.abcde.dtos.imageInfoAbcde.ImageInfoAbcdeCreateDto;
import com.lcsz.abcde.dtos.imageInfoAbcde.ImageInfoAbcdeResponseDto;
import com.lcsz.abcde.exceptions.customExceptions.EntityNotFoundException;
import com.lcsz.abcde.mappers.ImageInfoAbcdeMapper;
import com.lcsz.abcde.models.ImageInfoAbcde;
import com.lcsz.abcde.repositorys.ImageInfoAbcdeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImageInfoAbcdeService {
    private final ImageInfoAbcdeRepository repository;

    public ImageInfoAbcdeService(ImageInfoAbcdeRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = false)
    public ImageInfoAbcdeResponseDto create(ImageInfoAbcdeCreateDto dto) {
        ImageInfoAbcde entity = ImageInfoAbcdeMapper.toEntityCreate(dto);
        ImageInfoAbcde saved = this.repository.save(entity);
        return ImageInfoAbcdeMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public ImageInfoAbcdeResponseDto getByImageId(Long lotImageId) {
        ImageInfoAbcde imageInfoAbcde = this.repository.findByLotImageId(lotImageId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Informações do ABCDE do gabarito com id %s não encontradas", lotImageId))
        );
        return ImageInfoAbcdeMapper.toDto(imageInfoAbcde);
    }
}

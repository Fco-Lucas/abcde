package com.lcsz.abcde.services;

import com.lcsz.abcde.dtos.tourScreen.TourScreenCreateDto;
import com.lcsz.abcde.dtos.tourScreen.TourScreenResponseDto;
import com.lcsz.abcde.dtos.tourScreen.TourScreenUpdateDto;
import com.lcsz.abcde.enums.tourScreen.TourScreenEnum;
import com.lcsz.abcde.exceptions.customExceptions.EntityNotFoundException;
import com.lcsz.abcde.mappers.TourScreenMapper;
import com.lcsz.abcde.models.TourScreen;
import com.lcsz.abcde.repositorys.TourScreenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class TourScreenService {
    private final TourScreenRepository repository;

    TourScreenService(TourScreenRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = false)
    public TourScreenResponseDto create(TourScreenCreateDto dto) {
        TourScreen entity = TourScreenMapper.toEntityCreate(dto);
        TourScreen saved = this.repository.save(entity);
        return TourScreenMapper.toDto(saved);
    }

    @Transactional(readOnly = false)
    public TourScreenResponseDto getByUserIdAndScreen(UUID userId, TourScreenEnum screen) {
        Optional<TourScreen> tourScreen = this.repository.findByUserIdAndScreen(userId, screen);
        if(tourScreen.isPresent()) return TourScreenMapper.toDto(tourScreen.get());

        // Se não existir, cria
        TourScreenCreateDto createDto = new TourScreenCreateDto(userId, screen);
        return this.create(createDto);
    }

    @Transactional(readOnly = true)
    public TourScreen getById(Long id) {
        return this.repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Informações não encontradas com base no ID: %s", id))
        );
    }

    @Transactional(readOnly = false)
    public void update(Long id, TourScreenUpdateDto dto) {
        TourScreen entity = this.getById(id);
        if(dto.getCompleted() != null) entity.setCompleted(dto.getCompleted());

        this.repository.save(entity);
    }
}

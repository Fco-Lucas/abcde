package com.lcsz.abcde.services;

import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionCreateDto;
import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionResponseDto;
import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionUpdateDto;
import com.lcsz.abcde.exceptions.customExceptions.EntityExistsException;
import com.lcsz.abcde.exceptions.customExceptions.EntityNotFoundException;
import com.lcsz.abcde.mappers.LotImageQuestionMapper;
import com.lcsz.abcde.models.LotImageQuestion;
import com.lcsz.abcde.repositorys.LotImageQuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LotImageQuestionService {
    private final LotImageQuestionRepository lotImageQuestionRepository;
    LotImageQuestionService(LotImageQuestionRepository lotImageQuestionRepository) {
        this.lotImageQuestionRepository = lotImageQuestionRepository;
    }

    private Boolean numberExistisInOption(Long image_id, Integer number) {
        Optional<LotImageQuestion> question = this.lotImageQuestionRepository.findByImageIdAndNumber(image_id, number);
        return question.isPresent();
    }

    @Transactional(readOnly = false)
    public LotImageQuestionResponseDto create(LotImageQuestionCreateDto dto) {
        // Verifica se o número da questão já existe na imagem
        if(this.numberExistisInOption(dto.getImageId(), dto.getNumber()))
            throw new EntityExistsException(String.format("Questão '%s' já pertence á imagem", dto.getNumber()));

        LotImageQuestion question = new LotImageQuestion();
        question.setImageId(dto.getImageId());
        question.setNumber(dto.getNumber());
        question.setAlternative(dto.getAlternative());

        LotImageQuestion saved = this.lotImageQuestionRepository.save(question);

        return LotImageQuestionMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<LotImageQuestionResponseDto> getAllByImageId(Long imageId) {
        List<LotImageQuestion> questions = this.lotImageQuestionRepository.findAllByImageId(imageId);
        return LotImageQuestionMapper.toListDto(questions);
    }

    @Transactional(readOnly = true)
    public LotImageQuestion getById(Long id) {
        return this.lotImageQuestionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Questão com id '%s' não encontrada", id))
        );
    }

    @Transactional(readOnly = true)
    public LotImageQuestionResponseDto getByIdDto(Long id) {
        LotImageQuestion question = this.getById(id);
        return LotImageQuestionMapper.toDto(question);
    }

    @Transactional(readOnly = false)
    public void update(Long id, LotImageQuestionUpdateDto dto) {
        LotImageQuestion question = this.getById(id);
        if(dto.getAlternative() != null) question.setAlternative(dto.getAlternative());
        this.lotImageQuestionRepository.save(question);
    }

    @Transactional(readOnly = false)
    public void delete(Long id) {
        LotImageQuestion question = this.getById(id);
        this.lotImageQuestionRepository.delete(question);
    }
}
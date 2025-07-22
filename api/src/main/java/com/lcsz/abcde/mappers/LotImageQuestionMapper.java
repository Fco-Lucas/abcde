package com.lcsz.abcde.mappers;

import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionResponseDto;
import com.lcsz.abcde.models.LotImageQuestion;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class LotImageQuestionMapper {
    public static LotImageQuestionResponseDto toDto(LotImageQuestion lotImageQuestion) {
        return new ModelMapper().map(lotImageQuestion, LotImageQuestionResponseDto.class);
    }

    public static List<LotImageQuestionResponseDto> toListDto(List<LotImageQuestion> lotImageQuestions) {
        return lotImageQuestions.stream().map(lotImageQuestion -> toDto(lotImageQuestion)).collect(Collectors.toList());
    }
}

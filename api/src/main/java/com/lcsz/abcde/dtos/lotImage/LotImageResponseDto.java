package com.lcsz.abcde.dtos.lotImage;

import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionResponseDto;
import com.lcsz.abcde.enums.lot_image.LotImageStatus;

import java.util.List;

public class LotImageResponseDto {
    private Long id;
    private Long lotId;
    private String key;
    private LotImageStatus status;
    private List<LotImageQuestionResponseDto> questions;

    public LotImageResponseDto() {
    }

    public LotImageResponseDto(Long id, Long lotId, String key, LotImageStatus status, List<LotImageQuestionResponseDto> questions) {
        this.id = id;
        this.lotId = lotId;
        this.key = key;
        this.status = status;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LotImageStatus getStatus() {
        return status;
    }

    public void setStatus(LotImageStatus status) {
        this.status = status;
    }

    public List<LotImageQuestionResponseDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<LotImageQuestionResponseDto> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "LotImageResponseDto{" +
                "id=" + id +
                ", lotId=" + lotId +
                ", key='" + key + '\'' +
                ", status='" + status + '\'' +
                ", questions=" + questions +
                '}';
    }
}

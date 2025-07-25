package com.lcsz.abcde.dtos.lotImage;

public class LotImageUpdateQuestionDto {
    private Long lotImageQuestionId;
    private String alternative;

    public LotImageUpdateQuestionDto() {
    }

    public LotImageUpdateQuestionDto(Long lotImageQuestionId, String alternative) {
        this.lotImageQuestionId = lotImageQuestionId;
        this.alternative = alternative;
    }

    public Long getLotImageQuestionId() {
        return lotImageQuestionId;
    }

    public void setLotImageQuestionId(Long lotImageQuestionId) {
        this.lotImageQuestionId = lotImageQuestionId;
    }

    public String getAlternative() {
        return alternative;
    }

    public void setAlternative(String alternative) {
        this.alternative = alternative;
    }

    @Override
    public String toString() {
        return "LotImageUpdateQuestionDto{" +
                "lotImageQuestionId=" + lotImageQuestionId +
                ", alternative='" + alternative + '\'' +
                '}';
    }
}

package com.lcsz.abcde.dtos.lotImageQuestion;

public class LotImageQuestionUpdateDto {
    private String alternative;

    public LotImageQuestionUpdateDto() {
    }

    public LotImageQuestionUpdateDto(String alternative) {
        this.alternative = alternative;
    }

    public String getAlternative() {
        return alternative;
    }

    public void setAlternative(String alternative) {
        this.alternative = alternative;
    }

    @Override
    public String toString() {
        return "LotImageQuestionUpdateDto{" +
                "alternative=" + alternative +
                '}';
    }
}

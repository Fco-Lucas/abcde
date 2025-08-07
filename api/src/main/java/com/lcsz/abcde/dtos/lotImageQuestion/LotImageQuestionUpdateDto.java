package com.lcsz.abcde.dtos.lotImageQuestion;

public class LotImageQuestionUpdateDto {
    private String alternative;
    private String previousAlternative;

    public LotImageQuestionUpdateDto() {
    }

    public LotImageQuestionUpdateDto(String alternative, String previousAlternative) {
        this.alternative = alternative;
        this.previousAlternative = previousAlternative;
    }

    public String getAlternative() {
        return alternative;
    }

    public void setAlternative(String alternative) {
        this.alternative = alternative;
    }

    public String getPreviousAlternative() {
        return previousAlternative;
    }

    public void setPreviousAlternative(String previousAlternative) {
        this.previousAlternative = previousAlternative;
    }

    @Override
    public String toString() {
        return "LotImageQuestionUpdateDto{" +
                "alternative=" + alternative +
                '}';
    }
}

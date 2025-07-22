package com.lcsz.abcde.dtos.lotImageQuestion;

public class LotImageQuestionResponseDto {
    private Integer number;
    private String alternative;

    public LotImageQuestionResponseDto() {
    }

    public LotImageQuestionResponseDto(Integer number, String alternative) {
        this.number = number;
        this.alternative = alternative;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getAlternative() {
        return alternative;
    }

    public void setAlternative(String alternative) {
        this.alternative = alternative;
    }

    @Override
    public String toString() {
        return "lotImageQuestionCreateDto{" +
                "number=" + number +
                ", alternative='" + alternative + '\'' +
                '}';
    }
}

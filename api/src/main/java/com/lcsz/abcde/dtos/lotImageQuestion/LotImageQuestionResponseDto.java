package com.lcsz.abcde.dtos.lotImageQuestion;

public class LotImageQuestionResponseDto {
    private Long id;
    private Integer number;
    private String alternative;

    public LotImageQuestionResponseDto() {
    }

    public LotImageQuestionResponseDto(Long id, Integer number, String alternative) {
        this.id = id;
        this.number = number;
        this.alternative = alternative;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                "id=" + id +
                ", number='" + number + '\'' +
                ", alternative='" + alternative + '\'' +
                '}';
    }
}

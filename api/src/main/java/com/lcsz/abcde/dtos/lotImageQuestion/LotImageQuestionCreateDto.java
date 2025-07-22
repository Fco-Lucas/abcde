package com.lcsz.abcde.dtos.lotImageQuestion;

public class LotImageQuestionCreateDto {
    private Long imageId;
    private Integer number;
    private String alternative;

    public LotImageQuestionCreateDto() {
    }

    public LotImageQuestionCreateDto(Long imageId, Integer number, String alternative) {
        this.imageId = imageId;
        this.number = number;
        this.alternative = alternative;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
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
                "imageId=" + imageId +
                ", number=" + number +
                ", alternative='" + alternative + '\'' +
                '}';
    }
}

package com.lcsz.abcde.dtos.lotImage;

public class LotImageUpdateQuestionDto {
    private Long lotImageQuestionId;
    private String alternative;
    private String previousAlternative;

    public LotImageUpdateQuestionDto() {
    }

    public LotImageUpdateQuestionDto(Long lotImageQuestionId, String alternative, String previousAlternative) {
        this.lotImageQuestionId = lotImageQuestionId;
        this.alternative = alternative;
        this.previousAlternative = previousAlternative;
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

    public String getPreviousAlternative() {
        return previousAlternative;
    }

    public void setPreviousAlternative(String previousAlternative) {
        this.previousAlternative = previousAlternative;
    }

    @Override
    public String toString() {
        return "LotImageUpdateQuestionDto{" +
                "lotImageQuestionId=" + lotImageQuestionId +
                ", alternative='" + alternative + '\'' +
                ", previousAlternative='" + previousAlternative +
                '}';
    }
}

package com.lcsz.abcde.dtos.lotImage;

import com.lcsz.abcde.dtos.imageInfoAbcde.ImageInfoAbcdeResponseDto;
import com.lcsz.abcde.dtos.imageInfoVtb.ImageInfoVtbResponseDto;
import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionResponseDto;
import com.lcsz.abcde.enums.lot_image.LotImageStatus;

import java.time.LocalDateTime;
import java.util.List;

public class LotImageResponseDto {
    private Long id;
    private Long lotId;
    private String url;
    private Integer matricula;
    private String nomeAluno;
    private Integer presenca;
    private Integer qtdQuestoes;
    private Boolean haveModification;
    private LotImageStatus status;
    private LocalDateTime createdAt;
    private ImageInfoAbcdeResponseDto abcdeInfo;
    private ImageInfoVtbResponseDto vtbInfo;
    private List<LotImageQuestionResponseDto> questions;

    public LotImageResponseDto() {
    }

    public LotImageResponseDto(Long id, Long lotId, String url, Integer matricula, String nomeAluno, Integer presenca, Integer qtdQuestoes, Boolean haveModification, LotImageStatus status, LocalDateTime createdAt, ImageInfoAbcdeResponseDto abcdeInfo, ImageInfoVtbResponseDto vtbInfo, List<LotImageQuestionResponseDto> questions) {
        this.id = id;
        this.lotId = lotId;
        this.url = url;
        this.matricula = matricula;
        this.nomeAluno = nomeAluno;
        this.presenca = presenca;
        this.qtdQuestoes = qtdQuestoes;
        this.haveModification = haveModification;
        this.status = status;
        this.createdAt = createdAt;
        this.abcdeInfo = abcdeInfo;
        this.vtbInfo = vtbInfo;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public Integer getPresenca() {
        return presenca;
    }

    public void setPresenca(Integer presenca) {
        this.presenca = presenca;
    }

    public Integer getQtdQuestoes() {
        return qtdQuestoes;
    }

    public void setQtdQuestoes(Integer qtdQuestoes) {
        this.qtdQuestoes = qtdQuestoes;
    }

    public Boolean getHaveModification() {
        return haveModification;
    }

    public void setHaveModification(Boolean haveModification) {
        this.haveModification = haveModification;
    }

    public LotImageStatus getStatus() {
        return status;
    }

    public void setStatus(LotImageStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ImageInfoAbcdeResponseDto getAbcdeInfo() {
        return abcdeInfo;
    }

    public void setAbcdeInfo(ImageInfoAbcdeResponseDto abcdeInfo) {
        this.abcdeInfo = abcdeInfo;
    }

    public ImageInfoVtbResponseDto getVtbInfo() {
        return vtbInfo;
    }

    public void setVtbInfo(ImageInfoVtbResponseDto vtbInfo) {
        this.vtbInfo = vtbInfo;
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
                ", url='" + url + '\'' +
                ", matricula=" + matricula +
                ", nomeAluno='" + nomeAluno + '\'' +
                ", presenca=" + presenca +
                ", qtdQuestoes=" + qtdQuestoes +
                ", haveModification=" + haveModification +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", abcdeInfo=" + abcdeInfo +
                ", vtbInfo=" + vtbInfo +
                ", questions=" + questions +
                '}';
    }
}

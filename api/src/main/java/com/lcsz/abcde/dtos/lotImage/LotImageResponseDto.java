package com.lcsz.abcde.dtos.lotImage;

import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionResponseDto;
import com.lcsz.abcde.enums.lot_image.LotImageStatus;

import java.time.LocalDateTime;
import java.util.List;

public class LotImageResponseDto {
    private Long id;
    private Long lotId;
    private String url;
    private String originalName;
    private Integer matricula;
    private String nomeAluno;
    private String etapa;
    private Integer prova;
    private String gabarito;
    private Integer presenca;
    private Integer qtdQuestoes;
    private Boolean haveModification;
    private LotImageStatus status;
    private LocalDateTime createdAt;
    private List<LotImageQuestionResponseDto> questions;

    public LotImageResponseDto() {
    }

    public LotImageResponseDto(Long id, Long lotId, String url, String originalName, Integer matricula, String nomeAluno, String etapa, Integer prova, String gabarito, Integer presenca, Integer qtdQuestoes, Boolean haveModification, LotImageStatus status, LocalDateTime createdAt, List<LotImageQuestionResponseDto> questions) {
        this.id = id;
        this.lotId = lotId;
        this.url = url;
        this.originalName = originalName;
        this.matricula = matricula;
        this.nomeAluno = nomeAluno;
        this.etapa = etapa;
        this.prova = prova;
        this.gabarito = gabarito;
        this.presenca = presenca;
        this.qtdQuestoes = qtdQuestoes;
        this.haveModification = haveModification;
        this.status = status;
        this.createdAt = createdAt;
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

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
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

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public Integer getProva() {
        return prova;
    }

    public void setProva(Integer prova) {
        this.prova = prova;
    }

    public String getGabarito() {
        return gabarito;
    }

    public void setGabarito(String gabarito) {
        this.gabarito = gabarito;
    }

    public Integer getPresenca() {
        return presenca;
    }

    public void setPresenca(Integer presenca) {
        this.presenca = presenca;
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
                ", url=" + url +
                ", originalName='" + originalName + '\'' +
                ", matricula=" + matricula +
                ", etapa='" + etapa + '\'' +
                ", prova=" + prova +
                ", gabarito='" + gabarito + '\'' +
                ", presenca=" + presenca +
                ", qtdQuestoes=" + qtdQuestoes +
                ", haveModification=" + haveModification +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", questions=" + questions +
                '}';
    }
}

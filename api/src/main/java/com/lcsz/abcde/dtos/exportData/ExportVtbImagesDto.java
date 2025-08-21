package com.lcsz.abcde.dtos.exportData;

import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionResponseDto;

import java.util.List;

public class ExportVtbImagesDto {
    private Integer matricula;
    private String vtbCodigo;
    private String vtbFracao;
    private Integer faseGab;
    private Integer prova;
    private Integer presenca;
    private List<LotImageQuestionResponseDto> questions;

    public ExportVtbImagesDto() {
    }

    public ExportVtbImagesDto(Integer matricula, String vtbCodigo, String vtbFracao, Integer faseGab, Integer prova, Integer presenca, List<LotImageQuestionResponseDto> questions) {
        this.matricula = matricula;
        this.vtbCodigo = vtbCodigo;
        this.vtbFracao = vtbFracao;
        this.faseGab = faseGab;
        this.prova = prova;
        this.presenca = presenca;
        this.questions = questions;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getVtbCodigo() {
        return vtbCodigo;
    }

    public void setVtbCodigo(String vtbCodigo) {
        this.vtbCodigo = vtbCodigo;
    }

    public String getVtbFracao() {
        return vtbFracao;
    }

    public void setVtbFracao(String vtbFracao) {
        this.vtbFracao = vtbFracao;
    }

    public Integer getFaseGab() {
        return faseGab;
    }

    public void setFaseGab(Integer faseGab) {
        this.faseGab = faseGab;
    }

    public Integer getProva() {
        return prova;
    }

    public void setProva(Integer prova) {
        this.prova = prova;
    }

    public Integer getPresenca() {
        return presenca;
    }

    public void setPresenca(Integer presenca) {
        this.presenca = presenca;
    }

    public List<LotImageQuestionResponseDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<LotImageQuestionResponseDto> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "ExportVtbImagesDto{" +
                "matricula=" + matricula +
                ", vtbCodigo='" + vtbCodigo + '\'' +
                ", vtbFracao='" + vtbFracao + '\'' +
                ", faseGab=" + faseGab +
                ", prova=" + prova +
                ", presenca=" + presenca +
                ", questions=" + questions +
                '}';
    }
}

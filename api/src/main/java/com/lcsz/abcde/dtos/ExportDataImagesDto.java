package com.lcsz.abcde.dtos;

import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionResponseDto;

import java.util.List;

public class ExportDataImagesDto {
    private Integer matricula;
    private Integer codigoEscola;
    private Integer ano;
    private Integer grauSerie;
    private String turno;
    private Integer turma;
    private String etapa;
    private Integer prova;
    private String gabarito;
    private Integer presenca;
    private List<LotImageQuestionResponseDto> questions;

    public ExportDataImagesDto() {
    }

    public ExportDataImagesDto(Integer matricula, Integer codigoEscola, Integer ano, Integer grauSerie, String turno, Integer turma, String etapa, Integer prova, String gabarito, Integer presenca, List<LotImageQuestionResponseDto> questions) {
        this.matricula = matricula;
        this.codigoEscola = codigoEscola;
        this.ano = ano;
        this.grauSerie = grauSerie;
        this.turno = turno;
        this.turma = turma;
        this.etapa = etapa;
        this.prova = prova;
        this.gabarito = gabarito;
        this.presenca = presenca;
        this.questions = questions;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Integer getCodigoEscola() {
        return codigoEscola;
    }

    public void setCodigoEscola(Integer codigoEscola) {
        this.codigoEscola = codigoEscola;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getGrauSerie() {
        return grauSerie;
    }

    public void setGrauSerie(Integer grauSerie) {
        this.grauSerie = grauSerie;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Integer getTurma() {
        return turma;
    }

    public void setTurma(Integer turma) {
        this.turma = turma;
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

    public List<LotImageQuestionResponseDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<LotImageQuestionResponseDto> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "ExportDataImagesDto{" +
                "matricula=" + matricula +
                ", codigoEscola=" + codigoEscola +
                ", ano=" + ano +
                ", grauSerie=" + grauSerie +
                ", turno='" + turno + '\'' +
                ", turma=" + turma +
                ", etapa='" + etapa + '\'' +
                ", prova=" + prova +
                ", gabarito=" + gabarito +
                ", presenca=" + presenca +
                ", questions=" + questions +
                '}';
    }
}

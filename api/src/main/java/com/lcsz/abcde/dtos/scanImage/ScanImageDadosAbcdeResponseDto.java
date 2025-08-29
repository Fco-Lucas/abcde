package com.lcsz.abcde.dtos.scanImage;

public class ScanImageDadosAbcdeResponseDto {
    private Integer matricula;
    private String nomeAluno;
    private String etapa;
    private Integer prova;
    private String gabarito;
    private Integer qtdQuestoes;
    private Integer presenca;
    private Integer codigoEscola;
    private Integer ano;
    private Integer grauSerie;
    private String turno;
    private Integer turma;

    public ScanImageDadosAbcdeResponseDto() {
    }

    public ScanImageDadosAbcdeResponseDto(Integer matricula, String nomeAluno, String etapa, Integer prova, String gabarito, Integer qtdQuestoes, Integer presenca, Integer codigoEscola, Integer ano, Integer grauSerie, String turno, Integer turma) {
        this.matricula = matricula;
        this.nomeAluno = nomeAluno;
        this.etapa = etapa;
        this.prova = prova;
        this.gabarito = gabarito;
        this.qtdQuestoes = qtdQuestoes;
        this.presenca = presenca;
        this.codigoEscola = codigoEscola;
        this.ano = ano;
        this.grauSerie = grauSerie;
        this.turno = turno;
        this.turma = turma;
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

    public Integer getQtdQuestoes() {
        return qtdQuestoes;
    }

    public void setQtdQuestoes(Integer qtdQuestoes) {
        this.qtdQuestoes = qtdQuestoes;
    }

    public Integer getPresenca() {
        return presenca;
    }

    public void setPresenca(Integer presenca) {
        this.presenca = presenca;
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

    @Override
    public String toString() {
        return "ScanImageDadosAbcdeResponseDto{" +
                "matricula=" + matricula +
                ", nomeAluno='" + nomeAluno + '\'' +
                ", etapa='" + etapa + '\'' +
                ", prova=" + prova +
                ", gabarito='" + gabarito + '\'' +
                ", qtdQuestoes=" + qtdQuestoes +
                ", presenca=" + presenca +
                ", codigoEscola=" + codigoEscola +
                ", ano=" + ano +
                ", grauSerie=" + grauSerie +
                ", turno='" + turno + '\'' +
                ", turma=" + turma +
                '}';
    }
}

package com.lcsz.abcde.dtos;

public class ScanImageDadosResponseDto {
    private Integer matricula;
    private String nomeAluno;
    private String etapa;
    private Integer prova;
    private String gabarito;
    private Integer qtdQuestoes;
    private Integer presenca;

    public ScanImageDadosResponseDto() {
    }

    public ScanImageDadosResponseDto(Integer matricula, String nomeAluno, String etapa, Integer prova, String gabarito, Integer qtdQuestoes, Integer presenca) {
        this.matricula = matricula;
        this.nomeAluno = nomeAluno;
        this.etapa = etapa;
        this.prova = prova;
        this.gabarito = gabarito;
        this.qtdQuestoes = qtdQuestoes;
        this.presenca = presenca;
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

    public Integer getQtdQuestoes() {
        return qtdQuestoes;
    }

    public void setQtdQuestoes(Integer qtdQuestoes) {
        this.qtdQuestoes = qtdQuestoes;
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

    @Override
    public String toString() {
        return "ScanImageDadosResponseDto{" +
                "matricula='" + matricula + '\'' +
                ", nomeAluno='" + nomeAluno + '\'' +
                ", etapa='" + etapa + '\'' +
                ", prova=" + prova +
                ", qtdQuestoes='" + qtdQuestoes + '\'' +
                ", gabarito='" + gabarito + '\'' +
                ", presenca=" + presenca +
                '}';
    }
}

package com.lcsz.abcde.dtos.scanImage;

public class ScanImageDadosVtbResponseDto {
    private Integer matricula;
    private String vtbCodigo;
    private String vtbFracao;
    private Integer faseGab;
    private Integer prova;
    private String nomeAluno;
    private Integer qtdQuestoes;
    private Integer presenca;

    public ScanImageDadosVtbResponseDto() {
    }

    public ScanImageDadosVtbResponseDto(Integer matricula, String vtbCodigo, String vtbFracao, Integer faseGab, Integer prova, String nomeAluno, Integer qtdQuestoes, Integer presenca) {
        this.matricula = matricula;
        this.vtbCodigo = vtbCodigo;
        this.vtbFracao = vtbFracao;
        this.faseGab = faseGab;
        this.prova = prova;
        this.nomeAluno = nomeAluno;
        this.qtdQuestoes = qtdQuestoes;
        this.presenca = presenca;
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

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
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

    @Override
    public String toString() {
        return "ScanImageDadosVtbResponseDto{" +
                "matricula='" + matricula + '\'' +
                ", vtbCodigo='" + vtbCodigo + '\'' +
                ", vtbFracao='" + vtbFracao + '\'' +
                ", faseGab=" + faseGab +
                ", prova=" + prova +
                ", nomeAluno=" + nomeAluno +
                ", qtdQuestoes=" + qtdQuestoes +
                ", presenca=" + presenca +
                '}';
    }
}

package com.lcsz.abcde.dtos.lotImage;

public class LotImageCreateDto {
    private Long lotId;
    private String key;
    private String originalName;
    private Integer matricula;
    private String nomeAluno;
    private Integer codigoEscola;
    private Integer ano;
    private Integer grauSerie;
    private String turno;
    private Integer turma;
    private String etapa;
    private Integer prova;
    private String gabarito;
    private Integer presenca;
    private Integer qtdQuestoes;

    public LotImageCreateDto() {
    }

    public LotImageCreateDto(Long lotId, String key, String originalName, Integer matricula, String nomeAluno, Integer codigoEscola, Integer ano, Integer grauSerie, String turno, Integer turma, String etapa, Integer prova, String gabarito, Integer presenca, Integer qtdQuestoes) {
        this.lotId = lotId;
        this.key = key;
        this.originalName = originalName;
        this.matricula = matricula;
        this.nomeAluno = nomeAluno;
        this.codigoEscola = codigoEscola;
        this.ano = ano;
        this.grauSerie = grauSerie;
        this.turno = turno;
        this.turma = turma;
        this.etapa = etapa;
        this.prova = prova;
        this.gabarito = gabarito;
        this.presenca = presenca;
        this.qtdQuestoes = qtdQuestoes;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public Integer getQtdQuestoes() {
        return qtdQuestoes;
    }

    public void setQtdQuestoes(Integer qtdQuestoes) {
        this.qtdQuestoes = qtdQuestoes;
    }

    @Override
    public String toString() {
        return "LotImageCreateDto{" +
                "lotId=" + lotId +
                ", key='" + key + '\'' +
                ", originalName='" + originalName + '\'' +
                ", matricula=" + matricula +
                ", nomeAluno='" + nomeAluno + '\'' +
                ", codigoEscola=" + codigoEscola +
                ", ano=" + ano +
                ", grauSerie=" + grauSerie +
                ", turno='" + turno + '\'' +
                ", turma=" + turma +
                ", etapa='" + etapa + '\'' +
                ", prova=" + prova +
                ", gabarito='" + gabarito + '\'' +
                ", presenca=" + presenca +
                ", qtdQuestoes=" + qtdQuestoes +
                '}';
    }
}

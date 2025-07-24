package com.lcsz.abcde.dtos.lotImage;

public class LotImageCreateDto {
    private Long lotId;
    private String key;
    private String originalName;
    private Integer matricula;
    private String nomeAluno;
    private String etapa;
    private Integer prova;
    private String gabarito;
    private Integer presenca;

    public LotImageCreateDto() {
    }

    public LotImageCreateDto(Long lotId, String key, String originalName, Integer matricula, String nomeAluno, String etapa, Integer prova, String gabarito, Integer presenca) {
        this.lotId = lotId;
        this.key = key;
        this.originalName = originalName;
        this.matricula = matricula;
        this.nomeAluno = nomeAluno;
        this.etapa = etapa;
        this.prova = prova;
        this.gabarito = gabarito;
        this.presenca = presenca;
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

    @Override
    public String toString() {
        return "LotImageCreateDto{" +
                "lotId=" + lotId +
                ", key='" + key + '\'' +
                ", originalName='" + originalName + '\'' +
                ", matricula=" + matricula +
                ", nomeAluno=" + nomeAluno +
                ", etapa='" + etapa + '\'' +
                ", prova=" + prova +
                ", gabarito='" + gabarito + '\'' +
                ", presenca=" + presenca +
                '}';
    }
}

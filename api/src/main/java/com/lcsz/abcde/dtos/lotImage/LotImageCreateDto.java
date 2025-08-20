package com.lcsz.abcde.dtos.lotImage;

public class LotImageCreateDto {
    private Long lotId;
    private String key;
    private Integer matricula;
    private String nomeAluno;
    private Integer presenca;
    private Integer qtdQuestoes;

    public LotImageCreateDto() {
    }

    public LotImageCreateDto(Long lotId, String key, Integer matricula, String nomeAluno, Integer presenca, Integer qtdQuestoes) {
        this.lotId = lotId;
        this.key = key;
        this.matricula = matricula;
        this.nomeAluno = nomeAluno;
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

    @Override
    public String toString() {
        return "LotImageCreateDto{" +
                "lotId=" + lotId +
                ", key='" + key + '\'' +
                ", matricula=" + matricula +
                ", nomeAluno='" + nomeAluno + '\'' +
                ", presenca=" + presenca +
                ", qtdQuestoes=" + qtdQuestoes +
                '}';
    }
}

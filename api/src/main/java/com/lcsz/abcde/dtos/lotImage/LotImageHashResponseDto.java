package com.lcsz.abcde.dtos.lotImage;

public class LotImageHashResponseDto {
    private String hash;
    private Integer matricula;
    private String nomeAluno;

    public LotImageHashResponseDto() {
    }

    public LotImageHashResponseDto(String hash, Integer matricula, String nomeAluno) {
        this.hash = hash;
        this.matricula = matricula;
        this.nomeAluno = nomeAluno;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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

    @Override
    public String toString() {
        return "LotImageHashResponseDto{" +
                "hash='" + hash + '\'' +
                ", matricula=" + matricula +
                ", nomeAluno='" + nomeAluno + '\'' +
                '}';
    }
}

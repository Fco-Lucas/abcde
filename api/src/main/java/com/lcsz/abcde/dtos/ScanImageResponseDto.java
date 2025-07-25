package com.lcsz.abcde.dtos;

import java.util.Map;

public class ScanImageResponseDto {
    private Map<String, String> respostas;
    private ScanImageDadosResponseDto dados;

    public ScanImageResponseDto() {
    }

    public ScanImageResponseDto(Map<String, String> respostas, ScanImageDadosResponseDto dados) {
        this.respostas = respostas;
        this.dados = dados;
    }

    public Map<String, String> getRespostas() {
        return respostas;
    }

    public void setRespostas(Map<String, String> respostas) {
        this.respostas = respostas;
    }

    public ScanImageDadosResponseDto getDados() {
        return dados;
    }

    public void setDados(ScanImageDadosResponseDto dados) {
        this.dados = dados;
    }

    @Override
    public String toString() {
        return "ScanImageResponseDto{" +
                "respostas=" + respostas +
                ", dados=" + dados +
                '}';
    }
}

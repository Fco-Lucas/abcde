package com.lcsz.abcde.dtos.scanImage;

import java.util.Map;

public class ScanImageVtbResponseDto {
    private Map<String, String> respostas;
    private ScanImageDadosVtbResponseDto dados;

    public ScanImageVtbResponseDto() {
    }

    public ScanImageVtbResponseDto(Map<String, String> respostas, ScanImageDadosVtbResponseDto dados) {
        this.respostas = respostas;
        this.dados = dados;
    }

    public Map<String, String> getRespostas() {
        return respostas;
    }

    public void setRespostas(Map<String, String> respostas) {
        this.respostas = respostas;
    }

    public ScanImageDadosVtbResponseDto getDados() {
        return dados;
    }

    public void setDados(ScanImageDadosVtbResponseDto dados) {
        this.dados = dados;
    }

    @Override
    public String toString() {
        return "ScanImageVtbResponseDto{" +
                "respostas=" + respostas +
                ", dados=" + dados +
                '}';
    }
}

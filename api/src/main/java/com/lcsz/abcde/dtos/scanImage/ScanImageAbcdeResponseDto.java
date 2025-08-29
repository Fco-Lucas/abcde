package com.lcsz.abcde.dtos.scanImage;

import java.util.Map;

public class ScanImageAbcdeResponseDto {
    private Map<String, String> respostas;
    private ScanImageDadosAbcdeResponseDto dados;

    public ScanImageAbcdeResponseDto() {
    }

    public ScanImageAbcdeResponseDto(Map<String, String> respostas, ScanImageDadosAbcdeResponseDto dados) {
        this.respostas = respostas;
        this.dados = dados;
    }

    public Map<String, String> getRespostas() {
        return respostas;
    }

    public void setRespostas(Map<String, String> respostas) {
        this.respostas = respostas;
    }

    public ScanImageDadosAbcdeResponseDto getDados() {
        return dados;
    }

    public void setDados(ScanImageDadosAbcdeResponseDto dados) {
        this.dados = dados;
    }

    @Override
    public String toString() {
        return "ScanImageAbcdeResponseDto{" +
                "respostas=" + respostas +
                ", dados=" + dados +
                '}';
    }
}

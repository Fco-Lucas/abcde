package com.lcsz.abcde.dtos.imageInfoVtb;

public class ImageInfoVtbResponseDto {
    private Long id;
    private Long lotImageId;
    private String originalName;
    private String vtbCodigo;
    private String vtbFracao;
    private Integer faseGab;
    private Integer prova;

    public ImageInfoVtbResponseDto() {
    }

    public ImageInfoVtbResponseDto(Long id, Long lotImageId, String originalName, String vtbCodigo, String vtbFracao, Integer faseGab, Integer prova) {
        this.id = id;
        this.lotImageId = lotImageId;
        this.originalName = originalName;
        this.vtbCodigo = vtbCodigo;
        this.vtbFracao = vtbFracao;
        this.faseGab = faseGab;
        this.prova = prova;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLotImageId() {
        return lotImageId;
    }

    public void setLotImageId(Long lotImageId) {
        this.lotImageId = lotImageId;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
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

    @Override
    public String toString() {
        return "ImageInfoVtbResponseDto{" +
                "id=" + id +
                ", lotImageId=" + lotImageId +
                ", originalName=" + originalName +
                ", vtbCodigo='" + vtbCodigo + '\'' +
                ", vtbFracao='" + vtbFracao + '\'' +
                ", faseGab=" + faseGab +
                ", prova=" + prova +
                '}';
    }
}

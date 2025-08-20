package com.lcsz.abcde.dtos.imageInfoVtb;


public class ImageInfoVtbCreateDto {
    private Long id;
    private Long lotImageId;
    private String vtbFracao;
    private Integer faseGab;
    private Integer prova;

    public ImageInfoVtbCreateDto() {
    }

    public ImageInfoVtbCreateDto(Long id, Long lotImageId, String vtbFracao, Integer faseGab, Integer prova) {
        this.id = id;
        this.lotImageId = lotImageId;
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
        return "ImageInfoVtbCreateDto{" +
                "id=" + id +
                ", lotImageId=" + lotImageId +
                ", vtbFracao='" + vtbFracao + '\'' +
                ", faseGab=" + faseGab +
                ", prova=" + prova +
                '}';
    }
}

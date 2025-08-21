package com.lcsz.abcde.models;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "images_infos_vtb")
@EntityListeners(AuditingEntityListener.class)
public class ImageInfoVtb implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "lot_image_id")
    private Long lotImageId;
    @Column(nullable = false, name = "vtb_codigo", length = 9)
    private String vtbCodigo;
    @Column(nullable = false, name = "vtb_fracao", length = 2)
    private String vtbFracao;
    @Column(nullable = false, name = "fase_gab")
    private Integer faseGab;
    @Column(nullable = false)
    private Integer prova;

    public ImageInfoVtb() {
    }

    public ImageInfoVtb(Long id, Long lotImageId, String vtbCodigo, String vtbFracao, Integer faseGab, Integer prova) {
        this.id = id;
        this.lotImageId = lotImageId;
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
        return "ImageInfoVtb{" +
                "id=" + id +
                ", lotImageId=" + lotImageId +
                ", vtbCodigo='" + vtbCodigo + '\'' +
                ", vtbFracao='" + vtbFracao + '\'' +
                ", faseGab=" + faseGab +
                ", prova=" + prova +
                '}';
    }
}

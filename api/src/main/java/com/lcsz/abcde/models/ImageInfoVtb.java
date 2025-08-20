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
    @Column(nullable = false, name = "vtb_fracao")
    private String vtbFracao;
    @Column(nullable = false, name = "fase_gab")
    private Integer faseGab;
    @Column(nullable = false)
    private Integer prova;

    public ImageInfoVtb() {
    }

    public ImageInfoVtb(Long id, Long lotImageId, String vtbFracao, Integer faseGab, Integer prova) {
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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ImageInfoVtb that = (ImageInfoVtb) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ImageInfoVtb{" +
                "id=" + id +
                ", lotImageId=" + lotImageId +
                ", vtbFracao='" + vtbFracao + '\'' +
                ", faseGab=" + faseGab +
                ", prova=" + prova +
                '}';
    }
}

package com.lcsz.abcde.models;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "images_infos_abcde")
@EntityListeners(AuditingEntityListener.class)
public class ImageInfoAbcde implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "lot_image_id")
    private Long lotImageId;
    @Column(nullable = false, name = "original_name")
    private String originalName;
    @Column(nullable = false, name = "codigo_escola")
    private Integer codigoEscola;
    @Column(nullable = false)
    private Integer ano;
    @Column(nullable = false, name = "grau_serie")
    private Integer grauSerie;
    @Column(nullable = false, length = 1)
    private String turno;
    @Column(nullable = false)
    private Integer turma;
    @Column(nullable = false, length = 1)
    private String etapa;
    @Column(nullable = false)
    private Integer prova;
    @Column(nullable = false, length = 1)
    private String gabarito;

    public ImageInfoAbcde() {
    }

    public ImageInfoAbcde(Long id, Long lotImageId, String originalName, Integer codigoEscola, Integer ano, Integer grauSerie, String turno, Integer turma, String etapa, Integer prova, String gabarito) {
        this.id = id;
        this.lotImageId = lotImageId;
        this.originalName = originalName;
        this.codigoEscola = codigoEscola;
        this.ano = ano;
        this.grauSerie = grauSerie;
        this.turno = turno;
        this.turma = turma;
        this.etapa = etapa;
        this.prova = prova;
        this.gabarito = gabarito;
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

    public Integer getCodigoEscola() {
        return codigoEscola;
    }

    public void setCodigoEscola(Integer codigoEscola) {
        this.codigoEscola = codigoEscola;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getGrauSerie() {
        return grauSerie;
    }

    public void setGrauSerie(Integer grauSerie) {
        this.grauSerie = grauSerie;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Integer getTurma() {
        return turma;
    }

    public void setTurma(Integer turma) {
        this.turma = turma;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ImageInfoAbcde that = (ImageInfoAbcde) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ImageInfoAbcde{" +
                "id=" + id +
                ", lotImageId=" + lotImageId +
                ", originalName='" + originalName + '\'' +
                ", codigoEscola=" + codigoEscola +
                ", ano=" + ano +
                ", grauSerie=" + grauSerie +
                ", turno='" + turno + '\'' +
                ", turma=" + turma +
                ", etapa='" + etapa + '\'' +
                ", prova=" + prova +
                ", gabarito='" + gabarito + '\'' +
                '}';
    }
}

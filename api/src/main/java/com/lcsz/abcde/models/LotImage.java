package com.lcsz.abcde.models;

import com.lcsz.abcde.enums.lot_image.LotImageStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "lots_images")
@EntityListeners(AuditingEntityListener.class)
public class LotImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "lot_id")
    private Long lotId;
    @Column(nullable = false, length = 100)
    private String key;
    @Column(nullable = false, name = "original_name")
    private String originalName;
    @Column(nullable = false)
    private Integer matricula;
    @Column(nullable = false, length = 70, name = "nome_aluno")
    private String nomeAluno;
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
    @Column(nullable = false)
    private Integer presenca;
    @Column(nullable = false, name = "qtd_questoes")
    private Integer qtdQuestoes;
    @Column(nullable = false, name = "have_modification")
    private Boolean haveModification = false;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LotImageStatus status;
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public LotImage() {
    }

    public LotImage(Long id, Long lotId, String key, String originalName, Integer matricula, String nomeAluno, Integer codigoEscola, Integer ano, Integer grauSerie, String turno, Integer turma, String etapa, Integer prova, String gabarito, Integer presenca, Integer qtdQuestoes, Boolean haveModification, LotImageStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.lotId = lotId;
        this.key = key;
        this.originalName = originalName;
        this.matricula = matricula;
        this.nomeAluno = nomeAluno;
        this.codigoEscola = codigoEscola;
        this.ano = ano;
        this.grauSerie = grauSerie;
        this.turno = turno;
        this.turma = turma;
        this.etapa = etapa;
        this.prova = prova;
        this.gabarito = gabarito;
        this.presenca = presenca;
        this.qtdQuestoes = qtdQuestoes;
        this.haveModification = haveModification;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
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

    public Boolean getHaveModification() {
        return haveModification;
    }

    public void setHaveModification(Boolean haveModification) {
        this.haveModification = haveModification;
    }

    public LotImageStatus getStatus() {
        return status;
    }

    public void setStatus(LotImageStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LotImage lotImage = (LotImage) o;
        return Objects.equals(id, lotImage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LotImage{" +
                "id=" + id +
                ", lotId=" + lotId +
                ", key='" + key + '\'' +
                ", originalName='" + originalName + '\'' +
                ", matricula=" + matricula +
                ", nomeAluno='" + nomeAluno + '\'' +
                ", codigoEscola=" + codigoEscola +
                ", ano=" + ano +
                ", grauSerie=" + grauSerie +
                ", turno='" + turno + '\'' +
                ", turma=" + turma +
                ", etapa='" + etapa + '\'' +
                ", prova=" + prova +
                ", gabarito='" + gabarito + '\'' +
                ", presenca=" + presenca +
                ", qtdQuestoes=" + qtdQuestoes +
                ", haveModification=" + haveModification +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}

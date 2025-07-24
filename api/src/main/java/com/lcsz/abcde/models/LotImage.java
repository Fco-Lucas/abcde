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
    @Column(nullable = false, length = 1)
    private String etapa;
    @Column(nullable = false)
    private Integer prova;
    @Column(nullable = false, length = 1)
    private String gabarito;
    @Column(nullable = false)
    private Integer presenca;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LotImageStatus status;
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public LotImage() {
    }

    public LotImage(Long id, Long lotId, String key, String originalName, Integer matricula, String nomeAluno, String etapa, Integer prova, String gabarito, Integer presenca, LotImageStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.lotId = lotId;
        this.key = key;
        this.originalName = originalName;
        this.matricula = matricula;
        this.nomeAluno = nomeAluno;
        this.etapa = etapa;
        this.prova = prova;
        this.gabarito = gabarito;
        this.presenca = presenca;
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
                ", key=" + key +
                ", originalName='" + originalName + '\'' +
                ", matricula=" + matricula +
                ", nomeAluno=" + nomeAluno +
                ", etapa='" + etapa + '\'' +
                ", prova=" + prova +
                ", gabarito='" + gabarito + '\'' +
                ", presenca=" + presenca +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}

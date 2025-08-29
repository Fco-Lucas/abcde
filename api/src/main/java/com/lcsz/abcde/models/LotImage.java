package com.lcsz.abcde.models;

import com.lcsz.abcde.enums.lot_image.LotImageStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "lots_images")
@EntityListeners(AuditingEntityListener.class)
public class LotImage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "lot_id")
    private Long lotId;
    @Column(nullable = false, length = 100)
    private String key;
    @Column(nullable = false)
    private Integer matricula;
    @Column(nullable = false, length = 70, name = "nome_aluno")
    private String nomeAluno;
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

    public LotImage(Long id, Long lotId, String key, Integer matricula, String nomeAluno, Integer presenca, Integer qtdQuestoes, Boolean haveModification, LotImageStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.lotId = lotId;
        this.key = key;
        this.matricula = matricula;
        this.nomeAluno = nomeAluno;
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
                ", matricula=" + matricula +
                ", nomeAluno='" + nomeAluno + '\'' +
                ", presenca=" + presenca +
                ", qtdQuestoes=" + qtdQuestoes +
                ", haveModification=" + haveModification +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}

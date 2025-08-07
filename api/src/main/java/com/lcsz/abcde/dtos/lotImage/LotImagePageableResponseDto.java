package com.lcsz.abcde.dtos.lotImage;

import com.lcsz.abcde.enums.lot_image.LotImageStatus;

import java.time.LocalDateTime;

public class LotImagePageableResponseDto {
    private Long id;
    private Long lotId;
    private Integer matricula;
    private String nomeAluno;
    private Integer presenca;
    private Boolean haveModification;
    private LotImageStatus status;
    private LocalDateTime expirationImageDate;
    private LocalDateTime createdAt;

    public LotImagePageableResponseDto() {
    }

    public LotImagePageableResponseDto(Long id, Long lotId, Integer matricula, String nomeAluno, Integer presenca, Boolean haveModification, LotImageStatus status, LocalDateTime expirationImageDate, LocalDateTime createdAt) {
        this.id = id;
        this.lotId = lotId;
        this.matricula = matricula;
        this.nomeAluno = nomeAluno;
        this.presenca = presenca;
        this.haveModification = haveModification;
        this.status = status;
        this.expirationImageDate = expirationImageDate;
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

    public LocalDateTime getExpirationImageDate() {
        return expirationImageDate;
    }

    public void setExpirationImageDate(LocalDateTime expirationImageDate) {
        this.expirationImageDate = expirationImageDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "LotImagePageableResponseDto{" +
                "id=" + id +
                ", lotId=" + lotId +
                ", matricula=" + matricula +
                ", nomeAluno='" + nomeAluno + '\'' +
                ", presenca=" + presenca +
                ", haveModification=" + haveModification +
                ", status=" + status +
                ", expirationImageDate=" + expirationImageDate +
                ", createdAt=" + createdAt +
                '}';
    }
}

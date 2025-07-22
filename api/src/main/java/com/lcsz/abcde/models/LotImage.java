package com.lcsz.abcde.models;

import com.lcsz.abcde.enums.lot_image.LotImageStatus;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "lots_images")
public class LotImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "lot_id")
    private Long lotId;
    @Column(nullable = false)
    private String key;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LotImageStatus status;

    public LotImage() {
    }

    public LotImage(Long id, Long lotId, String key, LotImageStatus status) {
        this.id = id;
        this.lotId = lotId;
        this.key = key;
        this.status = status;
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

    public LotImageStatus getStatus() {
        return status;
    }

    public void setStatus(LotImageStatus status) {
        this.status = status;
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
                ", status=" + status +
                '}';
    }
}

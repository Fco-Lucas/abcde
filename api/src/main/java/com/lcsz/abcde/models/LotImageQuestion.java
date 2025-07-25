package com.lcsz.abcde.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "lots_images_questions")
public class LotImageQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "image_id")
    private Long imageId;
    @Column(nullable = false)
    private Integer number;
    @Column(nullable = false, length = 5)
    private String alternative;

    public LotImageQuestion() {
    }

    public LotImageQuestion(Long id, Long imageId, Integer number, String alternative) {
        this.id = id;
        this.imageId = imageId;
        this.number = number;
        this.alternative = alternative;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getAlternative() {
        return alternative;
    }

    public void setAlternative(String alternative) {
        this.alternative = alternative;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LotImageQuestion that = (LotImageQuestion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LotImageQuestion{" +
                "id=" + id +
                ", imageId=" + imageId +
                ", number=" + number +
                ", alternative='" + alternative + '\'' +
                '}';
    }
}

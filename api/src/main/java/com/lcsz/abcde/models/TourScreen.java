package com.lcsz.abcde.models;

import com.lcsz.abcde.enums.tourScreen.TourScreenEnum;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tour_screen")
@EntityListeners(AuditingEntityListener.class)
public class TourScreen implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "user_id")
    private UUID userId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TourScreenEnum screen;
    @Column(nullable = false)
    private Boolean completed = false;

    public TourScreen() {
    }

    public TourScreen(Long id, UUID userId, TourScreenEnum screen, Boolean completed) {
        this.id = id;
        this.userId = userId;
        this.screen = screen;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public TourScreenEnum getScreen() {
        return screen;
    }

    public void setScreen(TourScreenEnum screen) {
        this.screen = screen;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TourScreen that = (TourScreen) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TourScreen{" +
                "id=" + id +
                ", userId=" + userId +
                ", screen=" + screen +
                ", completed=" + completed +
                '}';
    }
}

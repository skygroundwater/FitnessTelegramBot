package com.fitnesstelegrambot.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "athlete")
@Getter
@Setter
@ToString
public class Athlete implements Serializable {

    @Id
    @Column(name = "athlete_id")
    private Long athleteId;

    @Column(name = "athlete_name")
    private String athleteName;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<TrainingNotification> notifications;

    public Athlete(Long chatId, String authorName, LocalDateTime registeredAt, boolean isActive) {
        this.athleteId = chatId;
        this.athleteName = authorName;
        this.registeredAt = registeredAt;
        this.isActive = isActive;
    }

    public Athlete() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Athlete athlete = (Athlete) o;
        return getAthleteId() != null && Objects.equals(getAthleteId(), athlete.getAthleteId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

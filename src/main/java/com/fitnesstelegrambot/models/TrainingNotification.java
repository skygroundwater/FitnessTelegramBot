package com.fitnesstelegrambot.models;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TrainingNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "athlete_id")
    @ToString.Exclude
    private Athlete athlete;

    @Column(name = "time_to_do")
    private LocalDateTime timeToDo;

    public TrainingNotification(String txt,
                                Athlete athlete,
                                LocalDateTime dateTime) {
        this.description = txt;
        this.timeToDo = dateTime;
        this.athlete = athlete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TrainingNotification that = (TrainingNotification) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

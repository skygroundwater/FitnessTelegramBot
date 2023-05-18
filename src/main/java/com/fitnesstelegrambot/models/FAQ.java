package com.fitnesstelegrambot.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "faq")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class FAQ {

    @Id
    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private String answer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FAQ faq = (FAQ) o;
        return getQuestion() != null && Objects.equals(getQuestion(), faq.getQuestion());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

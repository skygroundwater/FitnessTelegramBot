package com.fitnesstelegrambot.repositories;

import com.fitnesstelegrambot.models.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AthletesRepository extends JpaRepository<Athlete,Long> {

    List<Athlete> findByAthleteId(Long chatId);
}

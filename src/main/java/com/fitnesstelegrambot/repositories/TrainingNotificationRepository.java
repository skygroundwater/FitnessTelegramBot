package com.fitnesstelegrambot.repositories;

import com.fitnesstelegrambot.models.TrainingNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TrainingNotificationRepository extends JpaRepository<TrainingNotification, Long> {

    List<TrainingNotification> findAllByTimeToDo(LocalDateTime timeToDo);

    List<TrainingNotification> findAllByTimeToDoAndAndAthlete(Long chatId, LocalDateTime timeToDo);

    void removeTrainingNotificationByTimeToDoAndAthlete(Long chatId, LocalDateTime timeToDo);

}

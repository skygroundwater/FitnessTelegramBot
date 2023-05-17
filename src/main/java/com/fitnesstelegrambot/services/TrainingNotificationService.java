package com.fitnesstelegrambot.services;

import com.fitnesstelegrambot.models.TrainingNotification;

import java.time.LocalDateTime;
import java.util.List;

public interface TrainingNotificationService {
    List<TrainingNotification> findAll();

    void createNewTrainingNotification(TrainingNotification trainingNotification);

    List<TrainingNotification> allCloserTrainings(LocalDateTime localDateTime);

    void removeTrainingNotification(TrainingNotification trainingNotification);
}

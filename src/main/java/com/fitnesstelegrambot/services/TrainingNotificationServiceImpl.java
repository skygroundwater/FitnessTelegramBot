package com.fitnesstelegrambot.services;

import com.fitnesstelegrambot.models.TrainingNotification;
import com.fitnesstelegrambot.repositories.TrainingNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TrainingNotificationServiceImpl implements TrainingNotificationService {

    private final TrainingNotificationRepository notificationRepository;

    @Autowired
    public TrainingNotificationServiceImpl(TrainingNotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<TrainingNotification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public void createNewTrainingNotification(TrainingNotification trainingNotification) {
        if (!Objects.isNull(trainingNotification)) {
            notificationRepository.save(trainingNotification);
        }
    }

    @Override
    public List<TrainingNotification> allCloserTrainings(LocalDateTime localDateTime){
        return notificationRepository.findAllByTimeToDo(localDateTime);
    }

    @Override
    public void removeTrainingNotification(TrainingNotification trainingNotification){
        notificationRepository.delete(trainingNotification);
    }

}

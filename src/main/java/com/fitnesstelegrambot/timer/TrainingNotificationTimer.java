package com.fitnesstelegrambot.timer;

import com.fitnesstelegrambot.services.TrainingNotificationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Component
public class TrainingNotificationTimer {

    private final TrainingNotificationService notificationService;

    private final TelegramBot telegramBot;

    @Autowired
    public TrainingNotificationTimer(TrainingNotificationService notificationService, TelegramBot telegramBot) {
        this.notificationService = notificationService;
        this.telegramBot = telegramBot;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void training() {
        notificationService.allCloserTrainings(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .forEach(tn -> {
                    telegramBot.execute(new SendMessage(tn.getAthlete().getAthleteId(), tn.getDescription()));
                    notificationService.removeTrainingNotification(tn);
                });
    }

}

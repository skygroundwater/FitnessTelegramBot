package com.fitnesstelegrambot.listener;

import com.fitnesstelegrambot.models.Athlete;
import com.fitnesstelegrambot.models.TrainingNotification;
import com.fitnesstelegrambot.services.AthletesService;
import com.fitnesstelegrambot.services.TrainingNotificationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.MessageEntity;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FitnessTelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBot telegramBot;

    private final TrainingNotificationService notificationService;

    private final AthletesService athletesService;

    private final Logger logger = LoggerFactory.getLogger(FitnessTelegramBotUpdatesListener.class);

    private static final Pattern pattern = Pattern.compile("(\\d{1,2}\\.\\d{1,2}\\.\\d{4} \\d{1,2}:\\d{2}) ([А-я\\d\\s.,!?:]+)");

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Autowired
    public FitnessTelegramBotUpdatesListener(TelegramBot telegramBot,
                                             TrainingNotificationService notificationService, AthletesService athletesService) {
        this.telegramBot = telegramBot;
        this.notificationService = notificationService;
        this.athletesService = athletesService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.stream()
                    .filter(update -> update.message() != null)
                    .forEach(update -> {
                        Message message = update.message();
                        Long chatId = message.chat().id();
                        String text = message.text();
                        if ("/start".equals(text)) {
                            sendMessage(chatId,
                                    "Привет! " + message.chat().firstName() + " Меня зовут Арина и я квалифицированный фитнес-тренер ");
                            if (athletesService.findByAthleteChatId(chatId) == null) {
                                athletesService.addNewAthleteToDB(new Athlete(chatId, message.chat().username(), LocalDateTime.now(), true));
                            }
                        } else if (text != null) {
                            Matcher matcher = matcher(text);
                            if (matcher.find()) {
                                LocalDateTime dateTime = parseMatcher(matcher.group(1));
                                if (Objects.isNull(dateTime)) {
                                    sendMessage(chatId, "Некорректный формат даты и времени");

                                } else {
                                    String txt = matcher.group(2);
                                    notificationService.createNewTrainingNotification(
                                            new TrainingNotification(txt, athletesService.findByAthleteChatId(chatId), dateTime));
                                    sendMessage(chatId, "Тренировка успешно запланирована");
                                }
                            } else {
                                sendMessage(chatId, "Некорректный формат сообщения");
                            }
                        }
                        logger.info("Handles update: {}", update);
                    });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private static Matcher matcher(String text) {
        return pattern.matcher(text);
    }

    private static LocalDateTime parseMatcher(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("Вопросы").switchInlineQuery("/questions")
                }
        );
        sendMessage.replyMarkup(inlineKeyboard);
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.message());
        }
    }
}
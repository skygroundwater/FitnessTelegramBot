package com.fitnesstelegrambot.services;

import com.fitnesstelegrambot.models.Athlete;

public interface AthletesService {
    Athlete findByAthleteChatId(Long chatId);
}

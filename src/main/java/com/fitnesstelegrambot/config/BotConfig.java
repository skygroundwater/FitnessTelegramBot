package com.fitnesstelegrambot.config;

import com.pengrad.telegrambot.TelegramBot;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class BotConfig {

    @Value("${bot.name}")
    String botName;

    @Value("${bot.owner}")
    Long ownerId;

    @Bean
    public TelegramBot telegramBot(@Value("${bot.token}") String token) {
        return new TelegramBot(token);
    }
}

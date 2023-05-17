package com.fitnesstelegrambot.services;

import com.fitnesstelegrambot.models.Athlete;
import com.fitnesstelegrambot.repositories.AthletesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AthletesServiceImpl implements AthletesService{

    private final AthletesRepository athletesRepository;

    @Autowired
    public AthletesServiceImpl(AthletesRepository athletesRepository) {
        this.athletesRepository = athletesRepository;
    }

    @Override
    public Athlete findByAthleteChatId(Long chatId) {
        return athletesRepository.findByAthleteId(chatId).stream().findAny().orElse(null);
    }


}

package com.fitnesstelegrambot.services;

import com.fitnesstelegrambot.models.FAQ;
import com.fitnesstelegrambot.repositories.FAQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FAQServiceImpl implements FAQService{


    private final FAQRepository faqRepository;

    @Autowired
    public FAQServiceImpl(FAQRepository faqRepository) {
        this.faqRepository = faqRepository;
    }


    @Override
    public List<FAQ> getAllFAQs(){
        return faqRepository.findAll();
    }
}

package com.fitnesstelegrambot.repositories;

import com.fitnesstelegrambot.models.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, String> {

    List<FAQ> findByQuestion(String question);

}

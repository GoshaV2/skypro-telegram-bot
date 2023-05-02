package com.skypro.skyprotelegrambot.repository;

import com.skypro.skyprotelegrambot.entity.Answer;
import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByCommand(String command);

    List<Answer> findAllByCategoryAndShelter(Category category, Shelter shelter);

    void deleteAnswerById(Long id);

    Optional<Answer> findAnswerById(Long id);
}

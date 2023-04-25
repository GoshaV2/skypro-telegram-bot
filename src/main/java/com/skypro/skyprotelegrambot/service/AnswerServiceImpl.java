package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.exception.NotFoundElement;
import com.skypro.skyprotelegrambot.entity.Answer;
import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.Shelter;
import org.springframework.stereotype.Service;
import com.skypro.skyprotelegrambot.repository.AnswerRepository;

import java.util.List;

/**
 * Класс определяющий методы обработки ответов
 * */

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public Answer getAnswer(String command) {
        return answerRepository.findByCommand(command)
                .orElseThrow(() -> new NotFoundElement(command, Answer.class));
    }

    @Override
    public List<Answer> getAnswersByCategory(Category category, Shelter shelter) {
        return answerRepository.findAllByCategoryAndShelter(category,shelter);
    }

    @Override
    public boolean hasCommand(String command) {
        return answerRepository.existsByCommand(command);
    }
}

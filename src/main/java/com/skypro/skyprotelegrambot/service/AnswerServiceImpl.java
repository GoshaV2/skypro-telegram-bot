package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.dto.request.AnswerDto;
import com.skypro.skyprotelegrambot.exception.NotFoundElement;
import com.skypro.skyprotelegrambot.entity.Answer;
import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.Shelter;
import org.springframework.stereotype.Service;
import com.skypro.skyprotelegrambot.repository.AnswerRepository;

import java.util.List;

/**
 * Класс определяющий методы обработки ответов
 */

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
        return answerRepository.findAllByCategoryAndShelter(category, shelter);
    }

    @Override
    public Answer findAnswerById(Long id) {
        return answerRepository.findAnswerById(id)
                .orElseThrow(() -> new NotFoundElement(id, Answer.class));
    }

    @Override
    public AnswerDto createAnswer(String title, String text, String command, Category category, Shelter shelterId) {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setTitle(answerDto.getTitle());
        answerDto.setText(answerDto.getText());
        answerDto.setCommand(answerDto.getCommand());
        answerDto.setCategory(answerDto.getCategory());
        answerDto.setShelterId(answerDto.getShelterId());
        return answerDto;
    }

    @Override
    public AnswerDto updateAnswer(Long id, String title, String text, String command) {
        findAnswerById(id);
        AnswerDto answerDto = new AnswerDto();
        answerDto.setTitle(answerDto.getTitle());
        answerDto.setText(answerDto.getText());
        return answerDto;
    }

    @Override
    public AnswerDto deleteAnswer(Long id) {
        return answerRepository.deleteAnswerById(id)
                .orElseThrow(() -> new NotFoundElement(id, Answer.class));
    }

}

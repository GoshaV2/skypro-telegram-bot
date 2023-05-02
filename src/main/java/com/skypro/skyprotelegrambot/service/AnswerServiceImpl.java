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
    private final ShelterService shelterService;
    private final AnswerService answerService;

    public AnswerServiceImpl(AnswerRepository answerRepository,
                             ShelterService shelterService,
                             AnswerService answerService) {
        this.answerRepository = answerRepository;
        this.shelterService = shelterService;
        this.answerService = answerService;
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
    public Answer createAnswer(AnswerDto answerDto) {
        Answer answer = new Answer();
        answer.setTitle(answerDto.getTitle());
        answer.setText(answerDto.getText());
        answer.setCommand(answerDto.getCommand());
        answer.setShelter(shelterService.findShelterById(answerDto.getShelterId()));
        return answerRepository.save(answer);
    }

    @Override
    public Answer updateAnswer(AnswerDto answerDto, Long id) {
        Answer answer = answerService.findAnswerById(id);
        answer.setTitle(answerDto.getTitle());
        answer.setText(answerDto.getText());
        answer.setCommand(answerDto.getCommand());
        return answerRepository.save(answer);
    }

    @Override
    public void deleteAnswerById(Long id) {
        answerRepository.delete(answerService.findAnswerById(id));
    }
}

package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.dto.request.AnswerDto;
import com.skypro.skyprotelegrambot.entity.Answer;
import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.exception.NotFoundElement;
import com.skypro.skyprotelegrambot.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Класс определяющий методы обработки ответов
 */

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final ShelterService shelterService;

    public AnswerServiceImpl(AnswerRepository answerRepository,
                             ShelterService shelterService) {
        this.answerRepository = answerRepository;
        this.shelterService = shelterService;
    }

    @Override
    public Answer getAnswer(long id) {
        return answerRepository.findAnswerById(id)
                .orElseThrow(() -> new NotFoundElement(id, Answer.class));
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
        answer.setCategory(answerDto.getCategory());
        answer.setShelter(shelterService.findShelterById(answerDto.getShelterId()));
        return answerRepository.save(answer);
    }

    @Override
    public Answer updateAnswer(AnswerDto answerDto, Long id) {
        Answer answer = findAnswerById(id);
        answer.setTitle(answerDto.getTitle());
        answer.setText(answerDto.getText());
        answer.setCategory(answerDto.getCategory());
        return answerRepository.save(answer);
    }

    @Override
    public void deleteAnswerById(Long id) {

        answerRepository.delete(findAnswerById(id));
    }

}

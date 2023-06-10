package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.dto.request.AnswerDto;
import com.skypro.skyprotelegrambot.dto.response.AnswerResponse;
import com.skypro.skyprotelegrambot.entity.Answer;
import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.exception.NotFoundElement;
import com.skypro.skyprotelegrambot.repository.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public AnswerResponse getAnswerResponse(long id) {
        return AnswerResponse.from(getAnswer(id));
    }

    @Override
    public List<Answer> getAnswersByCategory(Category category, Shelter shelter) {
        return answerRepository.findAllByCategoryAndShelter(category, shelter);
    }

    @Override
    @Transactional
    public AnswerResponse createAnswer(AnswerDto answerDto) {
        Answer answer = new Answer();
        answer.setTitle(answerDto.getTitle());
        answer.setText(answerDto.getText());
        answer.setCategory(answerDto.getCategory());
        answer.setShelter(shelterService.findShelterById(answerDto.getShelterId()));
        return AnswerResponse.from(answerRepository.save(answer));
    }

    @Override
    public AnswerResponse updateAnswer(AnswerDto answerDto, Long id) {
        Answer answer = getAnswer(id);
        answer.setTitle(answerDto.getTitle());
        answer.setText(answerDto.getText());
        answer.setCategory(answerDto.getCategory());
        return AnswerResponse.from(answerRepository.save(answer));
    }

    @Override
    public void deleteAnswerById(Long id) {

        answerRepository.delete(getAnswer(id));
    }

}

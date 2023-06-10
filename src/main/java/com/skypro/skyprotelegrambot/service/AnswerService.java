package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.dto.request.AnswerDto;
import com.skypro.skyprotelegrambot.dto.response.AnswerResponse;
import com.skypro.skyprotelegrambot.entity.Answer;
import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.Shelter;

import java.util.List;

public interface AnswerService {
    Answer getAnswer(long id);

    AnswerResponse getAnswerResponse(long id);

    List<Answer> getAnswersByCategory(Category category, Shelter shelter);

    AnswerResponse createAnswer(AnswerDto answerDto);

    AnswerResponse updateAnswer(AnswerDto answerDto, Long id);

    void deleteAnswerById(Long id);
}

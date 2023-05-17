package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.dto.request.AnswerDto;
import com.skypro.skyprotelegrambot.entity.Answer;
import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.Shelter;

import java.util.List;

public interface AnswerService {
    Answer getAnswer(String command);

    List<Answer> getAnswersByCategory(Category category, Shelter shelter);

    Answer findAnswerById(Long id);

    Answer createAnswer(AnswerDto answerDto);

    Answer updateAnswer(AnswerDto answerDto, Long id);

    void deleteAnswerById(Long id);

    boolean hasCommand(String command);
}

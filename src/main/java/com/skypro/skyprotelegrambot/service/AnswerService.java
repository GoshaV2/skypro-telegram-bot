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

    AnswerDto createAnswer(String title, String text, String command, Category category, Shelter shelterId);

    AnswerDto updateAnswer(Long id, String title, String text, String command);

    AnswerDto deleteAnswer(Long id);
}

package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.entity.Answer;
import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.Shelter;

import java.util.List;

public interface AnswerService {
    Answer getAnswer(String command);
    List<Answer> getAnswersByCategory(Category category, Shelter shelter);
    boolean hasCommand(String command);
}

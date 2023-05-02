package com.skypro.skyprotelegrambot.controller;


import com.skypro.skyprotelegrambot.dto.request.AnswerDto;
import com.skypro.skyprotelegrambot.entity.Answer;
import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping
    @Operation(
            summary = "Получение ответа"
    )
    public Answer getAnswer(String command) {
        return answerService.getAnswer(command);
    }

    @GetMapping
    @Operation(
            summary = "Получение ответа по категории"
    )
    public List<Answer> getAnswersByCategory(Category category, Shelter shelter) {
        return answerService.getAnswersByCategory(category, shelter);
    }

    @PostMapping("/{id}")
    @Operation(
            summary = "Создание ответа"
    )
    public Answer create(@RequestBody AnswerDto answerDto, @PathVariable (name = "id") Long id) {
        return answerService.createAnswer(answerDto);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Изменение параметров ответа"
    )
    public Answer update(@RequestBody AnswerDto answerDto, @PathVariable (name = "id") Long id) {
        return answerService.updateAnswer(answerDto, id);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление ответа"
    )
    public void delete(@PathVariable @Parameter(description = "id ответа") Long id) {
        answerService.deleteAnswerById(id);
    }
}

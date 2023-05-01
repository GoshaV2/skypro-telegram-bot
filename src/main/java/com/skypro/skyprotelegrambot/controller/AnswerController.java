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

    @PostMapping("/")
    @Operation(
            summary = "Создание ответа"
    )
    public Answer create(@Parameter(description = "Приют") Long shelterId,
                         @RequestBody AnswerDto answerDto) {
        return answerService.createAnswer(shelterId, answerDto);
    }

    @PutMapping("/")
    @Operation(
            summary = "Изменение параметров ответа"
    )
    public Answer update(@RequestBody AnswerDto answerDto, @PathVariable Long id) {
        return answerService.updateAnswer(answerDto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление ответа"
    )
    public void delete(@PathVariable @Parameter(description = "id ответа") Long id) {
        answerService.deleteAnswerById(id);
    }
}

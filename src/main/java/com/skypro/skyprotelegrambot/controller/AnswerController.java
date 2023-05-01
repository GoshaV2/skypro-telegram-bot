package com.skypro.skyprotelegrambot.controller;


import com.skypro.skyprotelegrambot.dto.request.AnswerDto;
import com.skypro.skyprotelegrambot.dto.request.ShelterDto;
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
    public AnswerDto create(@RequestParam @Parameter(description = "Наименование ответа") String title,
                            @RequestParam @Parameter(description = "Содержание ответа") String text,
                            @RequestParam @Parameter(description = "Команда") String command,
                            @RequestParam @Parameter(description = "Категория ответа") Category category,
                            @RequestParam @Parameter(description = "Приют") Shelter shelterId,
                            AnswerDto answerDto) {
        return answerService.createAnswer(answerDto.getTitle(), answerDto.getText(), answerDto.getCommand(), answerDto.getCategory(), answerDto.getShelterId());
    }

    @PutMapping("/")
    @Operation(
            summary = "Изменение параметров ответа"
    )
    public AnswerDto update(@RequestBody AnswerDto answerDto) {
        return answerService.updateAnswer(answerDto.getTitle(), answerDto.getText(),answerDto.getCommand());
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление ответа"
    )
    public AnswerDto delete(@PathVariable @Parameter(description = "id ответа") Long id) {
        return answerService.deleteAnswer(id);
    }
}

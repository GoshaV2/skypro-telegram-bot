package com.skypro.skyprotelegrambot.controller;


import com.skypro.skyprotelegrambot.dto.request.AnswerDto;
import com.skypro.skyprotelegrambot.dto.response.AnswerResponse;
import com.skypro.skyprotelegrambot.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение ответа"
    )
    public AnswerResponse getAnswer(@PathVariable("id") long id) {
        return answerService.getAnswerResponse(id);
    }


    @PostMapping
    @Operation(
            summary = "Создание ответа"
    )
    public AnswerResponse create(@RequestBody AnswerDto answerDto) {
        return answerService.createAnswer(answerDto);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Изменение параметров ответа"
    )
    public AnswerResponse update(@RequestBody AnswerDto answerDto, @PathVariable(name = "id") Long id) {
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

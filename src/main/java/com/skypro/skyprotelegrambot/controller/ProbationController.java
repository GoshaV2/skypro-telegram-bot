package com.skypro.skyprotelegrambot.controller;

import com.skypro.skyprotelegrambot.dto.request.ProbationAddAdditionalDaysDto;
import com.skypro.skyprotelegrambot.dto.request.ProbationChangeStatusDto;
import com.skypro.skyprotelegrambot.dto.request.ProbationDto;
import com.skypro.skyprotelegrambot.dto.response.ProbationResponse;
import com.skypro.skyprotelegrambot.service.ProbationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/probation")
public class ProbationController {
    private final ProbationService probationService;

    public ProbationController(ProbationService probationService) {
        this.probationService = probationService;
    }

    @GetMapping()
    @Operation(summary = "Получить испытательные сроки пользователя по приюту")
    public ResponseEntity<List<ProbationResponse>> getUserProbationByShelter(@RequestParam("chatId") long chatId,
                                                                             @RequestParam("shelterId") long shelterId) {
        return ResponseEntity.ok(probationService.getUserProbationByShelter(chatId, shelterId));
    }

    @PostMapping
    @Operation(summary = "Назначить пользователю испыталельный срок")
    public ResponseEntity<ProbationResponse> createProbation(@RequestBody ProbationDto probationDto) {
        return ResponseEntity.ok(probationService.createProbation(probationDto));
    }

    @PatchMapping("/shangeStatus/{id}")
    @Operation(summary = "Поменять статус испытательного срока")
    public ResponseEntity<ProbationResponse> changeProbationStatus(@RequestBody ProbationChangeStatusDto probationDto,
                                                                   @PathVariable("id") long id) {
        return ResponseEntity.ok(probationService.changeProbationStatus(probationDto, id));
    }
    @PatchMapping("/addingDays/{id}")
    @Operation(summary = "Добавить дополнительные дни к испытательному сроку")
    public ResponseEntity<ProbationResponse> addAdditionalDays(@RequestBody ProbationAddAdditionalDaysDto probationDto,
                                                                   @PathVariable("id") long id) {
        return ResponseEntity.ok(probationService.addAdditionalDays(probationDto, id));
    }

}

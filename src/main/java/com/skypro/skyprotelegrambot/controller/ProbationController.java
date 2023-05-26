package com.skypro.skyprotelegrambot.controller;

import com.skypro.skyprotelegrambot.dto.request.ProbationDto;
import com.skypro.skyprotelegrambot.dto.response.ProbationResponse;
import com.skypro.skyprotelegrambot.service.ProbationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/probation")
public class ProbationController {
    private final ProbationService probationService;

    public ProbationController(ProbationService probationService) {
        this.probationService = probationService;
    }

    @PostMapping
    @Operation(summary = "Назначить пользователю испыталельный срок")
    public ResponseEntity<ProbationResponse> createProbation(@RequestBody ProbationDto probationDto) {
        return ResponseEntity.ok(probationService.createProbation(probationDto));
    }

}

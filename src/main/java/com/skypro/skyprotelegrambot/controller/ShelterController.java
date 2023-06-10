package com.skypro.skyprotelegrambot.controller;

import com.skypro.skyprotelegrambot.dto.request.ShelterDto;
import com.skypro.skyprotelegrambot.dto.response.ShelterResponse;
import com.skypro.skyprotelegrambot.dto.response.UserResponse;
import com.skypro.skyprotelegrambot.service.ShelterService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shelters")
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @PostMapping("/")
    @Operation(
            summary = "Создание приюта"
    )
    public ShelterResponse create(@RequestBody ShelterDto shelterDto) {
        return shelterService.createShelter(shelterDto);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Изменение параметров приюта"
    )
    public ShelterResponse update(@RequestBody ShelterDto shelterDto, @PathVariable(name = "id") Long id) {
        return shelterService.updateShelter(shelterDto, id);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Поиск приюта по id"
    )
    public ShelterResponse findShelterById(@PathVariable(name = "id") Long id) {
        return shelterService.getShelterResponse(id);
    }

    @GetMapping("/{id}/users")
    @Operation(
            summary = "Получить пользователей из приюта"
    )
    public ResponseEntity<List<UserResponse>> getUsers(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(shelterService.getUsersOfShelter(id));
    }
}

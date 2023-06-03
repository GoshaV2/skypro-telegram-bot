package com.skypro.skyprotelegrambot.controller;

import com.skypro.skyprotelegrambot.dto.request.ShelterDto;
import com.skypro.skyprotelegrambot.dto.response.UserResponse;
import com.skypro.skyprotelegrambot.entity.Shelter;
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
    public Shelter create(@RequestBody ShelterDto shelterDto) {
        return shelterService.createShelter(shelterDto);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Изменение параметров приюта"
    )
    public Shelter update(@RequestBody ShelterDto shelterDto, @PathVariable(name = "id") Long id) {
        return shelterService.updateShelter(shelterDto, id);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Поиск приюта по id"
    )
    public Shelter findShelterById(@PathVariable(name = "id") Long id) {
        return shelterService.findShelterById(id);
    }

    @GetMapping("/{id}/users")
    @Operation(
            summary = "Получить пользователей из приюта"
    )
    public ResponseEntity<List<UserResponse>> getUsers(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(shelterService.getUsersOfShelter(id));
    }
}

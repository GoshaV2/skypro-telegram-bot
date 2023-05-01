package com.skypro.skyprotelegrambot.controller;

import com.skypro.skyprotelegrambot.dto.request.ShelterDto;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.service.ShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @PostMapping("/")
    @Operation(
            summary = "Создание приюта"
    )
    public ShelterDto create(@RequestParam @Parameter(description = "Идентификатор приюта") Long id,
                             @RequestParam @Parameter(description = "Наименование приюта") String name,
                             ShelterDto shelterDto) {
        return shelterService.createShelter(shelterDto.getId(), shelterDto.getName());
    }

    @PutMapping("/")
    @Operation(
            summary = "Изменение параметров приюта"
    )
    public ShelterDto update(@RequestBody ShelterDto shelterDto) {
        return shelterService.updateShelter(shelterDto.getName());
    }

    @GetMapping
    @Operation(
            summary = "Поиск приюта по id"
    )
    public Shelter findShelterById(@PathVariable @Parameter(description = "id приюта") long id) {
        return shelterService.findShelterById(id);
    }


//    public Shelter delete() { //Нужно ли удалять приют?
//        return null;
//    }
}

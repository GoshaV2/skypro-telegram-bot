package com.skypro.skyprotelegrambot.controller;

import com.skypro.skyprotelegrambot.dto.request.DtoRequest;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.service.ShelterService;
import org.springframework.web.bind.annotation.RequestBody;

public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    public Shelter create(@RequestBody DtoRequest dtoRequest) {
        return null;
    }

    public Shelter read() {
        return null;
    }

    public Shelter update() {
        return null;
    }

    public Shelter delete() {
        return null;
    }
}

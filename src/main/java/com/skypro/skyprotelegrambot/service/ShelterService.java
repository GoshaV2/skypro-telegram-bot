package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.dto.request.ShelterDto;
import com.skypro.skyprotelegrambot.entity.Shelter;

/**
 * Общий сервис приютов
 */

public interface ShelterService {
    Shelter findShelterById(Long id);

    ShelterDto createShelter(Long id, String name);

    Shelter getShelterByName(String name);

    ShelterDto updateShelter(String name);
}

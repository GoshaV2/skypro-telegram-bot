package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.dto.request.ShelterDto;
import com.skypro.skyprotelegrambot.entity.Shelter;

/**
 * Общий сервис приютов
 */

public interface ShelterService {
    Shelter findShelterById(Long id);

    Shelter createShelter(ShelterDto shelterDto);

    Shelter updateShelter(ShelterDto shelterDto);
}

package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.entity.Shelter;

import java.util.List;

/**
 * Общий сервис приютов
 */

public interface ShelterService {
    Shelter findShelterById(Long id);

    List<Shelter> getAllShelter();
}

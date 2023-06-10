package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.dto.request.ShelterDto;
import com.skypro.skyprotelegrambot.dto.response.ShelterResponse;
import com.skypro.skyprotelegrambot.dto.response.UserResponse;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;

import java.util.List;

/**
 * Общий сервис приютов
 */

public interface ShelterService {
    Shelter findShelterById(Long id);

    ShelterResponse getShelterResponse(Long id);

    ShelterResponse createShelter(ShelterDto shelterDto);

    ShelterResponse updateShelter(ShelterDto shelterDto, Long id);

    boolean hasUser(User user, Shelter shelter);

    List<Shelter> getShelters();

    List<UserResponse> getUsersOfShelter(long shelterId);
}

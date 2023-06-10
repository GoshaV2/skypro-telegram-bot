package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.dto.request.ShelterDto;
import com.skypro.skyprotelegrambot.dto.response.ShelterResponse;
import com.skypro.skyprotelegrambot.dto.response.UserResponse;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.exception.NotFoundElement;
import com.skypro.skyprotelegrambot.repository.ShelterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Класс определяющий методы обработки состояний приютов
 */

@Service
public class ShelterServiceImpl implements ShelterService {

    private final ShelterRepository shelterRepository;

    public ShelterServiceImpl(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    @Override
    @Transactional
    public Shelter findShelterById(Long id) {
        return shelterRepository.findShelterById(id)
                .orElseThrow(() -> new NotFoundElement(id, Shelter.class));
    }

    @Override
    public ShelterResponse getShelterResponse(Long id) {
        return ShelterResponse.from(findShelterById(id));
    }

    @Override
    public ShelterResponse createShelter(ShelterDto shelterDto) {
        Shelter shelter = new Shelter();
        shelter.setName(shelterDto.getName());
        return ShelterResponse.from(shelterRepository.save(shelter));
    }

    @Override
    public ShelterResponse updateShelter(ShelterDto shelterDto, Long id) {
        Shelter shelter = findShelterById(id);
        shelter.setName(shelterDto.getName());
        return ShelterResponse.from(shelterRepository.save(shelter));
    }

    @Override
    public boolean hasUser(User user, Shelter shelter) {
        return shelterRepository.hasUser(user, shelter);
    }

    @Override
    public List<Shelter> getShelters() {
        return shelterRepository.findAll();
    }

    @Override
    @Transactional
    public List<UserResponse> getUsersOfShelter(long shelterId) {
        Shelter shelter = findShelterById(shelterId);
        return UserResponse.from(shelter.getUserSet());
    }
}

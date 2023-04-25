package com.skypro.skyprotelegrambot.service;


import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.repository.ShelterRepository;
import org.springframework.stereotype.Service;

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
    public Shelter findShelterById(Long id) {
        return shelterRepository.findShelterById(id);
    }

    @Override
    public List<Shelter> getAllShelter() {
        return shelterRepository.findAll();
    }
}

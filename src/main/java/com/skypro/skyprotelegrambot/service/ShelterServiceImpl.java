package com.skypro.skyprotelegrambot.service;


import com.skypro.skyprotelegrambot.dto.request.ShelterDto;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.exception.NotFoundElement;
import com.skypro.skyprotelegrambot.repository.ShelterRepository;
import org.springframework.stereotype.Service;

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
        return shelterRepository.findShelterById(id)
                .orElseThrow(() -> new NotFoundElement(id, Shelter.class));
    }

    @Override
    public Shelter createShelter(ShelterDto shelterDto) {
        Shelter shelter = new Shelter();
        shelter = shelterRepository.save(new Shelter());
        return shelter;
    }

    @Override
    public Shelter updateShelter(ShelterDto shelterDto) {
        Shelter shelter = new Shelter();
        findShelterById(shelter.getId());
        shelter.setName(shelterDto.getName());
        return shelterRepository.save(new Shelter());
    }

}

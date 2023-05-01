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
        return shelterRepository.findShelterById(id);
    }

    @Override
    public ShelterDto createShelter(Long id, String name) {
        ShelterDto shelterDto = new ShelterDto();
        shelterDto.setId(shelterDto.getId());
        shelterDto.setName(shelterDto.getName());
        return shelterDto;
    }

    @Override
    public Shelter getShelterByName(String name) {
        return shelterRepository.findByName(name)
                .orElseThrow(() -> new NotFoundElement(name, Shelter.class));
    }

    @Override
    public ShelterDto updateShelter(String name) {
        getShelterByName(name);
        ShelterDto shelterDto = new ShelterDto();
        shelterDto.setName(shelterDto.getName());
        return shelterDto;
    }
}

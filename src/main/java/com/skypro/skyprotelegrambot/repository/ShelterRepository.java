package com.skypro.skyprotelegrambot.repository;

import com.skypro.skyprotelegrambot.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRepository extends JpaRepository<Shelter,Long> {

    Shelter findShelterById(Long id);
}

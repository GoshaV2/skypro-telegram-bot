package com.skypro.skyprotelegrambot.repository;

import com.skypro.skyprotelegrambot.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {

    Optional<Shelter> findShelterById(Long id);

    Optional<Shelter> findByName(String name);
}

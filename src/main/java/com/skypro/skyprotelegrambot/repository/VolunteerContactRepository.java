package com.skypro.skyprotelegrambot.repository;

import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.VolunteerContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VolunteerContactRepository extends JpaRepository<VolunteerContact, Long> {

    Optional<VolunteerContact> findVolunteerContactById(Long id);

    List<VolunteerContact> findAllByShelter(Shelter shelter);
}

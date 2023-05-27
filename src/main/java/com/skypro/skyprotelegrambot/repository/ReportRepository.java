package com.skypro.skyprotelegrambot.repository;

import com.skypro.skyprotelegrambot.entity.Report;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findById(Long id);

    List<Report> findAllByUserAndShelter(User user, Shelter shelter);
    
    List<Report> findAllByDateAndShelter(LocalDate date, Shelter shelter);
}

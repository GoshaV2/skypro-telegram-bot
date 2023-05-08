package com.skypro.skyprotelegrambot.repository;

import com.skypro.skyprotelegrambot.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report>
}

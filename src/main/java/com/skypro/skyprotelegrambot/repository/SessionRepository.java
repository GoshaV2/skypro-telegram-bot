package com.skypro.skyprotelegrambot.repository;

import com.skypro.skyprotelegrambot.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}

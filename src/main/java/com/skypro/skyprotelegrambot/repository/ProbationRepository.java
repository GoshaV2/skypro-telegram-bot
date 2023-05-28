package com.skypro.skyprotelegrambot.repository;

import com.skypro.skyprotelegrambot.entity.Probation;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProbationRepository extends JpaRepository<Probation, Long> {
    Optional<Probation> findByUserAndShelter(User user, Shelter shelter);

    List<Probation> findAllByUserChatIdAndShelterId(long chatId, long shelterId);
}

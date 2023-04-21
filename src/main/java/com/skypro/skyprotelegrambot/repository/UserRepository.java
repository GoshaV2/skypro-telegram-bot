package com.skypro.skyprotelegrambot.repository;

import com.skypro.skyprotelegrambot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByChatId(Long chatId);
}

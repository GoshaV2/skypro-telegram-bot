package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.entity.User;


/**
 * Общий сервис пользователей
 */
public interface UserService {
    User getUserById(long userId);

    boolean existUser(long chatId);

    User saveUser(User user);

    User findUserByChatId(Long chatId); //Получение пользователя по chat id

    User chooseShelterForUser(Long chatId, Long shelterId);

    User createUser(Long chatId, String name);

    User turnOnReportSending(User user);

    User turnOffReportSending(User user);
}

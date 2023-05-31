package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.entity.User;


/**
 * Общий сервис пользователей
 */
public interface UserService {
    User getUserById(long userId);

    boolean existUser(long chatId);

    User saveUser(User user);

    User findUserByChatId(Long chatId);

    /**
     * Выбор приюта и запись в сессию, так же сохраняет пользователя в базу приюта
     */
    User chooseShelterForUser(Long chatId, Long shelterId);

    User createUser(Long chatId, String name);

    User turnOnReportSending(User user);

    User turnOffReportSending(User user);

    /**
     * Очистить сессию пользователя с дополнительными флагами, которые обозначают отправку текстового сообщения
     * пользователем. Например флаг для отправки контактов будет очищен и переведен в состояние по умолчанию.
     */
    User clearSessionAdditionalFlags(User user);
}

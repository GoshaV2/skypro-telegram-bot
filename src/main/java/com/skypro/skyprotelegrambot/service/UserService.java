package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.entity.User;


/**
 * Общий сервис пользователей
 */
public interface UserService {
    /**
     * Получение пользователя
     *
     * @param userId идентификатор пользователя
     * @return пользователя с заданным идентификатором
     */
    User getUserById(long userId);

    /**
     * Существует ли пользователь в базе?
     *
     * @param chatId идентификатор пользователя
     * @return true если пользователь найден, false если нет.
     */
    boolean existUser(long chatId);

    /**
     * Запись пользователя в базу
     *
     * @param user записываемый пользователь
     * @return записанный пользователь
     */
    User saveUser(User user);

    /**
     * Поиск пользователя по идентификатору
     *
     * @param chatId идентификатор пользователя
     * @return найденного пользователя
     */
    User findUserByChatId(Long chatId); //Получение пользователя по chat id

    /**
     * Выбор приюта и запись в сессию, так же сохраняет пользователя в базу приюта
     */
    User chooseShelterForUser(Long chatId, Long shelterId);

    /**
     * Создание пользователя.
     *
     * @param chatId идентификатор пользователя
     * @param name   Имя пользователя
     * @return
     */
    User createUser(Long chatId, String name);

    /**
     * Включение режима отправки отчета по питомцу
     *
     * @param user пользователь для которого включается режим
     * @return пользователя с включенным режимом
     */
    User turnOnReportSending(User user);


    /**
     * Очистить сессию пользователя с дополнительными флагами, которые обозначают отправку текстового сообщения
     * пользователем. Например флаг для отправки контактов будет очищен и переведен в состояние по умолчанию.
     */
    User clearSessionAdditionalFlags(User user);
}

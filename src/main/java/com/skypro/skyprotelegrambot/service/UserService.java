package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.entity.User;


/**
 * Общий сервис пользователей
 */
public interface UserService {
    /**
     * Получение пользователя
     * @param userId идентификатор пользователя
     * @return пользователя с заданным идентификатором
     */
    User getUserById(long userId);

    /**
     * Существует ли пользователь в базе?
     * @param chatId идентификатор пользователя
     * @return true - если пользователь найден, false если нет.
     */
    boolean existUser(long chatId);

    /**
     * Запись пользователя в базу
     * @param user записываемый пользователь
     * @return записанный пользователь
     */
    User saveUser(User user);

    /**
     * Поиск пользователя по идентификатору
     * @param chatId идентификатор пользователя
     * @return найденного пользователя
     */
    User findUserByChatId(Long chatId); //Получение пользователя по chat id

    /**
     * Сохранение выбранного пользователем приюта
     * @param chatId идентификатор пользователя
     * @param shelterId идентификатор приюта который выбрал ползователь
     * @return пользователя с указанным идентификатором
     */
    User chooseShelterForUser(Long chatId, Long shelterId);

    /**
     * Создание пользователя.
     * @param chatId идентификатор пользователя
     * @return созданного пользователя
     */
    User createUser(Long chatId);

    /**
     * Создание пользователя.
     * @param chatId идентификатор пользователя
     * @param name Имя пользователя
     * @return
     */
    User createUser(Long chatId, String name);

    /**
     * Включение режима отправки отчета по питомцу
     * @param user пользователь для которого включается режим
     * @return пользователя с включенным режимом
     */
    User turnOnReportSending(User user);
    /**
     * Выключение режима отправки отчета по питомцу
     * @param user пользователь для которого выключается режим
     * @return пользователя с выключенным режимом
     */
    User turnOffReportSending(User user);
}

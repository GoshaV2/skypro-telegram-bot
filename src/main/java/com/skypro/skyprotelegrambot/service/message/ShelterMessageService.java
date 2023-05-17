package com.skypro.skyprotelegrambot.service.message;

import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.entity.Shelter;

public interface ShelterMessageService {
    /**
     * @param chatId чат куда шлем сообщение
     * @return сообщение с кнопками приютов
     */
    SendMessage getMessageForChoosingShelter(long chatId, boolean isFirstRequest);

    /**
     * @param chatId чат куда шлем сообщение
     * @return сообщение о выборе приюта.
     */
    SendMessage getMessageAfterChosenShelter(long chatId, long shelterId);

    /**
     * @param chatId  чат куда шлем сообщение
     * @param shelter выбранный пользователем приют
     * @return сообщение с кнопками по базовой информации о приюте
     */
    SendMessage getMessageWithBaseInfo(long chatId, Shelter shelter);

    /**
     * @param chatId  чат куда шлем сообщение
     * @param shelter выбранный пользователем приют
     * @return сообщение с кнопками по информации о том как взять питомца
     */
    SendMessage getMessageWithTakePetInfo(long chatId, Shelter shelter);

    /**
     * @param chatId  чат куда шлем сообщение
     * @param command идентификатор ответа по кнопке
     * @return текстовое сообщение из БД
     */
    SendMessage getAnswer(Long chatId, String command);

    /**
     * @param chatId  чат куда шлем сообщение
     * @param shelter выбранный пользователем приют
     * @return сообщение с указаниями по отчету
     */
    SendMessage getMessageBeforeReport(long chatId, Shelter shelter);
}

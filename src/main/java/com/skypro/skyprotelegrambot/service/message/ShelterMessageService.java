package com.skypro.skyprotelegrambot.service.message;

import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.Shelter;

public interface ShelterMessageService {
    /**
     * Сообщение выбора приюта
     *
     * @param chatId         чат куда шлем сообщение
     * @param isFirstRequest флаг первый раз ли пользователь обращется к боту или нет
     * @return сообщение с кнопками приютов
     */
    SendMessage getMessageForChoosingShelter(long chatId, boolean isFirstRequest);

    /**
     * @param chatId чат куда шлем сообщение
     * @return сообщение о выборе приюта.
     */
    SendMessage getMessageAfterChosenShelter(long chatId, Shelter shelter);

    /**
     * Сообщение с выбором вопроса по приюту и категории
     *
     * @param chatId   чат куда шлем сообщение
     * @param shelter  выбранный пользователем приют
     * @param category категория, по которой будет выбираться подменю с различной информацией о приюте
     * @return сообщение с кнопками по базовой информации о приюте
     */
    SendMessage getMessageWithAnswerMenuInfo(long chatId, Shelter shelter, Category category);


    /**
     * @param chatId чат куда шлем сообщение
     * @return текстовое сообщение из БД
     */
    SendMessage getAnswerMessage(Long chatId, long answerId);

    /**
     * @param chatId  чат куда шлем сообщение
     * @param shelter выбранный пользователем приют
     * @return сообщение с указаниями по отчету + кнопка назад.
     */
    SendMessage getMessageBeforeReport(long chatId, Shelter shelter);

    /**
     * @param chatId  чат куда шлем сообщение
     * @param shelter выбранный пользователем приют
     * @return сообщение об успешном принятии отчета + кнопки после выбора приюта.
     */
    SendMessage getMessageAfterReport(long chatId, Shelter shelter);

    /**
     * @param chatId  чат куда шлем сообщение
     * @return сообщение об отсутствии "испытательного срока" + кнопки после выбора приюта.
     */
    SendMessage getNoProbationMessage(long chatId);

    /**
     * @param chatId  чат куда шлем сообщение
     * @param shelter выбранный пользователем приют
     * @return сообщение о плохом отчете + кнопка назад.
     */
    SendMessage getBadReportMessage(long chatId, Shelter shelter);

    /**
     * @param chatId  чат куда шлем сообщение
     * @param shelter выбранный пользователем приют
     * @return сообщение о плохом отчете + кнопки после выбора приюта.
     */
    SendMessage getBadSavingReportMessage(long chatId, Shelter shelter);
}

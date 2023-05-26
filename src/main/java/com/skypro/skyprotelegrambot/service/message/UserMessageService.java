package com.skypro.skyprotelegrambot.service.message;

import com.pengrad.telegrambot.request.SendMessage;

/**
 * Описывает получение сообщений по информации о пользователе
 */
public interface UserMessageService {
    /**
     * Получение сообщения после сохранения контактов пользователя
     */
    SendMessage getMessageAfterSentContact(long chatId);

    SendMessage getMessageForOverdueReportToUser(long chatId, String shelterName);

    SendMessage getMessageForOverdueReportToVolunteer(long chatId, String userName, long userChatId);
}

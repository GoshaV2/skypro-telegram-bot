package com.skypro.skyprotelegrambot.service;

/**
 * Сервис для уведомления пользователей
 */
public interface NotificationService {
    void sendNotificationAboutOverdueReportToUser(long chatId, String shelterName);

    void sendNotificationAboutOverdueReportToVolunteer(long chatId, String userName, long userChatId);
}

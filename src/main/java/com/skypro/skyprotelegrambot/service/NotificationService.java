package com.skypro.skyprotelegrambot.service;

/**
 * Сервис для уведомления пользователей
 */
public interface NotificationService {
    /**
     * Уведомление о просроченном отчёте пользователю
     */
    void sendNotificationAboutOverdueReportToUser(long chatId, String shelterName);

    /**
     * Уведомление о просроченном отчёте волонтёру
     */
    void sendNotificationAboutOverdueReportToVolunteer(long chatId, String userName, long userChatId);

    /**
     * Уведомление об окончание испытательного срока и подведения результатов
     */
    void sendNotificationAboutPassProbation(long chatId, boolean isPassed);
}

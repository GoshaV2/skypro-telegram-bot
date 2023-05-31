package com.skypro.skyprotelegrambot.service;

import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.service.message.UserMessageService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final TelegramMessageService telegramMessageService;
    private final UserMessageService userMessageService;

    public NotificationServiceImpl(TelegramMessageService telegramMessageService, UserMessageService userMessageService) {
        this.telegramMessageService = telegramMessageService;
        this.userMessageService = userMessageService;
    }

    @Override
    public void sendNotificationAboutOverdueReportToUser(long chatId, String shelterName) {
        SendMessage sendMessage = userMessageService.getMessageForOverdueReportToUser(chatId, shelterName);
        telegramMessageService.execute(sendMessage);
    }

    @Override
    public void sendNotificationAboutOverdueReportToVolunteer(long chatId, String userName, long userChatId) {
        SendMessage sendMessage = userMessageService.getMessageForOverdueReportToVolunteer(chatId, userName, userChatId);
        telegramMessageService.execute(sendMessage);
    }

    @Override
    public void sendNotificationAboutPassProbation(long chatId, boolean isPassed) {
        SendMessage sendMessage = userMessageService.getMessageAboutPassedProbation(chatId, isPassed);
        telegramMessageService.execute(sendMessage);
    }
}

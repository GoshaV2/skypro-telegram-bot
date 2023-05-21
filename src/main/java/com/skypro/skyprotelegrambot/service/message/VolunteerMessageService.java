package com.skypro.skyprotelegrambot.service.message;

import com.pengrad.telegrambot.request.SendMessage;

public interface VolunteerMessageService {
    SendMessage getMessageAfterSentVolunteerContact(long chatId);
}

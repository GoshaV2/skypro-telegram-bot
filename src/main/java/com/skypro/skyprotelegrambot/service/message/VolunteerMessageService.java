package com.skypro.skyprotelegrambot.service.message;

import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.entity.Shelter;

public interface VolunteerMessageService {
    SendMessage getMessageAfterSentVolunteerContact(long chatId, Shelter shelter);
}

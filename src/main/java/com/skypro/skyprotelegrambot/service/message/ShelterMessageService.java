package com.skypro.skyprotelegrambot.service.message;

import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.entity.Shelter;

public interface ShelterMessageService {
    SendMessage getMessageForChoosingShelter(long chatId);

    SendMessage getMessageAfterChosenShelter(long chatId);

    SendMessage getMessageWithInfo(long chatId, Shelter shelter);

    SendMessage getAnswer(Long chatId, String command);
}

package com.skypro.skyprotelegrambot.service;

import com.pengrad.telegrambot.request.SendMessage;

public interface TelegramMessageService {
    void sendMessage(SendMessage sendMessage);
}

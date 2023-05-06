package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.Update;

public interface CommandHandler {
    boolean apply(Update update);

    void process(Update update);
}

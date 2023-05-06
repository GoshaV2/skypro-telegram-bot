package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class StartHandler implements CommandHandler {
    private final TelegramBot telegramBot;

    public StartHandler(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public boolean apply(Update update) {
        return false;
    }

    @Override
    public void process(Update update) {
        SendMessage sendMessage = new SendMessage(2,"test");
        telegramBot.execute(sendMessage);
    }
}

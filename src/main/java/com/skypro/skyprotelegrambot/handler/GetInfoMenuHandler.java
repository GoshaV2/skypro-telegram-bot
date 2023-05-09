package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.listener.TelegramBotUpdateListener;
import com.skypro.skyprotelegrambot.model.command.ShelterCommand;
import com.skypro.skyprotelegrambot.service.UserService;
import com.skypro.skyprotelegrambot.service.message.ShelterMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetInfoMenuHandler implements CommandHandler {
    private final TelegramBot telegramBot;
    private final ShelterMessageService shelterMessageService;
    private final UserService userService;
    private final TelegramBotUpdateListener telegramBotUpdateListener;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);

    public GetInfoMenuHandler(TelegramBot telegramBot, ShelterMessageService shelterMessageService, UserService userService, TelegramBotUpdateListener telegramBotUpdateListener) {
        this.telegramBot = telegramBot;
        this.shelterMessageService = shelterMessageService;
        this.userService = userService;
        this.telegramBotUpdateListener = telegramBotUpdateListener;
    }

    @Override
    public boolean apply(Update update) {
        Message message = update.message();
        Long chatId = message.chat().id();
        String text = message.text();
        if (chatId == null && text == null) {
            return false;
        }
        if (ShelterCommand.GET_INFO_MENU.getStartPath().equals(text)) {
            SendMessage sendMessage = shelterMessageService.getMessageWithInfo(chatId,
                    userService.findUserByChatId(chatId).getSession().getSelectedShelter());
            telegramBotUpdateListener.send(sendMessage);
        }
        return false;
    }

    @Override
    public void process(Update update) {
        Message message = update.message();
        Long chatId = message.chat().id();
        SendMessage sendMessage = shelterMessageService.getMessageWithInfo(chatId,
                userService.findUserByChatId(chatId).getSession().getSelectedShelter());
        telegramBotUpdateListener.send(sendMessage);
    }
}
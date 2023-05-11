package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.exception.UserNotFoundException;
import com.skypro.skyprotelegrambot.listener.TelegramBotUpdateListener;
import com.skypro.skyprotelegrambot.service.UserService;
import com.skypro.skyprotelegrambot.service.message.ShelterMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StartHandler implements CommandHandler {
    private final ShelterMessageService shelterMessageService;
    private final UserService userService;
    private final TelegramBotUpdateListener telegramBotUpdateListener;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);
    private final TelegramBot telegramBot;

    public StartHandler(ShelterMessageService shelterMessageService, UserService userService, TelegramBotUpdateListener telegramBotUpdateListener, TelegramBot telegramBot) {
        this.shelterMessageService = shelterMessageService;
        this.userService = userService;
        this.telegramBotUpdateListener = telegramBotUpdateListener;
        this.telegramBot = telegramBot;
    }

    @Override
    public boolean apply(Update update) {
        Message message = update.message();
        if (message == null) {
            return false;
        }
        Long chatId = message.chat().id();
        String text = message.text();
        if (chatId == null || text == null) {
            return false;
        }
        return "start".equals(text);
    }

    @Override
    public void process(Update update) {
        Message message = update.message();
        Long chatId = message.chat().id();
        try {
            userService.findUserByChatId(chatId);
        } catch (UserNotFoundException e) {
            userService.createUser(chatId);
            logger.info("New user success created");
        }
        SendMessage sendMessage = shelterMessageService.getMessageForChoosingShelter(chatId);
        telegramBotUpdateListener.send(sendMessage);
    }
}
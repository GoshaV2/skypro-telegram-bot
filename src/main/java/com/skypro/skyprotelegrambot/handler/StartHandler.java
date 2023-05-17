package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.exception.UserNotFoundException;
import com.skypro.skyprotelegrambot.listener.TelegramBotUpdateListener;
import com.skypro.skyprotelegrambot.service.TelegramMessageService;
import com.skypro.skyprotelegrambot.service.UserService;
import com.skypro.skyprotelegrambot.service.message.ShelterMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
/**
 * Обработка стартовой точки
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class StartHandler implements CommandHandler {
    private final ShelterMessageService shelterMessageService;
    private final UserService userService;
    private final TelegramMessageService telegramMessageService;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);

    public StartHandler(ShelterMessageService shelterMessageService, UserService userService, TelegramMessageService telegramMessageService) {
        this.shelterMessageService = shelterMessageService;
        this.userService = userService;
        this.telegramMessageService = telegramMessageService;
    }


    @Override
    public boolean apply(Update update) {
        Message message = update.message();
        CallbackQuery callbackQuery = update.callbackQuery();
        if (message == null && callbackQuery == null) {
            return false;
        }
        String command = message != null ? message.text() : callbackQuery.data();
        return "/start".equals(command);
    }

    @Override
    public void process(Update update) {
        Message message = update.message();
        CallbackQuery callbackQuery = update.callbackQuery();

        Long chatId = message != null ? message.chat().id() : callbackQuery.from().id();
        boolean isFirstRequest=false;
        try {
            userService.findUserByChatId(chatId);
        } catch (UserNotFoundException e) {
            isFirstRequest = true;
            userService.createUser(chatId);
            logger.info("New user success created");
        }
        SendMessage sendMessage = shelterMessageService.getMessageForChoosingShelter(chatId);
        telegramMessageService.execute(sendMessage);
    }
}
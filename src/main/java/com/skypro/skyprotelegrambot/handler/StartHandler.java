package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.listener.TelegramBotUpdateListener;
import com.skypro.skyprotelegrambot.model.command.UserCommand;
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
        return UserCommand.START.getCommand().equals(command);
    }

    @Override
    public void process(Update update) {
        Message message = update.message();
        CallbackQuery callbackQuery = update.callbackQuery();

        final Long chatId = message != null ? message.from().id() : callbackQuery.from().id();
        final User user = userService.findUserByChatId(chatId);
        final boolean isFirstRequest;
        if (callbackQuery != null) {
            isFirstRequest = false;
        } else {
            isFirstRequest = user.getSession().isFirstRequest();
        }
        SendMessage sendMessage = shelterMessageService.getMessageForChoosingShelter(chatId, isFirstRequest);
        telegramMessageService.execute(sendMessage);
    }
}
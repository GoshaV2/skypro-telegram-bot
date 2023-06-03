package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.entity.Session;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.model.command.UserCommand;
import com.skypro.skyprotelegrambot.service.PropertyMessageService;
import com.skypro.skyprotelegrambot.service.TelegramMessageService;
import com.skypro.skyprotelegrambot.service.UserService;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Обработчик для установки в сессии, что пользователь вводит контакты
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class UserContactInitHandler implements CommandHandler {
    private final UserService userService;
    private final PropertyMessageService propertyMessageService;
    private final TelegramMessageService telegramMessageService;

    public UserContactInitHandler(UserService userService, PropertyMessageService propertyMessageService, TelegramMessageService telegramMessageService) {
        this.userService = userService;
        this.propertyMessageService = propertyMessageService;
        this.telegramMessageService = telegramMessageService;
    }

    @Override
    public boolean apply(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery == null) {
            return false;
        }
        return UserCommand.SEND_CONTACT.getCommand().equals(callbackQuery.data());
    }

    /**
     * Обработка команды, добавление в сессию юзера параметра, что мы ожидаем контакты
     *
     * @param update
     */
    @Override
    public void process(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.from().id();
        User user = userService.findUserByChatId(chatId);
        userService.clearSessionAdditionalFlags(user);
        Session session = user.getSession();
        session.setHasWaitingContact(true);
        userService.saveUser(user);
        SendMessage sendMessage = new SendMessage(chatId, propertyMessageService.getMessage("contact.init"));
        telegramMessageService.execute(sendMessage);
    }
}

package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.skypro.skyprotelegrambot.entity.Session;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.service.TelegramMessageService;
import com.skypro.skyprotelegrambot.service.UserService;
import com.skypro.skyprotelegrambot.service.message.UserMessageService;
import org.springframework.stereotype.Component;

/**
 * Обработка получения контактов и сохранение
 */
@Component
public class UserSendContactHandler implements CommandHandler {
    private final UserService userService;
    private final TelegramMessageService telegramMessageService;
    private final UserMessageService userMessageService;

    public UserSendContactHandler(UserService userService, TelegramMessageService telegramMessageService, UserMessageService userMessageService) {
        this.userService = userService;
        this.telegramMessageService = telegramMessageService;
        this.userMessageService = userMessageService;
    }


    @Override
    public boolean apply(Update update) {
        Message message = update.message();
        if (message == null) {
            return false;
        }
        long chatId = message.from().id();
        User user = userService.findUserByChatId(chatId);
        Session session = user.getSession();
        return session.hasWaitingContact();
    }

    @Override
    public void process(Update update) {
        Message message = update.message();
        String contact = message.text();
        long chatId = message.from().id();
        User user = userService.findUserByChatId(chatId);
        user.setContact(contact);
        userService.saveUser(user);
        userService.clearSessionAdditionalFlags(user);
        telegramMessageService.execute(userMessageService.getMessageAfterSentContact(chatId));
    }
}

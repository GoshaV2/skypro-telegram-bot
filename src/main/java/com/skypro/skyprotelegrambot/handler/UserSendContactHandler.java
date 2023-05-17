package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.entity.Session;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.service.PropertyMessageService;
import com.skypro.skyprotelegrambot.service.TelegramMessageService;
import com.skypro.skyprotelegrambot.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserSendContactHandler implements CommandHandler {
    private final UserService userService;
    private final PropertyMessageService propertyMessageService;
    private final TelegramMessageService telegramMessageService;

    public UserSendContactHandler(UserService userService, PropertyMessageService propertyMessageService, TelegramMessageService telegramMessageService) {
        this.userService = userService;
        this.propertyMessageService = propertyMessageService;
        this.telegramMessageService = telegramMessageService;
    }

    @Override
    public boolean apply(Update update) {
        Message message = update.message();
        if (message == null) {
            return false;
        }
        long chatId = message.chat().id();
        User user = userService.findUserByChatId(chatId);
        Session session = user.getSession();
        return session.hasWaitingContact();
    }

    @Override
    public void process(Update update) {
        Message message = update.message();
        String contact = message.text();
        long chatId = message.chat().id();
        User user = userService.findUserByChatId(chatId);
        user.setContact(contact);
        userService.saveUser(user);
        SendMessage sendMessage = new SendMessage(chatId, propertyMessageService.getMessage("contact.sent"));
        telegramMessageService.execute(sendMessage);
    }
}

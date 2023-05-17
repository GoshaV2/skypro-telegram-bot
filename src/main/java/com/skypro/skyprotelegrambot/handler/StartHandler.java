package com.skypro.skyprotelegrambot.handler;

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
import org.springframework.stereotype.Component;
/**
 * Обработка стартовой точки
 */
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
        String command = (update.message() != null) ? update.message().text() : update.callbackQuery().data(); //команда может прилететь из калбэка см. ShelterButtonServiceImpl
        Long chatId = (update.message() != null) ? update.message().from().id() : update.callbackQuery().from().id();

        if (chatId == null || command == null) { //Зачем проверка?
            return false;
        }

        return "/start".equals(command);
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
        telegramMessageService.execute(sendMessage);
    }
}
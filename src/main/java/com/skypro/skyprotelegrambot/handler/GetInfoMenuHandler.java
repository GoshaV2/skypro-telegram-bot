package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.listener.TelegramBotUpdateListener;
import com.skypro.skyprotelegrambot.model.command.ShelterCommand;
import com.skypro.skyprotelegrambot.service.TelegramMessageService;
import com.skypro.skyprotelegrambot.service.UserService;
import com.skypro.skyprotelegrambot.service.message.ShelterMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class GetInfoMenuHandler implements CommandHandler {
    private final ShelterMessageService shelterMessageService;
    private final UserService userService;
    private final TelegramMessageService telegramMessageService;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);

    public GetInfoMenuHandler(ShelterMessageService shelterMessageService, UserService userService, TelegramMessageService telegramMessageService) {
        this.shelterMessageService = shelterMessageService;
        this.userService = userService;
        this.telegramMessageService = telegramMessageService;
    }


    @Override
    public boolean apply(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery == null) {
            return false;
        }
        Long chatId = callbackQuery.from().id();
        String command = callbackQuery.data();
        if (chatId == null && command == null) {
            return false;
        }
        return ShelterCommand.GET_INFO_MENU.getStartPath().equals(command);
    }

    @Override
    public void process(Update update) {
        CallbackQuery callbackQuery=update.callbackQuery();
        Long chatId = callbackQuery.from().id();
        SendMessage sendMessage = shelterMessageService.getMessageWithInfo(chatId,
                userService.findUserByChatId(chatId).getSession().getSelectedShelter());
        telegramMessageService.sendMessage(sendMessage);
    }
}
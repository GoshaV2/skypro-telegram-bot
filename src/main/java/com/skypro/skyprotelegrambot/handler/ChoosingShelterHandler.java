package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.listener.TelegramBotUpdateListener;
import com.skypro.skyprotelegrambot.model.command.ShelterCommand;
import com.skypro.skyprotelegrambot.service.UserService;
import com.skypro.skyprotelegrambot.service.message.ShelterMessageService;

public class ChoosingShelterHandler implements CommandHandler {
    private final TelegramBot telegramBot;
    private final ShelterMessageService shelterMessageService;
    private final UserService userService;
    private final TelegramBotUpdateListener telegramBotUpdateListener;

    public ChoosingShelterHandler(TelegramBot telegramBot, ShelterMessageService shelterMessageService, UserService userService, TelegramBotUpdateListener telegramBotUpdateListener) {
        this.telegramBot = telegramBot;
        this.shelterMessageService = shelterMessageService;
        this.userService = userService;
        this.telegramBotUpdateListener = telegramBotUpdateListener;
    }

    @Override
    public boolean apply(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery == null) {
            return false;
        }
        Long chatId = callbackQuery.from().id();
        String text = callbackQuery.message().text();
        if (text.matches(ShelterCommand.CHOOSE_SHELTER.getStartPathPattern())) {
            Long shelterId = Long.parseLong(text.replace(ShelterCommand.CHOOSE_SHELTER.getStartPath(), ""));
            userService.chooseShelterForUser(chatId, shelterId);
            SendMessage sendMessage = shelterMessageService.getMessageAfterChosenShelter(chatId);
            telegramBotUpdateListener.send(sendMessage);
        }
        return false;
    }

    @Override
    public void process(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Long chatId = callbackQuery.from().id();
        String text = callbackQuery.message().text();
        Long shelterId = Long.parseLong(text.replace(ShelterCommand.CHOOSE_SHELTER.getStartPath(), ""));
        userService.chooseShelterForUser(chatId, shelterId);
        SendMessage sendMessage = shelterMessageService.getMessageAfterChosenShelter(chatId);
        telegramBotUpdateListener.send(sendMessage);
    }
}
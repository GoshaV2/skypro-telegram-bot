package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.listener.TelegramBotUpdateListener;
import com.skypro.skyprotelegrambot.model.command.ShelterCommand;
import com.skypro.skyprotelegrambot.service.TelegramMessageService;
import com.skypro.skyprotelegrambot.service.UserService;
import com.skypro.skyprotelegrambot.service.message.ShelterMessageService;
import org.springframework.stereotype.Component;

/**
 * Обработка выбора приюта
 */
@Component
public class ChoosingShelterHandler implements CommandHandler {
    private final ShelterMessageService shelterMessageService;
    private final UserService userService;
    private final TelegramMessageService telegramMessageService;

    public ChoosingShelterHandler(ShelterMessageService shelterMessageService, UserService userService, TelegramMessageService telegramMessageService) {
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
        String command = callbackQuery.data();
        return command.matches(ShelterCommand.CHOOSE_SHELTER.getStartPathPattern());
    }

    @Override
    public void process(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Long chatId = callbackQuery.from().id();
        String text = callbackQuery.data();
        Long shelterId = Long.parseLong(text.replace(ShelterCommand.CHOOSE_SHELTER.getStartPath(), ""));
        userService.chooseShelterForUser(chatId, shelterId);
        SendMessage sendMessage = shelterMessageService.getMessageAfterChosenShelter(chatId);
        telegramMessageService.sendMessage(sendMessage);
    }
}
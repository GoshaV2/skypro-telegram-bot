package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.User;
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

/**
 * Обработчик базовой информации о приюте по категории
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class GetInfoMenuHandler implements CommandHandler {
    private final ShelterMessageService shelterMessageService;
    private final UserService userService;
    private final TelegramMessageService telegramMessageService;

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
        String command = callbackQuery.data();
        return command.matches(ShelterCommand.GET_INFO_MENU.getStartPathPattern());
    }

    @Override
    public void process(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Long chatId = callbackQuery.from().id();
        String command = callbackQuery.data();
        Category category = Category.valueOf(command
                .replace(ShelterCommand.GET_INFO_MENU.getStartPath(), ""));
        User user = userService.findUserByChatId(chatId);
        SendMessage sendMessage = shelterMessageService.getMessageWithAnswerMenuInfo(chatId,
                user.getSession().getSelectedShelter(), category
        );
        telegramMessageService.execute(sendMessage);
    }
}
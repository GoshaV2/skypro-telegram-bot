package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.model.command.ShelterCommand;
import com.skypro.skyprotelegrambot.service.TelegramMessageService;
import com.skypro.skyprotelegrambot.service.UserService;
import com.skypro.skyprotelegrambot.service.message.ShelterMessageService;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Обработка выбора приюта
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
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

    /**
     * Записать в сессию пользователя информацию о выбраном приюте
     */
    @Override
    public void process(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Long chatId = callbackQuery.from().id();
        String text = callbackQuery.data();
        long shelterId = Long.parseLong(text.replace(ShelterCommand.CHOOSE_SHELTER.getStartPath(), ""));
        User user = userService.chooseShelterForUser(chatId, shelterId);
        userService.clearSessionAdditionalFlags(user);// на сучай возврата из меню отправки отчета о питомце
        SendMessage sendMessage = shelterMessageService.getMessageAfterChosenShelter(chatId, user.getSession().getSelectedShelter());
        telegramMessageService.execute(sendMessage);
    }
}
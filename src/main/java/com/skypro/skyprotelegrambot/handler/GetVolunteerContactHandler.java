package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.model.command.VolunteerCommand;
import com.skypro.skyprotelegrambot.service.TelegramMessageService;
import com.skypro.skyprotelegrambot.service.UserService;
import com.skypro.skyprotelegrambot.service.message.VolunteerMessageService;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class GetVolunteerContactHandler implements CommandHandler {
    private final VolunteerMessageService volunteerMessageService;
    private final TelegramMessageService telegramMessageService;
    private final UserService userService;

    public GetVolunteerContactHandler(VolunteerMessageService volunteerMessageService, TelegramMessageService telegramMessageService, UserService userService) {
        this.volunteerMessageService = volunteerMessageService;
        this.telegramMessageService = telegramMessageService;
        this.userService = userService;
    }

    @Override
    public boolean apply(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery == null) {
            return false;
        }
        return VolunteerCommand.SEND_VOLUNTEER_CONTACT.getCommand().equals(callbackQuery.data());
    }

    @Override
    public void process(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.from().id();
        User user = userService.findUserByChatId(chatId);
        SendMessage sendMessage = volunteerMessageService.getMessageAfterSentVolunteerContact(chatId,
                user.getSession().getSelectedShelter());
        telegramMessageService.execute(sendMessage);
    }
}
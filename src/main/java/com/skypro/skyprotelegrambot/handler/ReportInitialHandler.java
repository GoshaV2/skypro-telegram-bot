package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.model.command.ShelterCommand;
import com.skypro.skyprotelegrambot.service.TelegramMessageService;
import com.skypro.skyprotelegrambot.service.UserService;
import com.skypro.skyprotelegrambot.service.message.ShelterMessageService;
import org.springframework.stereotype.Component;

@Component
public class ReportInitialHandler implements CommandHandler {
    private final UserService userService;
    private final ShelterMessageService shelterMessageService;
    private final TelegramMessageService telegramMessageService;

    public ReportInitialHandler(UserService userService, ShelterMessageService shelterMessageService,
                                TelegramMessageService telegramMessageService) {
        this.userService = userService;
        this.shelterMessageService = shelterMessageService;
        this.telegramMessageService = telegramMessageService;
    }


    @Override
    public boolean apply(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (update.callbackQuery() == null) {
            return false;
        }
        String command = callbackQuery.data();
        if (command == null) {
            return false;
        }
        return command.equals(ShelterCommand.SEND_REPORT.getStartPath());
    }

    @Override
    public void process(Update update) {
        Long id = update.callbackQuery().from().id();
        User user = userService.findUserByChatId(id);

        userService.turnOnReportSending(user);

        SendMessage sendMessage = shelterMessageService.getMessageBeforeReport(id,
                user.getSession().getSelectedShelter());
        telegramMessageService.execute(sendMessage);

    }
}

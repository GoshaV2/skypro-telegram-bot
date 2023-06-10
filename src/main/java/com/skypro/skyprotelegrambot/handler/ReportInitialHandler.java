package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.entity.ProbationStatus;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.exception.NotFoundElement;
import com.skypro.skyprotelegrambot.model.command.ShelterCommand;
import com.skypro.skyprotelegrambot.service.ProbationService;
import com.skypro.skyprotelegrambot.service.TelegramMessageService;
import com.skypro.skyprotelegrambot.service.UserService;
import com.skypro.skyprotelegrambot.service.message.ShelterMessageService;
import org.springframework.stereotype.Component;

@Component
public class ReportInitialHandler implements CommandHandler {
    private final UserService userService;
    private final ShelterMessageService shelterMessageService;
    private final TelegramMessageService telegramMessageService;
    private final ProbationService probationService;

    public ReportInitialHandler(UserService userService, ShelterMessageService shelterMessageService,
                                TelegramMessageService telegramMessageService, ProbationService probationService) {
        this.userService = userService;
        this.shelterMessageService = shelterMessageService;
        this.telegramMessageService = telegramMessageService;
        this.probationService = probationService;
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
        try {
            probationService.getProbation(user, user.getSession().getSelectedShelter(),
                    ProbationStatus.APPOINTED);
        } catch (NotFoundElement e) {
            SendMessage sendMessage = shelterMessageService.getNoProbationMessage(id);
            telegramMessageService.execute(sendMessage);
            return;
        }
        userService.clearSessionAdditionalFlags(user);
        userService.turnOnReportSending(user);

        SendMessage sendMessage = shelterMessageService.getMessageBeforeReport(id,
                user.getSession().getSelectedShelter());
        telegramMessageService.execute(sendMessage);

    }
}

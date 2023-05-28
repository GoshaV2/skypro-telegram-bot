package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.service.ReportService;
import com.skypro.skyprotelegrambot.service.TelegramMessageService;
import com.skypro.skyprotelegrambot.service.UserService;
import com.skypro.skyprotelegrambot.service.message.ShelterMessageService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ReportSendHandler implements CommandHandler {
    private final UserService userService;
    private final ShelterMessageService shelterMessageService;
    private final TelegramMessageService telegramMessageService;
    private final ReportService reportService;

    public ReportSendHandler(UserService userService, ShelterMessageService shelterMessageService,
                             TelegramMessageService telegramMessageService, ReportService reportService) {
        this.userService = userService;
        this.shelterMessageService = shelterMessageService;
        this.telegramMessageService = telegramMessageService;
        this.reportService = reportService;
    }


    @Override
    public boolean apply(Update update) {
        Message message = update.message();
        CallbackQuery callbackQuery = update.callbackQuery();
        Long id = (message != null) ? message.from().id() : callbackQuery.from().id();

        return userService.findUserByChatId(id).getSession().isReportSending();
    }

    @Override
    public void process(Update update) {
        Message message = update.message();
        CallbackQuery callbackQuery = update.callbackQuery();

        if (callbackQuery != null) {
            User user = userService.findUserByChatId(callbackQuery.from().id());
            userService.clearSessionAdditionalFlags(user);
            return;
        }

        Long id = message.from().id();
        PhotoSize[] photoSizes = message.photo();
        String caption = message.caption();
        User user = userService.findUserByChatId(id);

        if (photoSizes == null || caption == null) {
            SendMessage sendMessage =
                    shelterMessageService.getMessageBeforeReport(id, user.getSession().getSelectedShelter());
            telegramMessageService.execute(sendMessage);
            return;
        }

        GetFile getfile = new GetFile(photoSizes[0].fileId());
        GetFileResponse getFileResponse = telegramMessageService.execute(getfile);
        File file = getFileResponse.file();

        try {
            byte[] photoFile = telegramMessageService.getFileContent(file);
            reportService.createReport(user, caption, photoFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

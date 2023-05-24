package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.service.FileServise;
import com.skypro.skyprotelegrambot.service.ReportService;
import com.skypro.skyprotelegrambot.service.TelegramMessageService;
import com.skypro.skyprotelegrambot.service.UserService;
import com.skypro.skyprotelegrambot.service.message.ShelterMessageService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Component
public class ReportSendHandler implements CommandHandler {
    private final Path DIRECTORY_PATH =
            Path.of("C:\\Users\\offic\\IdeaProjects\\skypro-telegram-bot\\src\\main\\resources\\photos");
    private final UserService userService;
    private final ShelterMessageService shelterMessageService;
    private final TelegramMessageService telegramMessageService;
    private final ReportService reportService;
    private final FileServise fileService;

    public ReportSendHandler(UserService userService, ShelterMessageService shelterMessageService,
                             TelegramMessageService telegramMessageService, ReportService reportService,
                             FileServise fileService) {
        this.userService = userService;
        this.shelterMessageService = shelterMessageService;
        this.telegramMessageService = telegramMessageService;
        this.reportService = reportService;
        this.fileService = fileService;
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
            userService.turnOffReportSending(user);
            //отправить сообщение предыдущего меню.
            SendMessage sendMessage = shelterMessageService.getMessageAfterChosenShelter(callbackQuery.from().id(),
                    user.getSession().getSelectedShelter());
            telegramMessageService.execute(sendMessage);
            return;
        }

        Long id = message.from().id();
        PhotoSize[] photoSizes = message.photo();

        if (photoSizes != null){
            for (PhotoSize ps:photoSizes) {
                System.out.println(ps);
            }
            System.out.println("***");
        }

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
        String fileName = photoSizes[photoSizes.length-1].fileId()+"."+getFileExtension(file.filePath());
        Path filePath = DIRECTORY_PATH.resolve(fileName);
//добавление отчета в базу и сохранение фото
        try {
            byte[] photoFile = telegramMessageService.getFileContent(file);
            fileService.saveFile(filePath,photoFile);
            reportService.createReport(user, caption, filePath.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
 //отправка сообщения пользователю что отчет принят
        SendMessage sendMessage = shelterMessageService.getMessageAfterReport(id,
                user.getSession().getSelectedShelter());
        telegramMessageService.execute(sendMessage);
    }
    private String getFileExtension(String filePath){
        String[] array = filePath.split("\\.");
        return array[array.length-1];
    }
}

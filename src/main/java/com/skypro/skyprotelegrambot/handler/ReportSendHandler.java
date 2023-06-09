package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.skypro.skyprotelegrambot.entity.Probation;
import com.skypro.skyprotelegrambot.entity.ProbationStatus;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.service.*;
import com.skypro.skyprotelegrambot.service.message.ShelterMessageService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Component
public class ReportSendHandler implements CommandHandler {
    private final Path DIRECTORY_PATH =
            Path.of("photos/");
    private final UserService userService;
    private final ShelterMessageService shelterMessageService;
    private final TelegramMessageService telegramMessageService;
    private final ReportService reportService;
    private final FileService fileService;

    private final ProbationService probationService;

    public ReportSendHandler(UserService userService, ShelterMessageService shelterMessageService,
                             TelegramMessageService telegramMessageService, ReportService reportService,
                             FileService fileService, ProbationService probationService) {
        this.userService = userService;
        this.shelterMessageService = shelterMessageService;
        this.telegramMessageService = telegramMessageService;
        this.reportService = reportService;
        this.fileService = fileService;
        this.probationService = probationService;
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
        Long id = message.from().id();
        PhotoSize[] photoSizes = message.photo();
        String caption = message.caption();
        User user = userService.findUserByChatId(id);
        Probation probation = probationService.getProbation(user, user.getSession().getSelectedShelter(), ProbationStatus.APPOINTED);

        if (photoSizes == null || caption == null) {
            SendMessage sendMessage =
                    shelterMessageService.getBadReportMessage(id, user.getSession().getSelectedShelter());
            telegramMessageService.execute(sendMessage);
            return;
        }

        try {
            createReport(probation, caption, photoSizes);
        } catch (IOException e) {
            telegramMessageService.execute(shelterMessageService.getBadSavingReportMessage(id,
                    user.getSession().getSelectedShelter()));
            e.printStackTrace();
            return;
        } finally {
            userService.clearSessionAdditionalFlags(user);
        }
        //отправка сообщения пользователю что отчет принят
        SendMessage sendMessage = shelterMessageService.getMessageAfterReport(id,
                user.getSession().getSelectedShelter());
        telegramMessageService.execute(sendMessage);
    }

    //добавление отчета в базу и сохранение фото
    private void createReport(Probation probation, String text, PhotoSize[] photos) throws IOException {
        GetFile getfile = new GetFile(photos[photos.length - 1].fileId());
        GetFileResponse getFileResponse = telegramMessageService.execute(getfile);
        File file = getFileResponse.file();
        String fileName = photos[photos.length - 1].fileUniqueId() + ".jpg"; //опытным путем установлено что фотки всегда приходят .jpg
        Path filePath = DIRECTORY_PATH.resolve(fileName);
        byte[] photoFile = telegramMessageService.getFileContent(file);
        fileService.saveFile(filePath, photoFile);
        reportService.createReport(probation, text, filePath.toString());
    }
}

package com.skypro.skyprotelegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.skypro.skyprotelegrambot.handler.CommandHandler;
import com.skypro.skyprotelegrambot.service.AnswerService;
import com.skypro.skyprotelegrambot.service.UserService;
import com.skypro.skyprotelegrambot.service.message.ShelterMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

@Component
public class TelegramBotUpdateListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final ShelterMessageService shelterMessageService;
    private final UserService userService;
    private final AnswerService answerService;
    private final Collection<CommandHandler> commandHandlers;

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);

    public TelegramBotUpdateListener(TelegramBot telegramBot, ShelterMessageService shelterMessageService,
                                     UserService userService, AnswerService answerService,
                                     Collection<CommandHandler> commandHandlers) {
        this.telegramBot = telegramBot;
        this.shelterMessageService = shelterMessageService;
        this.userService = userService;
        this.answerService = answerService;
        this.commandHandlers = commandHandlers;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> list) {
        try {
            list.forEach(update -> {
                for (CommandHandler commandHandler : commandHandlers) {
                    if (commandHandler.apply(update)) {
                        commandHandler.process(update);
                        break;
                    }
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
/*
    private void send(SendMessage sendMessage) {
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    private void sendReport(User user, PhotoSize[] photo, String caption) {
        //to do something... SELECT * FROM users INNER JOIN session ON users.session_id=session.id;

        String fileId = photo[photo.length - 1].fileId();//самая крупная фотка лежит в конце массива
        GetFileResponse getFileResponse = telegramBot.execute(new GetFile(fileId));
        File file = getFileResponse.file();
        try {
            byte[] photoFile = telegramBot.getFileContent(file);
            reportService.CreateReport(user, caption, photoFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


        userService.turnOffReportSending(user);
    }
*/
}

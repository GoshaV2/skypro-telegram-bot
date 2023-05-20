package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.listener.TelegramBotUpdateListener;
import com.skypro.skyprotelegrambot.service.AnswerService;
import com.skypro.skyprotelegrambot.service.TelegramMessageService;
import com.skypro.skyprotelegrambot.service.message.ShelterMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class AnswerHandler implements CommandHandler {
    private final ShelterMessageService shelterMessageService;
    private final AnswerService answerService;
    private final TelegramMessageService telegramMessageService;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);

    public AnswerHandler(ShelterMessageService shelterMessageService, AnswerService answerService, TelegramMessageService telegramMessageService) {
        this.shelterMessageService = shelterMessageService;
        this.answerService = answerService;
        this.telegramMessageService = telegramMessageService;
    }


    @Override
    public boolean apply(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery == null) {
            return false;
        }
        String command = callbackQuery.data();
        return answerService.hasCommand(command);
    }

    @Override
    public void process(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Long chatId = callbackQuery.from().id();
        String command = callbackQuery.data();
        SendMessage sendMessage = shelterMessageService.getAnswer(chatId, command);
        telegramMessageService.execute(sendMessage);
    }
}
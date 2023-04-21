package com.skypro.skyprotelegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class TelegramBotUpdateListener implements UpdatesListener {
    private final TelegramBot telegramBot;

    public TelegramBotUpdateListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> list) {
        list.forEach(update -> {
            Message message = update.message();
            Long chatId = message.chat().id();
            String text = message.text();
            AbstractSendRequest<SendMessage> sendRequest;
            sendRequest = new SendMessage(chatId, "hello world");
            SendResponse sendResponse = telegramBot.execute(sendRequest);
            if (!sendResponse.isOk()) {
                System.out.println("Ошибка");
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}

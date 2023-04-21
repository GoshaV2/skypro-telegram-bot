package com.skypro.skyprotelegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
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
            Message message=update.message();
            Long chatId=message.chat().id();
            String text=message.text();
            if("/start".equals(text)){
                SendMessage sendMessage=new SendMessage(chatId,"Привет!");
                SendResponse sendResponse= telegramBot.execute(sendMessage);
                if(!sendResponse.isOk()){
                    System.out.println("Ошибка");
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}

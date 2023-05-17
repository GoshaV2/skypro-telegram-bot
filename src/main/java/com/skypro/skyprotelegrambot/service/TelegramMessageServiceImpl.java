package com.skypro.skyprotelegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import com.skypro.skyprotelegrambot.listener.TelegramBotUpdateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TelegramMessageServiceImpl implements TelegramMessageService{
    private final TelegramBot telegramBot;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);

    public TelegramMessageServiceImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse>  R execute(BaseRequest<T, R> request) {
        R response = telegramBot.execute(request);
        if (!response.isOk()) {
            logger.error("Error during sending message: {}", response.description());
        }
        return response;
    }
    @Override
    public byte[] getFileContent(File file) throws IOException {
       return telegramBot.getFileContent(file);
    }
}

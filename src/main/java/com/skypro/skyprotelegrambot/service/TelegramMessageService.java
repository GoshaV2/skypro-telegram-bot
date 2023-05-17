package com.skypro.skyprotelegrambot.service;

import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;

import java.io.IOException;

public interface TelegramMessageService {

    <T extends BaseRequest<T, R>, R extends BaseResponse>  R execute(BaseRequest<T, R> request);

    byte[] getFileContent(File file) throws IOException;
}

package com.skypro.skyprotelegrambot.service;

import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;

import java.io.IOException;

public interface TelegramMessageService {
    /**
     * Исполнение запроса ботом
     *
     * @param request исполняемый запрос
     * @param <T>     extends BaseRequest<T, R>
     * @param <R>     extends BaseResponse
     * @return ответ на исполненный запрос
     */
    <T extends BaseRequest<T, R>, R extends BaseResponse> R execute(BaseRequest<T, R> request);

    /**
     * Получение содержимого файла
     *
     * @param file запрашивавемый файл (см. com.pengrad.telegrambot.model)
     * @return содержимое файла как массив байт
     * @throws IOException
     */
    byte[] getFileContent(File file) throws IOException;
}

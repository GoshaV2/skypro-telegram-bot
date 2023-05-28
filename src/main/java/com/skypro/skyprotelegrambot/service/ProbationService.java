package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.dto.request.ProbationDto;
import com.skypro.skyprotelegrambot.dto.response.ProbationResponse;

public interface ProbationService {
    /**
     * Создать испытательный срок
     */
    ProbationResponse createProbation(ProbationDto probationDto);

    /**
     * Проверить сдачу отчётов и отправить уведомления, если пользователь не прислал отчёт
     */
    void sendNotificationAboutReport();
}

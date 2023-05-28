package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.dto.request.ProbationAddAdditionalDaysDto;
import com.skypro.skyprotelegrambot.dto.request.ProbationChangeStatusDto;
import com.skypro.skyprotelegrambot.dto.request.ProbationDto;
import com.skypro.skyprotelegrambot.dto.response.ProbationResponse;

import java.util.List;

public interface ProbationService {
    /**
     * Создать испытательный срок
     */
    ProbationResponse createProbation(ProbationDto probationDto);

    /**
     * Проверить сдачу отчётов и отправить уведомления, если пользователь не прислал отчёт
     */
    void sendNotificationAboutReport();

    /**
     * Поменять статус испытательного срока
     */
    ProbationResponse changeProbationStatus(ProbationChangeStatusDto probationDto, long probationId);

    /**
     * Получить список испытательных сроков по пользователю в приюте
     */
    List<ProbationResponse> getUserProbationByShelter(long chatId, long shelterId);

    /**
     * Добавить дополнительные дни к испытательному сроку
     */
    ProbationResponse addAdditionalDays(ProbationAddAdditionalDaysDto probationDto, long id);
}

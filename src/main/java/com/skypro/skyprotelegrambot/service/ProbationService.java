package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.dto.request.ProbationDto;
import com.skypro.skyprotelegrambot.dto.response.ProbationResponse;
import com.skypro.skyprotelegrambot.entity.Probation;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;

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
     * Получение "испытательного срока" для заданного пользователя и приюта
     * @param user пользователь которому назначен "испытательный срок"
     * @param shelter приют который назначил "испытательный срок" пользователю
     * @return объект испытательного срока, если такого нет выбрасывается исключение NotFoundElement
     */
    Probation getProbation(User user, Shelter shelter);

    List<Probation> gatAllByShelter (Shelter shelter);
}

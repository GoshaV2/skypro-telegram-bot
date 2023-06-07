package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.dto.request.ProbationAddAdditionalDaysDto;
import com.skypro.skyprotelegrambot.dto.request.ProbationDto;
import com.skypro.skyprotelegrambot.dto.response.ProbationResponse;
import com.skypro.skyprotelegrambot.entity.Probation;
import com.skypro.skyprotelegrambot.entity.ProbationStatus;
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
     * Поменять статус испытательного срока
     */
    ProbationResponse changeProbationStatus(ProbationStatus probationStatus, long probationId);

    /**
     * Получить список испытательных сроков по пользователю в приюте
     */
    List<ProbationResponse> getUserProbationByShelter(long chatId, long shelterId);

    /**
     * Добавить дополнительные дни к испытательному сроку
     */
    ProbationResponse addAdditionalDays(ProbationAddAdditionalDaysDto probationDto, long id);

    /**
     * Получение "испытательного срока" для заданного пользователя и приюта
     *
     * @param user    пользователь которому назначен "испытательный срок"
     * @param shelter приют который назначил "испытательный срок" пользователю
     * @return объект испытательного срока, если такого нет выбрасывается исключение NotFoundElement
     */
    Probation getProbation(User user, Shelter shelter);

    /**
     * Получение "испытательного срока" для заданного пользователя и приюта по статусу
     *
     * @param user            пользователь которому назначен "испытательный срок"
     * @param shelter         приют который назначил "испытательный срок" пользователю
     * @param probationStatus статус испытательного срока
     * @return объект испытательного срока, если такого нет выбрасывается исключение NotFoundElement
     */
    Probation getProbation(User user, Shelter shelter, ProbationStatus probationStatus);

    List<Probation> gatAllByShelter(Shelter shelter);
}

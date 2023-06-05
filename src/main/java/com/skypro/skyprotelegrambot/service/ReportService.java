package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.entity.Probation;
import com.skypro.skyprotelegrambot.entity.Report;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.model.OverdueDayData;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    List<Report> getAll();

    /**
     * Создание отчета в БД
     * @param probation "испытательный срок" к которому относится отчет
     * @param text текст отчета
     * @param photoPath путь к фотографии
     * @return созданный отчет
     */
    Report createReport(Probation probation, String text, String photoPath);

    /**
     * Получение отчета по идентификатору
     * @param id идентификатор отчета
     * @return очет с указанным идентификатором
     */
    Report getById(Long id);

    /**
     * Формирование списка отчетов пользователя по заданному приюту
     * @param probation "испытательный срок" для формирования списка
     * @return Список отчетов пользователя по заданному приюту
     */
    List<Report> getAllByProbation(Probation probation);

    /**
     * Формирут список отчетов по дате и приюту
     * @param date дата для формирования списка
     * @param probation "испытательный срок" для формирования списка
     * @return список отчетов для заданного приют на заданную дату
     */
    List<Report> getAllByDateAndProbation(LocalDate date, Probation probation);

    /**
     * Удаление отчета из базы
     * @param report удаляемый отчет.
     */
    void deleteReport(Report report);

    /**
     * Получить данные о просроченных отчётов, где отчёт считается просроченным если дата отчёта меньше переданной даты
     *
     * @param to дата до который отчёт считается просроченным
     * @return список информации о просроченных отчётах пользователя по приюту
     */
    List<OverdueDayData> getOverdueDayData(LocalDate to);

}

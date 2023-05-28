package com.skypro.skyprotelegrambot.service;

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
     * @param user пользователь отправивший отчет
     * @param text текст отчета
     * @param photoPath путь к фотографии
     * @return созданный отчет
     */
    Report createReport(User user, String text, String photoPath);

    /**
     * Получение отчета по идентификатору
     * @param id идентификатор отчета
     * @return очет с указанным идентификатором
     */
    Report getById(Long id);

    /**
     * Формирование списка отчетов пользователя по заданному приюту
     * @param user пользователь для формирования списка
     * @param shelter приют для формирования списка
     * @return Список отчетов пользователя по заданному приюту
     */
    List<Report> getAllByUserAndShelter(User user, Shelter shelter);

    /**
     * Формирут список отчетов по дате и приюту
     * @param date дата для формирования списка
     * @param shelter приют для формирования списка
     * @return список отчетов для заданного приют на заданную дату
     */
    List<Report> getAllByDateAndShelter(LocalDate date, Shelter shelter);

    /**
     * Обновление фото для данного отчета. Потенциальный функционал...
     * @param photoPath новый путь к фотографии
     * @param id идентификатор отчета
     */
    void updatePhoto(String photoPath, Long id);

    /**
     * Обновление текста для данного отчета. Потенциальный функционал...
     * @param text новый текст
     * @param id идентификатор отчета
     */
    void updateText(String text, Long id);

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

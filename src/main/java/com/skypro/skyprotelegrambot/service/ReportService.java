package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.entity.Report;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.model.OverdueDayData;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    Report createReport(User user, String text, byte[] photo);

    Report getById(Long id);

    List<Report> getAllByUserAndShelter(User user, Shelter shelter);

    void updatePhoto(byte[] photo, Long id);

    void updateText(String text, Long id);

    void deleteReport(Report report);

    /**
     * Получить данные о просроченных отчётов, где отчёт считается просроченным если дата отчёта меньше переданной даты
     *
     * @param to дата до который отчёт считается просроченным
     * @return список информации о просроченных отчётах пользователя по приюту
     */
    List<OverdueDayData> getOverdueDayData(LocalDate to);
}

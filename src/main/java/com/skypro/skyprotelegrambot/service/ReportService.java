package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.entity.Report;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;

import java.util.List;

public interface ReportService {

    Report createReport(User user, String text, String photoPath);

    Report getById(Long id);

    List<Report> getAllByUserAndShelter(User user, Shelter shelter);

    void updatePhoto(String photoPath, Long id);

    void updateText(String text, Long id);

    void deleteReport(Report report);
}

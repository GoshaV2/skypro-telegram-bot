package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.entity.Report;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.exception.NotFoundElement;
import com.skypro.skyprotelegrambot.model.OverdueDayData;
import com.skypro.skyprotelegrambot.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Report createReport(User user, String text, byte[] photo) {
        return reportRepository.save(new Report(text, photo,
                user, user.getSession().getSelectedShelter()));
    }

    @Override
    public Report getById(Long id) {
        return reportRepository.findById(id).orElseThrow(
                () -> new NotFoundElement(id, Report.class)
        );
    }

    @Override
    public List<Report> getAllByUserAndShelter(User user, Shelter shelter) {
        return reportRepository.findAllByUserAndShelter(user, shelter);
    }

    @Override
    public void updatePhoto(byte[] photo, Long id) {
        Report edited = getById(id);
        edited.setPhoto(photo);
        reportRepository.save(edited);
    }

    @Override
    public void updateText(String text, Long id) {
        Report edited = getById(id);
        edited.setReport(text);
        reportRepository.save(edited);
    }

    @Override
    public void deleteReport(Report report) {
        reportRepository.delete(report);
    }

    @Override
    public List<OverdueDayData> getOverdueDayData(LocalDate to) {
        return reportRepository.getDataOfOverdueDays(to);
    }
}

package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.entity.Report;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.exception.NotFoundElement;
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
    public List<Report> getAll(){
        return  reportRepository.findAll();
    }

    @Override
    public Report createReport(User user, String text, String photoPath) {
        return reportRepository.save(new Report(text, photoPath,
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
    public List<Report> getAllByDateAndShelter(LocalDate date, Shelter shelter) {
        return reportRepository.findAllByDateAndShelter(date, shelter);
    }

    @Override
    public void updatePhoto(String photoPath, Long id) {
        //надо подумать как менять фото в новой реализации
        Report edited = getById(id);
        edited.setPhotoPath(photoPath);
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
}

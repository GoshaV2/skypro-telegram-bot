package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.entity.Probation;
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
    public List<Report> getAll(){
        return  reportRepository.findAll();
    }

    @Override
    public Report createReport(Probation probation, String text, String photoPath) {
        return reportRepository.save(new Report(text, photoPath,
                probation));
    }

    @Override
    public Report getById(Long id) {
        return reportRepository.findById(id).orElseThrow(
                () -> new NotFoundElement(id, Report.class)
        );
    }

    @Override
    public List<Report> getAllByProbation(Probation probation) {
        return reportRepository.findAllByProbation(probation);
    }

    @Override
    public List<Report> getAllByDateAndProbation(LocalDate date, Probation probation) {
        return reportRepository.findAllByDateAndProbation(date, probation);
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

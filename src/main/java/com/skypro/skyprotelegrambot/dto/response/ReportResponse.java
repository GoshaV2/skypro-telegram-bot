package com.skypro.skyprotelegrambot.dto.response;

import com.skypro.skyprotelegrambot.entity.Report;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ReportResponse {
    private Long id;
    private String report;
    private LocalDate date;
    private ProbationResponse probation;


    public ReportResponse(Long id, String report, LocalDate date, ProbationResponse probation) {
        this.id = id;
        this.report = report;
        this.date = date;
        this.probation = probation;
    }

    public static ReportResponse from(Report report) {
        return new ReportResponse(report.getId(), report.getReport(), report.getDate(),ProbationResponse.from(report.getProbation()));
    }

    public static List<ReportResponse> from(Collection<Report> reports) {
        return reports.stream().map(ReportResponse::from).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ProbationResponse getProbation() {
        return probation;
    }

    public void setProbation(ProbationResponse probation) {
        this.probation = probation;
    }
}

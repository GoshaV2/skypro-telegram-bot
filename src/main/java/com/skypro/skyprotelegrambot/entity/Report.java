package com.skypro.skyprotelegrambot.entity;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "report",nullable = false)
    private String report;

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name = "dates")
    private final LocalDate date;

    @ManyToOne
    @JoinColumn(name = "probation_id", nullable = false)
    private Probation probation;


    public Report() {
        date = LocalDate.now();
    }

    public Report(String report, String photoPath, Probation probation) {
        date = LocalDate.now();
        this.report = report;
        this.photoPath = photoPath;
        this.probation = probation;
    }

    public Long getId() {
        return id;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public LocalDate getDate() {
        return date;
    }

    public Probation getProbation() {
        return probation;
    }

    public void setProbation(Probation probation) {
        this.probation = probation;
    }
}

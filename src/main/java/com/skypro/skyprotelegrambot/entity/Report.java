package com.skypro.skyprotelegrambot.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "report")
    private String report;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "date")
    private final LocalDate date;

    @ManyToOne
    @Column(name = "user_id")
    private User user;

    @ManyToOne
    @Column(name = "shelter_id")
    private Shelter shelter;

    public Report() {
        date = LocalDate.now();
    }
    public Report(String report, byte[] photo, User user, Shelter shelter) {
        date = LocalDate.now();
        setReport(report);
        setPhoto(photo);
        setUser(user);
        setShelter(shelter);
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public LocalDate getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }
}

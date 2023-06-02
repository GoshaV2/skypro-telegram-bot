package com.skypro.skyprotelegrambot.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Probation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "pet_name")
    private String petName;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "probation_status")
    @Enumerated(EnumType.STRING)
    private ProbationStatus probationStatus;
    @ManyToOne
    @JoinColumn(name = "volunteer_contact_id")
    private VolunteerContact volunteerContact;
    /**
     * количество дней испытательного срока
     */
    @Column (name = "count_probation_days")
    private int countProbationDays;

    public Long getId() {
        return id;
    }

    public ProbationStatus getProbationStatus() {
        return probationStatus;
    }

    public void setProbationStatus(ProbationStatus probationStatus) {
        this.probationStatus = probationStatus;
    }

    public VolunteerContact getVolunteerContact() {
        return volunteerContact;
    }

    public void setVolunteerContact(VolunteerContact volunteerContact) {
        this.volunteerContact = volunteerContact;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getCountProbationDays() {
        return countProbationDays;
    }

    public void setCountProbationDays(int countProbationDays) {
        this.countProbationDays = countProbationDays;
    }
}

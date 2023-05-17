package com.skypro.skyprotelegrambot.entity;

import javax.persistence.*;

@Entity
@Table (name = "session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "selected_shelter_id")
    private Shelter selectedShelter;

    @Column(name = "is_report_sending")
    boolean isReportSending;

    public Session(){
        isReportSending = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shelter getSelectedShelter() {
        return selectedShelter;
    }

    public void setSelectedShelter(Shelter selectedShelter) {
        this.selectedShelter = selectedShelter;
    }

    public boolean isReportSending() {
        return isReportSending;
    }

    public void setReportSending(boolean reportSending) {
        isReportSending = reportSending;
    }
}

package com.skypro.skyprotelegrambot.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "selected_shelter_id")
    private Shelter selectedShelter;
    @ColumnDefault("false")
    @Column(name = "has_waiting_contact", nullable = false)
    private boolean hasWaitingContact;
    @ColumnDefault("false")
    @Column(name = "is_report_sending", nullable = false)
    boolean isReportSending;
    @ColumnDefault("true")
    @Column(name = "is_first_request", nullable = false)
    boolean isFirstRequest;

    public Session() {
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

    public boolean hasWaitingContact() {
        return hasWaitingContact;
    }

    public void setHasWaitingContact(boolean hasWaitingContact) {
        this.hasWaitingContact = hasWaitingContact;
    }

    public boolean isReportSending() {
        return isReportSending;
    }

    public void setReportSending(boolean reportSending) {
        isReportSending = reportSending;
    }

    public boolean isFirstRequest() {
        return isFirstRequest;
    }

    public void setFirstRequest(boolean firstRequest) {
        isFirstRequest = firstRequest;
    }
}

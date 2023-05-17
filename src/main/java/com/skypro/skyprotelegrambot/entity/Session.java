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
    @Column(name = "is_waiting_contact",nullable = false,columnDefinition = "false")
    private boolean isWaitingContact;

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

    public boolean isWaitingContact() {
        return isWaitingContact;
    }

    public void setWaitingContact(boolean waitingContact) {
        isWaitingContact = waitingContact;
    }
}

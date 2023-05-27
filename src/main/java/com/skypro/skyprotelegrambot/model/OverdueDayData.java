package com.skypro.skyprotelegrambot.model;

import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;

import java.time.LocalDate;

public class OverdueDayData {
    private LocalDate lastLoadDate;
    private User user;
    private Shelter shelter;

    public OverdueDayData(LocalDate lastLoadDate, User user, Shelter shelter) {
        this.lastLoadDate = lastLoadDate;
        this.user = user;
        this.shelter = shelter;
    }

    public LocalDate getLastLoadDate() {
        return lastLoadDate;
    }

    public void setLastLoadDate(LocalDate lastLoadDate) {
        this.lastLoadDate = lastLoadDate;
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

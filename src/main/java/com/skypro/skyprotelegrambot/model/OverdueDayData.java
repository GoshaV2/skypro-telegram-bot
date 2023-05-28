package com.skypro.skyprotelegrambot.model;

import com.skypro.skyprotelegrambot.entity.Probation;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;

import java.time.LocalDate;

public class OverdueDayData {
    private LocalDate lastLoadDate;
    private Probation probation;

    public OverdueDayData(LocalDate lastLoadDate, Probation probation) {
        this.lastLoadDate = lastLoadDate;
        this.probation = probation;
    }

    public LocalDate getLastLoadDate() {
        return lastLoadDate;
    }

    public void setLastLoadDate(LocalDate lastLoadDate) {
        this.lastLoadDate = lastLoadDate;
    }

    public Probation getProbation() {
        return probation;
    }

    public void setProbation(Probation probation) {
        this.probation = probation;
    }
}

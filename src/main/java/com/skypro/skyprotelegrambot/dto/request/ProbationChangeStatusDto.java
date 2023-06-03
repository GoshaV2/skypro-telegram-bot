package com.skypro.skyprotelegrambot.dto.request;

import com.skypro.skyprotelegrambot.entity.ProbationStatus;

public class ProbationChangeStatusDto {
    private ProbationStatus probationStatus;

    public ProbationStatus getProbationStatus() {
        return probationStatus;
    }

    public void setProbationStatus(ProbationStatus probationStatus) {
        this.probationStatus = probationStatus;
    }
}

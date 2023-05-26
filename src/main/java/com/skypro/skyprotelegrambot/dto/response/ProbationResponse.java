package com.skypro.skyprotelegrambot.dto.response;

import com.skypro.skyprotelegrambot.entity.Probation;
import com.skypro.skyprotelegrambot.entity.ProbationStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class ProbationResponse {
    private Long id;
    private long shelterId;
    private long userId;
    private String petName;
    private LocalDate startDate;
    private int countProbationDays;
    private ProbationStatus probationStatus;
    private long volunteerContactId;

    public ProbationResponse(Long id, long shelterId, long userId, String petName, LocalDate startDate, int countProbationDays, ProbationStatus probationStatus, long volunteerContactId) {
        this.id = id;
        this.shelterId = shelterId;
        this.userId = userId;
        this.petName = petName;
        this.startDate = startDate;
        this.countProbationDays = countProbationDays;
        this.probationStatus = probationStatus;
        this.volunteerContactId = volunteerContactId;
    }

    public ProbationResponse() {
    }

    public static ProbationResponse from(Probation probation) {
        return ProbationResponse.builder()
                .id(probation.getId())
                .probationStatus(probation.getProbationStatus())
                .startDate(probation.getStartDate())
                .petName(probation.getPetName())
                .shelterId(probation.getShelter().getId())
                .userId(probation.getUser().getId())
                .countProbationDays(probation.getCountProbationDays())
                .volunteerContactId(probation.getVolunteerContact().getId())
                .build();
    }

    public long getVolunteerContactId() {
        return volunteerContactId;
    }

    public void setVolunteerContactId(long volunteerContactId) {
        this.volunteerContactId = volunteerContactId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getShelterId() {
        return shelterId;
    }

    public void setShelterId(long shelterId) {
        this.shelterId = shelterId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public ProbationStatus getProbationStatus() {
        return probationStatus;
    }

    public void setProbationStatus(ProbationStatus probationStatus) {
        this.probationStatus = probationStatus;
    }
}

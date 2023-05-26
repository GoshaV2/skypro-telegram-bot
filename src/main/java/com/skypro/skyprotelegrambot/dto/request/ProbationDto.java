package com.skypro.skyprotelegrambot.dto.request;

public class ProbationDto {
    private long shelterId;
    private long userId;
    private String petName;
    private int countProbationDays;
    private long volunteerContactId;

    public long getShelterId() {
        return shelterId;
    }

    public void setShelterId(long shelterId) {
        this.shelterId = shelterId;
    }

    public long getVolunteerContactId() {
        return volunteerContactId;
    }

    public void setVolunteerContactId(long volunteerContactId) {
        this.volunteerContactId = volunteerContactId;
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

    public int getCountProbationDays() {
        return countProbationDays;
    }

    public void setCountProbationDays(int countProbationDays) {
        this.countProbationDays = countProbationDays;
    }
}

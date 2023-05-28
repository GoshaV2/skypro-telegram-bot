package com.skypro.skyprotelegrambot.dto.request;


public class VolunteerContactDto {
    private String phone;
    private String telegramTag;
    private String email;
    private String fullName;
    private Long shelterId;
    private long chatId;

    public VolunteerContactDto(String phone, String telegramTag, String email, String fullName, Long shelterId, long chatId) {
        this.phone = phone;
        this.telegramTag = telegramTag;
        this.email = email;
        this.fullName = fullName;
        this.shelterId = shelterId;
        this.chatId = chatId;
    }

    public VolunteerContactDto() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTelegramTag() {
        return telegramTag;
    }

    public void setTelegramTag(String telegramTag) {
        this.telegramTag = telegramTag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getShelterId() {
        return shelterId;
    }

    public void setShelterId(Long shelterId) {
        this.shelterId = shelterId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
}
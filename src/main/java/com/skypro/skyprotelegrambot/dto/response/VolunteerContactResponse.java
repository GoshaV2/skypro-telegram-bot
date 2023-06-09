package com.skypro.skyprotelegrambot.dto.response;

import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.VolunteerContact;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class VolunteerContactResponse {
    private Long id;
    private String phone;
    private Long chatId;
    private String telegramTag;
    private String email;
    private String fullName;
    private Shelter shelter;

    public VolunteerContactResponse(Long id, String phone, Long chatId, String telegramTag, String email, String fullName, Shelter shelter) {
        this.id = id;
        this.phone = phone;
        this.chatId = chatId;
        this.telegramTag = telegramTag;
        this.email = email;
        this.fullName = fullName;
        this.shelter = shelter;
    }

    public static VolunteerContactResponse from(VolunteerContact volunteerContact) {
        return VolunteerContactResponse.builder()
                .id(volunteerContact.getId())
                .phone(volunteerContact.getPhone())
                .chatId(volunteerContact.getChatId())
                .telegramTag(volunteerContact.getTelegramTag())
                .email(volunteerContact.getEmail())
                .fullName(volunteerContact.getFullName())
                .shelter(volunteerContact.getShelter()).build();
    }

    public static List<VolunteerContactResponse> from(List<VolunteerContact> volunteerContactsList) {
        return volunteerContactsList.stream().map(VolunteerContactResponse::from)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
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

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }
}

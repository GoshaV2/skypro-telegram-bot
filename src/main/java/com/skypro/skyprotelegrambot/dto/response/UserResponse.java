package com.skypro.skyprotelegrambot.dto.response;

import com.skypro.skyprotelegrambot.entity.Session;
import com.skypro.skyprotelegrambot.entity.User;
import lombok.Builder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public class UserResponse {
    private Long id;
    private Long chatId;
    private String name;
    private String contact;

    public UserResponse(Long id, Long chatId, String name, String contact) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.contact = contact;
    }

    public UserResponse() {
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .chatId(user.getChatId())
                .id(user.getId())
                .name(user.getName())
                .contact(user.getContact())
                .build();
    }

    public static List<UserResponse> from(Collection<User> users) {
        return users.stream().map(UserResponse::from).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}

package com.skypro.skyprotelegrambot.dto.request;

import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.Shelter;

public class AnswerDto {
    private String title;
    private String text;
    private String command;
    private Category category;
    private Long shelterId;

    public AnswerDto(String title, String text, String command, Category category, Long shelterId) {
        this.title = title;
        this.text = text;
        this.command = command;
        this.category = category;
        this.shelterId = shelterId;
    }

    public AnswerDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getShelterId() {
        return shelterId;
    }

    public void setShelterId(Long shelterId) {
        this.shelterId = shelterId;
    }

}

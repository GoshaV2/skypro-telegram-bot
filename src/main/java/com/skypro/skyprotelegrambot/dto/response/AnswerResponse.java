package com.skypro.skyprotelegrambot.dto.response;

import com.skypro.skyprotelegrambot.entity.Answer;
import com.skypro.skyprotelegrambot.entity.Category;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class AnswerResponse {
    private Long id;
    private String title;
    private String text;
    private Category category;
    private ShelterResponse shelterResponse;

    public AnswerResponse(Long id, String title, String text, Category category, ShelterResponse shelterResponse) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.category = category;
        this.shelterResponse = shelterResponse;
    }

    public static AnswerResponse from(Answer answer) {
        return AnswerResponse.builder()
                .id(answer.getId())
                .title(answer.getTitle())
                .text(answer.getText())
                .category(answer.getCategory())
                .shelterResponse(ShelterResponse.from(answer.getShelter())).build();
    }

    public static List<AnswerResponse> from(List<Answer> answerList) {
        return answerList.stream().map(AnswerResponse::from).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ShelterResponse getShelterResponse() {
        return shelterResponse;
    }

    public void setShelterResponse(ShelterResponse shelterResponse) {
        this.shelterResponse = shelterResponse;
    }
}

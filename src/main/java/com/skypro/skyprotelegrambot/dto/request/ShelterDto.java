package com.skypro.skyprotelegrambot.dto.request;


public class ShelterDto {
    private String name;

    public ShelterDto(String name) {
        this.name = name;
    }

    public ShelterDto() {
    }

    public ShelterDto(Long id, String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

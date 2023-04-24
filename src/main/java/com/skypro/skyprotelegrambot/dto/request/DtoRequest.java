package com.skypro.skyprotelegrambot.dto.request;

import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.Shelter;

public class DtoRequest {
    private String title;
    private String text;
    private String command;
    private Category category;
    private Shelter shelter;
    private Shelter selectedShelter;
    private String name;
    private int age;

    public DtoRequest(String title,
                      String text,
                      String command,
                      Category category,
                      Shelter shelter,
                      Shelter selectedShelter,
                      String name,
                      int age) {
        this.title = title;
        this.text = text;
        this.command = command;
        this.category = category;
        this.shelter = shelter;
        this.selectedShelter = selectedShelter;
        this.name = name;
        this.age = age;
    }

    public DtoRequest() {
    }
}

package com.skypro.skyprotelegrambot.dto.request;


public class ShelterDto {
    private String name;
    private int age;

    public ShelterDto(String name, int age) {
        this.name = name;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

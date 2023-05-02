package com.skypro.skyprotelegrambot.dto.request;


public class ShelterDto {

    private Long id;
    private String name;
    private int age;

    public ShelterDto(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public ShelterDto() {
    }

    public ShelterDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

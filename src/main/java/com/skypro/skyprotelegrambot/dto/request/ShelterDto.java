package com.skypro.skyprotelegrambot.dto.request;

import com.skypro.skyprotelegrambot.entity.User;

import java.util.List;

public class ShelterDto {

    private Long id;
    private String name;
    private int age;
    List<User> userList;

    public ShelterDto(Long id, String name, int age, List<User> userList) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.userList = userList;
    }

    public ShelterDto() {
    }

    public ShelterDto(Long id, String name, List<User> userList) {
        this.id = id;
        this.name = name;
        this.userList = userList;
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

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}

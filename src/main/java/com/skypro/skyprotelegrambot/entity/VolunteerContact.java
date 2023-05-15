package com.skypro.skyprotelegrambot.entity;

import javax.persistence.*;

@Entity
@Table(name = "volunteer_contact")
public class VolunteerContact {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "phone")
    private String phone;
    @Column(name = "telegram_tag")
    private String telegramTag;
    @Column(name = "email")
    private String email;
    @Column(name = "full_name",nullable = false)
    private String fullName;
    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    public VolunteerContact() {
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
}

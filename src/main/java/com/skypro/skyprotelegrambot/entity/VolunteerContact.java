package com.skypro.skyprotelegrambot.entity;

import javax.persistence.*;

@Entity
@Table(name = "volunteer_contact")
public class VolunteerContact {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    /**
     * Телефон пользователя
     */
    @Column(name = "phone")
    private String phone;
    /**
     * Индентификатор пользователя в телеграмм
     */
    @Column(name = "chat_id")
    private long chatId;
    /**
     * Телеграмм тег пользователя
     */
    @Column(name = "telegram_tag")
    private String telegramTag;
    /**
     * Почта пользователя
     */
    @Column(name = "email")
    private String email;
    /**
     * Полное имя
     */
    @Column(name = "full_name", nullable = false)
    private String fullName;
    /**
     * Приют к которому относится волонтер
     */
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

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
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

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }
}

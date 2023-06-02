package com.skypro.skyprotelegrambot.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    /**
     * Индентификатор пользователя в телеграмм
     */
    @Column(name = "chat_id", unique = true, nullable = false)
    private Long chatId;
    /**
     * Полное имя пользователя
     */
    @Column(name = "names")
    private String name;
    /**
     * Контакты пользователя
     */
    @Column(name = "contact")
    private String contact;
    /**
     * Сессия пользователя
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public Long getChatId() {
        // оно нужно?
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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        return chatId.equals(user.chatId);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + chatId.hashCode();
        return result;
    }
}

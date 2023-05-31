package com.skypro.skyprotelegrambot.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shelter")
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Название приюта
     */
    @Column(name = "name")
    private String name;
    @ManyToMany
    @JoinTable(name = "shelter_user",
            joinColumns = @JoinColumn(name = "shelter_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<User> userSet = new HashSet<>();

    public Shelter() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }
}

package com.skypro.skyprotelegrambot.entity;

import javax.persistence.*;

@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "text", nullable = false)
    private String text;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "shelter_id", nullable = false)
    private Shelter shelter;

    public Answer(String text, Category category, Shelter shelter) {
        this.text = text;
        this.category = category;
        this.shelter = shelter;
    }

    public Answer() {

    }

    public Long getId() {
        return id;
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

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }
}

package com.skypro.skyprotelegrambot.entity;

public enum Category {
    INFORMATION("shelter.info"),
    GETTING_ANIMAL("shelter.howGetAnimal");
    /**
     * Ключ для получения названия категории
     */
    private final String propertyMessageKey;

    Category(String propertyMessageKey) {
        this.propertyMessageKey = propertyMessageKey;
    }

    public String getPropertyMessageKey() {
        return propertyMessageKey;
    }
}

package com.skypro.skyprotelegrambot.exception;

public class NotFoundElement extends RuntimeException {
    public NotFoundElement(long id, Class<?> clazz) {
        super(String.format("%s not found by id=%d", clazz.getName(), id));
    }

    public NotFoundElement(String UUID, Class<?> clazz) {
        super(String.format("%s not found by UUID=%s", clazz.getName(), UUID));
    }
}

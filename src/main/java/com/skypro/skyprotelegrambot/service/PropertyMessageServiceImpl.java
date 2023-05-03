package com.skypro.skyprotelegrambot.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class PropertyMessageServiceImpl implements PropertyMessageService {
    private final MessageSource messageSource;

    public PropertyMessageServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }
}

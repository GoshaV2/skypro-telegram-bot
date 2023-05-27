package com.skypro.skyprotelegrambot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TelegramBotTestConfiguration {

    @Bean
    @Primary
    public TelegramBot telegramBotTest() {
        return Mockito.mock(TelegramBot.class);
    }
}

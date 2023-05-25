package com.skypro.skyprotelegrambot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TelegramBotTestConfiguration {

    @Bean
    @Primary
    public TelegramBot telegramBotTest() {
        return Mockito.mock(TelegramBot.class);
    }
}

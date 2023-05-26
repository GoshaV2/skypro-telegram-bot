package com.skypro.skyprotelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SkyproTelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyproTelegramBotApplication.class, args);
    }

}

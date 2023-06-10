package com.skypro.skyprotelegrambot.schedule;

import com.skypro.skyprotelegrambot.service.ProbationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ProbationSchedule {
    private final ProbationService probationService;

    public ProbationSchedule(ProbationService probationService) {
        this.probationService = probationService;
    }

    @Scheduled(cron = "0 10 * * * *")
    public void checkReportSending() {
        probationService.sendNotificationAboutReport();
    }
}

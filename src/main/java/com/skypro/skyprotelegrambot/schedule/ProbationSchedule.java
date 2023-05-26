package com.skypro.skyprotelegrambot.schedule;

import com.skypro.skyprotelegrambot.service.ProbationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProbationSchedule {
    private final ProbationService probationService;

    public ProbationSchedule(ProbationService probationService) {
        this.probationService = probationService;
    }

    @Scheduled(fixedRate = 10000)
    public void checkReportSending() {
        probationService.sendNotificationAboutReport();
    }
}

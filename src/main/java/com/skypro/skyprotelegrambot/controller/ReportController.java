package com.skypro.skyprotelegrambot.controller;

import com.skypro.skyprotelegrambot.entity.Report;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.service.ReportService;
import com.skypro.skyprotelegrambot.service.ShelterService;
import com.skypro.skyprotelegrambot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;

import org.apache.catalina.webresources.FileResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;
    private final ShelterService shelterService;


    public ReportController(ReportService reportService, UserService userService, ShelterService shelterService) {
        this.reportService = reportService;
        this.userService = userService;
        this.shelterService = shelterService;
    }

    @GetMapping()
    @Operation(summary = "список всех отчетов")
    public List<Report> getAll() {
        return reportService.getAll();
    }

    @GetMapping("/{userId}{shelterId}")
    @Operation(summary = "список всех отчетов пользователя для указанного приюта")
    public List<Report> getReportsFromUserToShelter(@PathVariable(name = "userId") Long userId,
                                                    @PathVariable(name = "shelterId") Long shelterId) {
        User user = userService.findUserByChatId(userId);
        Shelter shelter = shelterService.findShelterById(shelterId);
        return reportService.getAllByUserAndShelter(user, shelter);
    }

    @GetMapping("/today{shelterId}")
    @Operation(summary = "список всех сегодняшних отчетов для приюта")
    public List<Report> getAllTodayReportToShelter(@PathVariable(name = "shelterId") Long shelterId) {
        return reportService.getAllByDateAndShelter(LocalDate.now(), shelterService.findShelterById(shelterId));
    }

}

package com.skypro.skyprotelegrambot.controller;

import com.skypro.skyprotelegrambot.entity.Probation;
import com.skypro.skyprotelegrambot.entity.Report;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.exception.NotFoundElement;
import com.skypro.skyprotelegrambot.service.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;
    private final ShelterService shelterService;
    private final ProbationService probationService;
    private final FileService fileService;


    public ReportController(ReportService reportService, UserService userService, ShelterService shelterService,
                            ProbationService probationService, FileService fileService) {
        this.reportService = reportService;
        this.userService = userService;
        this.shelterService = shelterService;
        this.probationService = probationService;
        this.fileService = fileService;
    }

    @GetMapping()
    @Operation(summary = "список всех отчетов")
    public List<Report> getAll() {
        return reportService.getAll();
    }

    @GetMapping("/{userId}{shelterId}")
    @Operation(summary = "список всех отчетов пользователя для указанного приюта")
    public ResponseEntity<List<Report>> getReportsFromUserToShelter(@PathVariable(name = "userId") Long userId,
                                                      @PathVariable(name = "shelterId") Long shelterId) {
        User user = userService.findUserByChatId(userId);
        Shelter shelter = shelterService.findShelterById(shelterId);
        Probation probation;
        try {
            probation = probationService.getProbation(user, shelter);
        } catch (NotFoundElement e) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        List<Report> reports = reportService.getAllByProbation(probation);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/today{shelterId}")
    @Operation(summary = "список всех сегодняшних отчетов для приюта")
    public ResponseEntity<List<Report>> getAllTodayReportToShelter(@PathVariable(name = "shelterId") Long shelterId) {
        List<Probation> probations = probationService.gatAllByShelter(shelterService.findShelterById(shelterId));
        if (probations.size() == 0) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        List<Report> reports = new ArrayList<>();
        for (Probation pb : probations) {
            reports.addAll(reportService.getAllByDateAndProbation(LocalDate.now(), pb));
        }
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/photo{reportId}")
    @Operation(summary = "Получение фото")
    public byte[] getPhoto(@PathVariable(name = "reportId") Long reportId) {
        Path filepath = Path.of(reportService.getById(reportId).getPhotoPath());
        byte[] result = null;
        try {
            result = fileService.readFile(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

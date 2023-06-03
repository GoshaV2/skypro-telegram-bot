package com.skypro.skyprotelegrambot.controller;

import com.skypro.skyprotelegrambot.entity.Probation;
import com.skypro.skyprotelegrambot.entity.Report;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.exception.NotFoundElement;
import com.skypro.skyprotelegrambot.service.*;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Value;
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
    @Value("${report.server.address}")
    private String serverAddress;
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
    public String getReportsFromUserToShelter(@PathVariable(name = "userId") Long userId,
                                              @PathVariable(name = "shelterId") Long shelterId) {
        User user = userService.findUserByChatId(userId);
        Shelter shelter = shelterService.findShelterById(shelterId);
        Probation probation;
        try {
            probation = probationService.getProbation(user, shelter);
        } catch (NotFoundElement e) {
            return "Пользователю еще не назначени испытательный срок в приюте";
        }
        List<Report> reports = reportService.getAllByProbation(probation);
        return reportsToText(reports);
    }

    @GetMapping("/today{shelterId}")
    @Operation(summary = "список всех сегодняшних отчетов для приюта")
    public String getAllTodayReportToShelter(@PathVariable(name = "shelterId") Long shelterId) {
        List<Probation> probations = probationService.gatAllByShelter(shelterService.findShelterById(shelterId));


        if (probations.size() == 0) {
            return "Отчетов не найдено";
        }
        List<Report> reports = new ArrayList<>();
        for (Probation pb : probations) {
            reports.addAll(reportService.getAllByDateAndProbation(LocalDate.now(), pb));
        }
        return reportsToText(reports);
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

    private String reportToText(Report report) {
        Long id = report.getId();
        String userName = report.getProbation().getUser().getName();
        long userId = report.getProbation().getUser().getId();
        String shelterName = report.getProbation().getShelter().getName();
        LocalDate date = report.getDate();
        String text = report.getReport();
        String link = String.format("%s/reports/photo%d", serverAddress, report.getId());

        return String.format(
                "ID: %1$d\n From: %2$s id:%7$d\n To: %3$s\n Date: %4$te %4$tB %4$tY\nText: %5$s\n Photo link: %6$s",
                id, userName, shelterName, date, text, link, userId);
    }

    private String reportsToText(List<Report> reports) {
        StringBuilder sb = new StringBuilder();
        for (Report r : reports) {
            sb.append(reportToText(r));
            sb.append("\n\n");
        }
        return sb.toString().trim();
    }
}

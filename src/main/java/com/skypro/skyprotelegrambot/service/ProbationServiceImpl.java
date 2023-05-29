package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.dto.request.ProbationDto;
import com.skypro.skyprotelegrambot.dto.response.ProbationResponse;
import com.skypro.skyprotelegrambot.entity.Probation;
import com.skypro.skyprotelegrambot.entity.ProbationStatus;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.exception.NotFoundElement;
import com.skypro.skyprotelegrambot.model.OverdueDayData;
import com.skypro.skyprotelegrambot.repository.ProbationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ProbationServiceImpl implements ProbationService {
    private final ShelterService shelterService;
    private final UserService userService;
    private final ProbationRepository probationRepository;
    private final ReportService reportService;
    private final NotificationService notificationService;
    private final VolunteerContactService volunteerContactService;
    @Value("${report.count.days.toCallVolunteer}")
    private long daysToCallVolunteer;

    public ProbationServiceImpl(ShelterService shelterService, UserService userService, ProbationRepository probationRepository, ReportService reportService, NotificationService notificationService, VolunteerContactService volunteerContactService) {
        this.shelterService = shelterService;
        this.userService = userService;
        this.probationRepository = probationRepository;
        this.reportService = reportService;
        this.notificationService = notificationService;
        this.volunteerContactService = volunteerContactService;
    }


    @Override
    public ProbationResponse createProbation(ProbationDto probationDto) {
        Shelter shelter = shelterService.findShelterById(probationDto.getShelterId());
        User user = userService.getUserById(probationDto.getUserId());
        Probation probation = new Probation();
        probation.setCountProbationDays(probationDto.getCountProbationDays());
        probation.setUser(user);
        probation.setShelter(shelter);
        probation.setPetName(probationDto.getPetName());
        probation.setStartDate(LocalDate.now().plusDays(1));
        probation.setProbationStatus(ProbationStatus.APPOINTED);
        probation.setVolunteerContact(volunteerContactService.findVolunteerContactById(probationDto.getVolunteerContactId()));
        probationRepository.save(probation);
        return ProbationResponse.from(probation);
    }

    @Override
    public void sendNotificationAboutReport() {
        LocalDate overdueDate = LocalDate.now().minusDays(1);
        List<OverdueDayData> overdueDayDataList = reportService.getOverdueDayData(overdueDate);
        overdueDayDataList.forEach(overdueDayData -> {
            long overdueDays = ChronoUnit.DAYS.between(overdueDayData.getLastLoadDate(), overdueDate);
            Probation probation = overdueDayData.getProbation();
            User user = probation.getUser();
            Shelter shelter = probation.getShelter();
            if (overdueDays % daysToCallVolunteer == 0) {
                notificationService.sendNotificationAboutOverdueReportToVolunteer(
                        probation.getVolunteerContact().getChatId(),
                        user.getName(),
                        user.getChatId()
                );
            } else {
                notificationService.sendNotificationAboutOverdueReportToUser(
                        user.getChatId(),
                        shelter.getName());
            }
        });
    }

    @Override
    public Probation getProbation(User user, Shelter shelter) {
        return probationRepository.findByUserAndShelter(user, shelter).orElseThrow(() ->
                new NotFoundElement(String.format("Not Probation by user(id=%d) and shelter(id=%d)",
                        user.getId(), shelter.getId())));
    }

    @Override
    public List<Probation> gatAllByShelter(Shelter shelter) {
        return probationRepository.findAllByShelter(shelter);

    }
}

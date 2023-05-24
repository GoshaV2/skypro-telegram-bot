package com.skypro.skyprotelegrambot.service.message;

import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.VolunteerContact;
import com.skypro.skyprotelegrambot.service.VolunteerContactService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerMessageServiceImpl implements VolunteerMessageService {
    private final VolunteerContactService volunteerContactService;

    public VolunteerMessageServiceImpl(VolunteerContactService volunteerContactService) {
        this.volunteerContactService = volunteerContactService;
    }

    @Override
    public SendMessage getMessageAfterSentVolunteerContact(long id, Shelter shelter) {
        StringBuilder stringBuilder = new StringBuilder();
        List<VolunteerContact> contactList = volunteerContactService.findAllByShelter(shelter);
        for (VolunteerContact volunteerContact : contactList) {
            stringBuilder.append("Номер телефона волонтера - ").append(volunteerContact.getPhone());
            stringBuilder.append("\nТелеграм-тэг волонтера - ").append(volunteerContact.getTelegramTag());
            stringBuilder.append("\nЭл. почта волонтера - ").append(volunteerContact.getEmail());
            stringBuilder.append("\nИмя волонтера - ").append(volunteerContact.getFullName());
            stringBuilder.append("\nПриют - ").append(volunteerContact.getShelter());
        }
        return new SendMessage(id, stringBuilder.toString());
    }
}
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
        StringBuilder contactBuilder = new StringBuilder();
        List<VolunteerContact> contactList = volunteerContactService.findAllByShelter(shelter);
        for (VolunteerContact volunteerContact : contactList) {
            contactBuilder.append("Номер телефона волонтера - ").append(volunteerContact.getPhone());
            contactBuilder.append("\nТелеграм-тэг волонтера - ").append(volunteerContact.getTelegramTag());
            contactBuilder.append("\nЭл. почта волонтера - ").append(volunteerContact.getEmail());
            contactBuilder.append("\nИмя волонтера - ").append(volunteerContact.getFullName());
            contactBuilder.append("\nПриют - ").append(volunteerContact.getShelter());
        }
        String contact = contactBuilder.toString();
        if (contact.isEmpty()) {
            contact = "Информация временно не загруженна";
        }
        return new SendMessage(id, contact);
    }
}
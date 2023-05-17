package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.dto.request.VolunteerContactDto;
import com.skypro.skyprotelegrambot.entity.VolunteerContact;

public interface VolunteerContactService {
    VolunteerContact findVolunteerContactById(Long id);

    VolunteerContact createVolunteerContact(VolunteerContactDto volunteerContactDto);

    VolunteerContact updateVolunteerContact(VolunteerContactDto volunteerContactDto, Long id);

    void deleteVolunteerContactById(Long id);
}

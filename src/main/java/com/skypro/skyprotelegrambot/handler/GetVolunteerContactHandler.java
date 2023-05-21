package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.skypro.skyprotelegrambot.entity.Session;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.entity.VolunteerContact;
import com.skypro.skyprotelegrambot.model.command.VolunteerCommand;
import com.skypro.skyprotelegrambot.repository.UserRepository;
import com.skypro.skyprotelegrambot.service.VolunteerContactService;

import java.util.List;

public class GetVolunteerContactHandler implements CommandHandler {
    private final VolunteerContactService volunteerContactService;
    private final UserRepository userRepository;

    public GetVolunteerContactHandler(VolunteerContactService volunteerContactService, UserRepository userRepository) {
        this.volunteerContactService = volunteerContactService;
        this.userRepository = userRepository;
    }

    @Override
    public boolean apply(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery == null) {
            return false;
        }
        return VolunteerCommand.SEND_VOLUNTEER_CONTACT.getCommand().equals(callbackQuery.data());
    }

    @Override
    public void process(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(stringBuilder);
        long id = callbackQuery.from().id();
        VolunteerContact volunteerContact = volunteerContactService.findVolunteerContactById(id);
        Session session = new Session();
        session.getSelectedShelter();

    }

    public List<User> getAll(Shelter shelter) {
        return userRepository.getAll(shelter);
    }
}

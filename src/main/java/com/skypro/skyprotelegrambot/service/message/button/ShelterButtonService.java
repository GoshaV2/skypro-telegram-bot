package com.skypro.skyprotelegrambot.service.message.button;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.skypro.skyprotelegrambot.entity.Shelter;

public interface ShelterButtonService {
    Keyboard getInfoMenu(long shelterId);
    Keyboard getChooseSheltersMenu();

    Keyboard getBaseInformationMenu(Shelter shelter,long shelterId);
}

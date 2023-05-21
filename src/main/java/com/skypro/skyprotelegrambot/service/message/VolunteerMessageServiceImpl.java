package com.skypro.skyprotelegrambot.service.message;

import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.service.PropertyMessageService;
import com.skypro.skyprotelegrambot.service.message.button.ShelterButtonService;

public class VolunteerMessageServiceImpl implements VolunteerMessageService{
    private final PropertyMessageService propertyMessageService;
    private final ShelterButtonService shelterButtonService;

    public VolunteerMessageServiceImpl(PropertyMessageService propertyMessageService, ShelterButtonService shelterButtonService) {
        this.propertyMessageService = propertyMessageService;
        this.shelterButtonService = shelterButtonService;
    }

    @Override
    public SendMessage getMessageAfterSentVolunteerContact(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, propertyMessageService.getMessage("volunteer.contact.sent"));
        sendMessage.replyMarkup(shelterButtonService.getInfoMenu());
        return sendMessage;
    }
}

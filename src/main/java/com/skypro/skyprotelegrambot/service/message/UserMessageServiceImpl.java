package com.skypro.skyprotelegrambot.service.message;

import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.service.PropertyMessageService;
import com.skypro.skyprotelegrambot.service.message.button.ShelterButtonService;
import org.springframework.stereotype.Service;

@Service
public class UserMessageServiceImpl implements UserMessageService {
    private final PropertyMessageService propertyMessageService;
    private final ShelterButtonService shelterButtonService;

    public UserMessageServiceImpl(PropertyMessageService propertyMessageService, ShelterButtonService shelterButtonService) {
        this.propertyMessageService = propertyMessageService;
        this.shelterButtonService = shelterButtonService;
    }

    @Override
    public SendMessage getMessageAfterSentContact(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, propertyMessageService.getMessage("contact.sent"));
        sendMessage.replyMarkup(shelterButtonService.getInfoMenu());
        return sendMessage;
    }
}

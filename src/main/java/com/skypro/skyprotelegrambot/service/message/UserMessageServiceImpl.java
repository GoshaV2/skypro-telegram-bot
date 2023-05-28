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

    @Override
    public SendMessage getMessageForOverdueReportToUser(long chatId, String shelterName) {
        return new SendMessage(chatId,
                String.format(propertyMessageService.getMessage("report.overdue.message"), shelterName));
    }

    @Override
    public SendMessage getMessageForOverdueReportToVolunteer(long chatId, String userName, long userChatId) {
        return new SendMessage(chatId,
                String.format(propertyMessageService.getMessage("report.overdue.message.toVolunteer"),
                        userName, userChatId));
    }
}

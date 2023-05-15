package com.skypro.skyprotelegrambot.service.message;

import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.entity.Answer;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.service.AnswerService;
import com.skypro.skyprotelegrambot.service.PropertyMessageService;
import com.skypro.skyprotelegrambot.service.message.button.ShelterButtonService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class ShelterMessageServiceImpl implements ShelterMessageService {
    private final ShelterButtonService shelterButtonService;
    private final PropertyMessageService propertyMessageService;
    private final AnswerService answerService;

    public ShelterMessageServiceImpl(ShelterButtonService shelterButtonService, MessageSource messageSource, PropertyMessageService propertyMessageService, AnswerService answerService) {
        this.shelterButtonService = shelterButtonService;
        this.propertyMessageService = propertyMessageService;
        this.answerService = answerService;
    }

    @Override
    public SendMessage getMessageForChoosingShelter(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, propertyMessageService.getMessage("shelter.menu.choose"));
        sendMessage.replyMarkup(shelterButtonService.getChooseSheltersMenu());
        return sendMessage;
    }

    @Override
    public SendMessage getMessageAfterChosenShelter(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId,
                propertyMessageService.getMessage("shelter.menu.chosen.getInfo"));
        sendMessage.replyMarkup(shelterButtonService.getInfoMenu());
        return sendMessage;
    }

    @Override
    public SendMessage getMessageWithInfo(long chatId, Shelter shelter) {
        SendMessage sendMessage = new SendMessage(chatId, propertyMessageService.getMessage("shelter.selectFromList"));
        sendMessage.replyMarkup(shelterButtonService.getBaseInformationMenu(shelter));
        return sendMessage;
    }

    @Override
    public SendMessage getAnswer(Long chatId, String command) {
        Answer answer = answerService.getAnswer(command);
        SendMessage sendMessage = new SendMessage(chatId, answer.getText());
        sendMessage.replyMarkup(shelterButtonService.getBaseInformationMenu(answer.getShelter()));
        return sendMessage;
    }

    @Override
    public SendMessage getMessageBeforeReport(long chatId, Shelter shelter) {
        SendMessage sendMessage = new SendMessage(chatId, propertyMessageService.getMessage("shelter.send.report.info"));
        sendMessage.replyMarkup(shelterButtonService.getBaseInformationMenu(shelter));
        return sendMessage;
    }
}

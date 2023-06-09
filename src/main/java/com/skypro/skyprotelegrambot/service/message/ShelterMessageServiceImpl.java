package com.skypro.skyprotelegrambot.service.message;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.entity.Answer;
import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.service.AnswerService;
import com.skypro.skyprotelegrambot.service.PropertyMessageService;
import com.skypro.skyprotelegrambot.service.message.button.ShelterButtonService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
public class ShelterMessageServiceImpl implements ShelterMessageService {
    private final ShelterButtonService shelterButtonService;
    private final PropertyMessageService propertyMessageService;
    private final AnswerService answerService;

    public ShelterMessageServiceImpl(ShelterButtonService shelterButtonService,
                                     PropertyMessageService propertyMessageService, AnswerService answerService) {
        this.shelterButtonService = shelterButtonService;
        this.propertyMessageService = propertyMessageService;
        this.answerService = answerService;
    }

    @Override
    public SendMessage getMessageForChoosingShelter(long chatId, boolean isFirstRequest) {
        String greeting;
        if (isFirstRequest) {
            greeting = propertyMessageService.getMessage("shelter.menu.choose.greeting");
        } else {
            greeting = propertyMessageService.getMessage("shelter.menu.choose");
        }
        SendMessage sendMessage = new SendMessage(chatId, greeting);
        sendMessage.replyMarkup(shelterButtonService.getChooseSheltersMenu());
        return sendMessage;
    }

    @Override
    public SendMessage getMessageAfterChosenShelter(long chatId, Shelter shelter) {
        SendMessage sendMessage = new SendMessage(chatId,
                String.format(propertyMessageService.getMessage("shelter.menu.chosen.getInfo"), shelter.getName()));
        sendMessage.replyMarkup(shelterButtonService.getInfoMenu());
        return sendMessage;
    }

    @Override
    public SendMessage getMessageWithAnswerMenuInfo(long chatId, Shelter shelter, Category category) {
        Pair<Boolean, Keyboard> pair = shelterButtonService.getAnswerMenu(
                shelter,
                category);
        final boolean hasInformation = pair.getLeft();
        final Keyboard keyboard = pair.getRight();
        final String message;
        if (hasInformation) {
            message = propertyMessageService.getMessage("shelter.selectFromList");
        } else {
            message = propertyMessageService.getMessage("notLoadInformation");
        }
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.replyMarkup(keyboard);
        return sendMessage;
    }

    @Override
    public SendMessage getAnswerMessage(Long chatId, long answerId) {
        Answer answer = answerService.getAnswer(answerId);
        SendMessage sendMessage = new SendMessage(chatId, answer.getText());
        sendMessage.replyMarkup(shelterButtonService.getAnswerMenu(
                answer.getShelter(),
                answer.getCategory()).getRight());
        return sendMessage;
    }

    @Override
    public SendMessage getMessageBeforeReport(long chatId, Shelter shelter) {
        SendMessage sendMessage = new SendMessage(chatId, propertyMessageService.getMessage("shelter.send.report.info"));
        sendMessage.replyMarkup(shelterButtonService.backFromReport(shelter));
        return sendMessage;
    }

    @Override
    public SendMessage getMessageAfterReport(long chatId, Shelter shelter) {
        SendMessage sendMessage = new SendMessage(chatId, String.format(propertyMessageService.getMessage("shelter.after.report"), shelter.getName()));
        sendMessage.replyMarkup(shelterButtonService.getInfoMenu());
        return sendMessage;
    }

    @Override
    public SendMessage getNoProbationMessage(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, propertyMessageService.getMessage("report.no.probation"));
        sendMessage.replyMarkup(shelterButtonService.getInfoMenu());
        return sendMessage;
    }

    @Override
    public SendMessage getBadReportMessage(long chatId, Shelter shelter) {
        String text = propertyMessageService.getMessage("report.is.bad") + " " +
                propertyMessageService.getMessage("shelter.send.report.info");
        SendMessage sendMessage = new SendMessage(chatId, text);
        sendMessage.replyMarkup(shelterButtonService.backFromReport(shelter));
        return sendMessage;
    }
    @Override
    public SendMessage getBadSavingReportMessage(long chatId, Shelter shelter){
        SendMessage sendMessage = new SendMessage(chatId, propertyMessageService.getMessage("report.bad.record"));
        sendMessage.replyMarkup(shelterButtonService.getInfoMenu());
        return sendMessage;
    }
}

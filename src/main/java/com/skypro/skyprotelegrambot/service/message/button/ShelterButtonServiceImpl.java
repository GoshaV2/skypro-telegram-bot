package com.skypro.skyprotelegrambot.service.message.button;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.service.AnswerService;
import com.skypro.skyprotelegrambot.service.PropertyMessageService;
import com.skypro.skyprotelegrambot.service.ShelterService;
import com.skypro.skyprotelegrambot.model.command.ShelterCommand;
import org.springframework.stereotype.Service;

@Service
public class ShelterButtonServiceImpl implements ShelterButtonService {
    private final ShelterService shelterService;
    private final PropertyMessageService propertyMessageService;
    private final AnswerService answerService;


    public ShelterButtonServiceImpl(ShelterService shelterService, PropertyMessageService propertyMessageService, AnswerService answerService) {
        this.shelterService = shelterService;
        this.propertyMessageService = propertyMessageService;
        this.answerService = answerService;
    }

    @Override
    public Keyboard getInfoMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(propertyMessageService.getMessage("shelter.info"))
                        .callbackData(ShelterCommand.GET_INFO_MENU.getStartPath()));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(propertyMessageService.getMessage("shelter.howGetAnimal"))
                        .callbackData("/3"));//это что за дробь три??
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(propertyMessageService.getMessage("shelter.send.report"))
                        .callbackData(ShelterCommand.SEND_REPORT.getStartPath())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(propertyMessageService.getMessage("back")).callbackData("/start")
        );
        return inlineKeyboardMarkup;
    }

    @Override
    public Keyboard getChooseSheltersMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        shelterService.getShelters().forEach(shelter -> {
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(shelter.getName())
                    .callbackData(ShelterCommand.CHOOSE_SHELTER.getStartPath() + shelter.getId()));
        });
        return inlineKeyboardMarkup;
    }

    @Override
    public Keyboard getBaseInformationMenu(Shelter shelter) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        answerService.getAnswersByCategory(Category.INFORMATION, shelter).forEach(answer -> {
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(answer.getTitle())
                    .callbackData(answer.getCommand()));
        });
        return inlineKeyboardMarkup;
    }
    @Override
    public Keyboard getTakePetInformationMenu(Shelter shelter) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        answerService.getAnswersByCategory(Category.GETTING_ANIMAL, shelter).forEach(answer -> {
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(answer.getTitle())
                    .callbackData(answer.getCommand()));
        });
        return inlineKeyboardMarkup;
    }
    public Keyboard backFromReport(Shelter shelter){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(propertyMessageService.getMessage("back"))
                .callbackData(ShelterCommand.CHOOSE_SHELTER.getStartPathPattern()));
        return inlineKeyboardMarkup;
    }


}

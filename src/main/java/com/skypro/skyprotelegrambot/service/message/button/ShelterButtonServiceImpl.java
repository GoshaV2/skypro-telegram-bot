package com.skypro.skyprotelegrambot.service.message.button;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.model.command.ShelterCommand;
import com.skypro.skyprotelegrambot.model.command.UserCommand;
import com.skypro.skyprotelegrambot.service.AnswerService;
import com.skypro.skyprotelegrambot.service.PropertyMessageService;
import com.skypro.skyprotelegrambot.service.ShelterService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
        for (Category category : Category.values()) {
            final String categoryMessage = propertyMessageService.getMessage(category.getPropertyMessageKey());
            inlineKeyboardMarkup.addRow(
                    new InlineKeyboardButton(categoryMessage)
                            .callbackData(ShelterCommand.GET_INFO_MENU.getStartPath() + category.name()));
        }
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(propertyMessageService.getMessage("shelter.send.report"))
                        .callbackData(ShelterCommand.SEND_REPORT.getStartPath())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(propertyMessageService.getMessage("contact.startMessage"))
                        .callbackData(UserCommand.SEND_CONTACT.getCommand())
        );
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(propertyMessageService.getMessage("button.back"))
                .callbackData("/start"));
        return inlineKeyboardMarkup;
    }

    @Override
    public Keyboard getChooseSheltersMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        shelterService.getShelters().forEach(shelter ->
                inlineKeyboardMarkup.addRow(new InlineKeyboardButton(shelter.getName())
                        .callbackData(ShelterCommand.CHOOSE_SHELTER.getStartPath() + shelter.getId())));
        return inlineKeyboardMarkup;
    }

    @Override
    public Pair<Boolean, Keyboard> getAnswerMenu(Shelter shelter, Category category) {
        final var wrapper = new Object() {
            boolean hasInformation;
        };
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        answerService.getAnswersByCategory(category, shelter).forEach(answer -> {
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(answer.getTitle())
                    .callbackData(answer.getCommand()));
            wrapper.hasInformation = true;
        });
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(propertyMessageService.getMessage("button.back"))
                        .callbackData(ShelterCommand.CHOOSE_SHELTER.getStartPath() + shelter.getId())
        );
        return new ImmutablePair<>(wrapper.hasInformation, inlineKeyboardMarkup);
    }

    @Override
    public Keyboard getTakePetInformationMenu(Shelter shelter) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        answerService.getAnswersByCategory(Category.GETTING_ANIMAL, shelter).forEach(answer ->
                inlineKeyboardMarkup.addRow(new InlineKeyboardButton(answer.getTitle())
                        .callbackData(answer.getCommand()))
        );
        return inlineKeyboardMarkup;
    }

    @Override
    public Keyboard backFromReport(Shelter shelter) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(propertyMessageService.getMessage("button.back"))
                .callbackData(ShelterCommand.CHOOSE_SHELTER.getStartPath() + shelter.getId()));
        return inlineKeyboardMarkup;
    }


}

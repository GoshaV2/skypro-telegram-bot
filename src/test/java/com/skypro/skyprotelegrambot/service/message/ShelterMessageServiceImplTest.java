package com.skypro.skyprotelegrambot.service.message;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.service.PropertyMessageService;
import com.skypro.skyprotelegrambot.service.message.button.ShelterButtonService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShelterMessageServiceImplTest {
    @Mock
    private ShelterButtonService shelterButtonService;
    @Mock
    private PropertyMessageService propertyMessageService;
    @InjectMocks
    private ShelterMessageServiceImpl shelterMessageService;

    @Test
    void getMessageForChoosingShelter_whenRequestIsFirst() {
        shelterMessageService.getMessageForChoosingShelter(1L, true);
        verify(propertyMessageService).getMessage("shelter.menu.choose.greeting");
        verify(propertyMessageService, never()).getMessage("shelter.menu.choose");
        verify(shelterButtonService).getChooseSheltersMenu();
    }

    @Test
    void getMessageForChoosingShelter_whenRequestNotIsFirst() {
        shelterMessageService.getMessageForChoosingShelter(1L, false);
        verify(propertyMessageService).getMessage("shelter.menu.choose");
        verify(propertyMessageService, never()).getMessage("shelter.menu.choose.greeting");
        verify(shelterButtonService).getChooseSheltersMenu();
    }

    @Test
    void getMessageWithAnswerMenuInfo_whenDataLoaded() {
        Shelter shelter = new Shelter();
        Category category = Category.GETTING_ANIMAL;
        when(shelterButtonService.getAnswerMenu(shelter, category))
                .thenReturn(new ImmutablePair<>(true, new Keyboard() {
                }));
        shelterMessageService.getMessageWithAnswerMenuInfo(1L, shelter, category);
        verify(propertyMessageService).getMessage("shelter.selectFromList");
        verify(propertyMessageService, never()).getMessage("notLoadInformation");
        verify(shelterButtonService).getAnswerMenu(shelter, category);
    }

    @Test
    void getMessageWithAnswerMenuInfo_whenDataNotLoaded() {
        Shelter shelter = new Shelter();
        Category category = Category.GETTING_ANIMAL;
        when(shelterButtonService.getAnswerMenu(shelter, category))
                .thenReturn(new ImmutablePair<>(false, new Keyboard() {
                }));
        shelterMessageService.getMessageWithAnswerMenuInfo(1L, shelter, category);
        verify(propertyMessageService, never()).getMessage("shelter.selectFromList");
        verify(propertyMessageService).getMessage("notLoadInformation");
        verify(shelterButtonService).getAnswerMenu(shelter, category);
    }

    @Test
    void getMessageAfterChosenShelter() {
        when(propertyMessageService.getMessage("shelter.menu.chosen.getInfo")).thenReturn("test");
        Shelter shelter = new Shelter();
        shelter.setName("test");
        shelterMessageService.getMessageAfterChosenShelter(1L, shelter);
        verify(propertyMessageService).getMessage("shelter.menu.chosen.getInfo");
        verify(shelterButtonService).getInfoMenu();
    }

}
package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.skypro.skyprotelegrambot.configuration.TelegramBotTestConfiguration;
import com.skypro.skyprotelegrambot.service.PropertyMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TelegramBotTestConfiguration.class})
@Sql(scripts = {"/script/handler/test-data-for-get-info-menu-handler.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GetInfoMenuHandlerTest {
    @Autowired
    private GetInfoMenuHandler getInfoMenuHandler;
    @Autowired
    private PropertyMessageService propertyMessageService;
    @Mock
    private Update update;
    @Mock
    private CallbackQuery callbackQuery;
    @Mock
    private BaseResponse baseResponse;
    @Mock
    private User user;
    @Autowired
    private TelegramBot telegramBot;

    @BeforeEach
    void mockResponseToSendTelegramMessage() {
        when(baseResponse.isOk()).thenReturn(true);
        when(telegramBot.execute(any())).thenReturn(baseResponse);
    }
    @Test
    void apply_whenCommandIsCorrect() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("/getInfoMenu/INFORMATION");
        assertTrue(getInfoMenuHandler.apply(update));
    }

    @Test
    void process_whenMenuHasLoadData() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("/getInfoMenu/INFORMATION");
        when(callbackQuery.from()).thenReturn(user);
        when(user.id()).thenReturn(1L);
        getInfoMenuHandler.process(update);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());

        SendMessage sendMessage = argumentCaptor.getValue();
        Map<String, Object> parameters = sendMessage.getParameters();
        String text = (String) parameters.get("text");
        long chatId = (Long) parameters.get("chat_id");
        InlineKeyboardMarkup inlineKeyboardMarkup = (InlineKeyboardMarkup) parameters.get("reply_markup");
        InlineKeyboardButton[][] inlineKeyboardButtons = inlineKeyboardMarkup.inlineKeyboard();

        assertEquals(text, propertyMessageService.getMessage("shelter.selectFromList"));
        assertEquals(chatId, 1);
        assertEquals(inlineKeyboardButtons.length, 3);
        assertEquals(inlineKeyboardButtons[0][0].callbackData(), "/chooseAnswer/1");
        assertEquals(inlineKeyboardButtons[1][0].callbackData(), "/chooseAnswer/2");
        assertEquals(inlineKeyboardButtons[2][0].callbackData(), "/chooseShelter/1");
    }
    @Test
    void process_whenMenuNotHasLoadData() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("/getInfoMenu/GETTING_ANIMAL");
        when(callbackQuery.from()).thenReturn(user);
        when(user.id()).thenReturn(1L);
        getInfoMenuHandler.process(update);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());

        SendMessage sendMessage = argumentCaptor.getValue();
        Map<String, Object> parameters = sendMessage.getParameters();
        String text = (String) parameters.get("text");
        long chatId = (Long) parameters.get("chat_id");
        InlineKeyboardMarkup inlineKeyboardMarkup = (InlineKeyboardMarkup) parameters.get("reply_markup");
        InlineKeyboardButton[][] inlineKeyboardButtons = inlineKeyboardMarkup.inlineKeyboard();

        assertEquals(text, propertyMessageService.getMessage("notLoadInformation"));
        assertEquals(chatId, 1);
        assertEquals(inlineKeyboardButtons.length, 1);
        assertEquals(inlineKeyboardButtons[0][0].callbackData(), "/chooseShelter/1");
    }
}
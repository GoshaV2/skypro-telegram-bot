package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TelegramBotTestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = {"/script/clear-all-data.sql"})
@Sql(scripts = {"/script/handler/test-data-for-start-handler.sql"})
class StartHandlerTest {
    @Autowired
    private StartHandler startHandler;
    @Autowired
    private PropertyMessageService propertyMessageService;
    @Mock
    private Update update;
    @Mock
    private Message message;
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
    void apply_whenIsMessageAndCorrectCommand_thenReturnTrue() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start");
        assertTrue(startHandler.apply(update));
    }

    @Test
    void apply_whenIsCallbackQueryAndCorrectCommand_thenReturnTrue() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("/start");
        assertTrue(startHandler.apply(update));
    }

    @Test
    void process_whenIsCallbackQuery_thenSendMessageWithMessageWithoutGreeting() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("/start");
        when(callbackQuery.from()).thenReturn(user);
        when(user.id()).thenReturn(100L);
        startHandler.process(update);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());

        SendMessage sendMessage = argumentCaptor.getValue();
        Map<String, Object> parameters = sendMessage.getParameters();
        String text = (String) parameters.get("text");
        long chatId = (Long) parameters.get("chat_id");
        InlineKeyboardMarkup inlineKeyboardMarkup = (InlineKeyboardMarkup) parameters.get("reply_markup");
        InlineKeyboardButton[][] inlineKeyboardButtons = inlineKeyboardMarkup.inlineKeyboard();

        assertEquals(text, propertyMessageService.getMessage("shelter.menu.choose"));
        assertEquals(chatId, 100);
        assertEquals(inlineKeyboardButtons.length, 3);
        assertEquals(inlineKeyboardButtons[0][0].callbackData(), "/chooseShelter/100");
        assertEquals(inlineKeyboardButtons[1][0].callbackData(), "/chooseShelter/200");
        assertEquals(inlineKeyboardButtons[2][0].callbackData(), "/chooseShelter/300");
    }

    @Test
    void process_whenIsMessageAndFirstRequest_thenSendMessageWithMessageWithGreeting() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start");
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(100L);
        startHandler.process(update);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        argumentCaptor.getValue();
        SendMessage sendMessage = argumentCaptor.getValue();
        Map<String, Object> parameters = sendMessage.getParameters();
        String text = (String) parameters.get("text");
        long chatId = (Long) parameters.get("chat_id");
        InlineKeyboardMarkup inlineKeyboardMarkup = (InlineKeyboardMarkup) parameters.get("reply_markup");
        InlineKeyboardButton[][] inlineKeyboardButtons = inlineKeyboardMarkup.inlineKeyboard();

        assertEquals(text, propertyMessageService.getMessage("shelter.menu.choose.greeting"));
        assertEquals(chatId, 100);
        assertEquals(inlineKeyboardButtons.length, 3);
        assertEquals(inlineKeyboardButtons[0][0].callbackData(), "/chooseShelter/100");
        assertEquals(inlineKeyboardButtons[1][0].callbackData(), "/chooseShelter/200");
        assertEquals(inlineKeyboardButtons[2][0].callbackData(), "/chooseShelter/300");
    }

    @Test
    void process_whenIsMessageAndNotFirstRequest_thenSendMessageWithMessageWithoutGreeting() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start");
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(200L);
        startHandler.process(update);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        argumentCaptor.getValue();
        SendMessage sendMessage = argumentCaptor.getValue();
        Map<String, Object> parameters = sendMessage.getParameters();
        String text = (String) parameters.get("text");
        long chatId = (Long) parameters.get("chat_id");
        InlineKeyboardMarkup inlineKeyboardMarkup = (InlineKeyboardMarkup) parameters.get("reply_markup");
        InlineKeyboardButton[][] inlineKeyboardButtons = inlineKeyboardMarkup.inlineKeyboard();

        assertEquals(text, propertyMessageService.getMessage("shelter.menu.choose"));
        assertEquals(chatId, 200);
        assertEquals(inlineKeyboardButtons.length, 3);
        assertEquals(inlineKeyboardButtons[0][0].callbackData(), "/chooseShelter/100");
        assertEquals(inlineKeyboardButtons[1][0].callbackData(), "/chooseShelter/200");
        assertEquals(inlineKeyboardButtons[2][0].callbackData(), "/chooseShelter/300");
    }
}
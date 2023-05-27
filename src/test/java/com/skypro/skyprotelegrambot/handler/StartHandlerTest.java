package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.configuration.TelegramBotTestConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TelegramBotTestConfiguration.class})
@Sql(scripts = {"/script/test-user.sql"})
class StartHandlerTest {
    @Autowired
    private StartHandler startHandler;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private CallbackQuery callbackQuery;
    @Mock
    private User user;
    @Autowired
    private TelegramBot telegramBot;
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
    void process_whenIsCallbackQueryAndCorrectCommand_thenSendMessageWithMenu() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("/start");
        when(callbackQuery.from()).thenReturn(user);
        when(user.id()).thenReturn(1L);
        startHandler.process(update);

        ArgumentCaptor<SendMessage> argumentCaptor=ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        argumentCaptor.getValue();
        SendMessage sendMessage=argumentCaptor.getValue();
        Map<String,Object> parameters=sendMessage.getParameters();

        InlineKeyboardMarkup inlineKeyboardMarkup=(InlineKeyboardMarkup) parameters.get("reply_markup");
        InlineKeyboardButton[][] inlineKeyboardButtons=inlineKeyboardMarkup.inlineKeyboard();

//        Assertions.assertThat(inlineKeyboardButtons[0]).containsExactlyInAnyOrder()
    }
}
package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.skypro.skyprotelegrambot.configuration.TelegramBotTestConfiguration;
import com.skypro.skyprotelegrambot.repository.UserRepository;
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
@Sql(scripts = {"/script/handler/test-data-for-send-contact-handler.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserSendContactHandlerTest {
    @Autowired
    private UserSendContactHandler userSendContactHandler;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private BaseResponse baseResponse;
    @Mock
    private User user;
    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private PropertyMessageService propertyMessageService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void mockResponseToSendTelegramMessage() {
        when(baseResponse.isOk()).thenReturn(true);
        when(telegramBot.execute(any())).thenReturn(baseResponse);
    }

    @Test
    void apply_whenUserSessionHasWaitingContact_thenReturnTrue() {
        when(update.message()).thenReturn(message);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(1L);
        assertTrue(userSendContactHandler.apply(update));
    }


    @Test
    void process_whenUserSessionHasWaitingContact_thenSaveUserContact() {
        final String contact="+777777777";
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(contact);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(1L);
        userSendContactHandler.process(update);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        argumentCaptor.getValue();
        SendMessage sendMessage = argumentCaptor.getValue();
        Map<String, Object> parameters = sendMessage.getParameters();
        String text = (String) parameters.get("text");
        long chatId = (Long) parameters.get("chat_id");
        InlineKeyboardMarkup inlineKeyboardMarkup = (InlineKeyboardMarkup) parameters.get("reply_markup");
        InlineKeyboardButton[][] inlineKeyboardButtons = inlineKeyboardMarkup.inlineKeyboard();
        assertEquals(userRepository.findById(1L).get().getContact(),contact);
        assertEquals(text, propertyMessageService.getMessage("contact.sent"));
        assertEquals(chatId, 1);
        assertEquals(inlineKeyboardButtons.length, 6);
        assertEquals(inlineKeyboardButtons[0][0].callbackData(), "/getInfoMenu/INFORMATION");
        assertEquals(inlineKeyboardButtons[1][0].callbackData(), "/getInfoMenu/GETTING_ANIMAL");
        assertEquals(inlineKeyboardButtons[2][0].callbackData(), "/sendReport");
        assertEquals(inlineKeyboardButtons[3][0].callbackData(), "/sendContact");
        assertEquals(inlineKeyboardButtons[4][0].callbackData(), "/sendVolunteerContact");
        assertEquals(inlineKeyboardButtons[5][0].callbackData(), "/start");
    }
}
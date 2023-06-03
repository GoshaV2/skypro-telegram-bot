package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.skypro.skyprotelegrambot.configuration.TelegramBotTestConfiguration;
import com.skypro.skyprotelegrambot.entity.Session;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TelegramBotTestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = {"/script/clear-all-data.sql"})
@Sql(scripts = {"/script/handler/test-data-for-user-contact-init-handler.sql"})
class UserContactInitHandlerTest {
    @Autowired
    private UserContactInitHandler userContactInitHandler;
    @Mock
    private Update update;
    @Mock
    private CallbackQuery callbackQuery;
    @Mock
    private BaseResponse baseResponse;
    @Mock
    private User user;
    @Autowired
    private PropertyMessageService propertyMessageService;
    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void mockResponseToSendTelegramMessage() {
        when(baseResponse.isOk()).thenReturn(true);
        when(telegramBot.execute(any())).thenReturn(baseResponse);
    }

    @Test
    void apply_whenIsCorrectCommand_thenReturnTrue() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("/sendContact");
        assertTrue(userContactInitHandler.apply(update));
    }

    @Test
    void process_thenSessionMustHasDataAboutSendingContact() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("/sendContact");
        when(callbackQuery.from()).thenReturn(user);
        when(user.id()).thenReturn(100L);
        userContactInitHandler.process(update);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());

        Session userSession = userRepository.findUserByChatId(100L).get().getSession();

        SendMessage sendMessage = argumentCaptor.getValue();
        Map<String, Object> parameters = sendMessage.getParameters();
        String text = (String) parameters.get("text");
        long chatId = (Long) parameters.get("chat_id");

        assertEquals(text, propertyMessageService.getMessage("contact.init"));
        assertEquals(chatId, 100);
        assertTrue(userSession.hasWaitingContact());
    }
}
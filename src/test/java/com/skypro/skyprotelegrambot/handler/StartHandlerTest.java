package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.skypro.skyprotelegrambot.service.TelegramMessageService;
import com.skypro.skyprotelegrambot.service.UserService;
import com.skypro.skyprotelegrambot.service.message.ShelterMessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class StartHandlerTest {
    @Mock
    private TelegramMessageService telegramMessageService;
    @Mock
    private ShelterMessageService shelterMessageService;
    @Mock
    private UserService userService;
    @InjectMocks
    private StartHandler startHandler;

    @Test
    void apply_whenAppliedAndCallbackNotNull_thenReturnTrue() {
        Update update = new Update() {
            @Override
            public CallbackQuery callbackQuery() {
                return new CallbackQuery() {

                    @Override
                    public String data() {
                        return "/start";
                    }
                };
            }
        };
        assertTrue(startHandler.apply(update));
    }

    @Test
    void apply_whenAppliedAndMessageNotNull_thenReturnTrue() {
        Update update = new Update() {
            @Override
            public Message message() {
                return new Message() {

                    @Override
                    public String text() {
                        return "/start";
                    }
                };
            }
        };
        assertTrue(startHandler.apply(update));
    }

    @Test
    void apply_whenNotAppliedAndMessageNotNull_thenReturnFalse() {
        Update update = new Update() {
            @Override
            public Message message() {
                return new Message() {

                    @Override
                    public String text() {
                        return "start";
                    }
                };
            }
        };
        assertFalse(startHandler.apply(update));
    }
}
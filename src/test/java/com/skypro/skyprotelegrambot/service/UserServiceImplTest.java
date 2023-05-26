package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.repository.SessionRepository;
import com.skypro.skyprotelegrambot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private SessionRepository sessionRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_whenSuccessCreated_thenReturnUserWithNotNullSession() {
        final long chatId = 1;
        User userCreate = userService.createUser(chatId,"test");
        assertNotNull(userCreate.getSession());
        assertEquals(chatId, userCreate.getChatId());
        verify(userRepository).save(any());
        verify(sessionRepository).save(any());
    }
}
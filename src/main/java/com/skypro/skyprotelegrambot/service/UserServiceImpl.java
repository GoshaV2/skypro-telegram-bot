package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.entity.Session;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.exception.NotFoundElement;
import com.skypro.skyprotelegrambot.repository.SessionRepository;
import org.springframework.stereotype.Service;
import com.skypro.skyprotelegrambot.repository.UserRepository;

/**
 * Класс определяющий методы обработки пользовательских состояний
 * */

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ShelterService shelterService;
    private final SessionRepository sessionRepository;

    public UserServiceImpl(UserRepository userRepository, ShelterService shelterService, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.shelterService = shelterService;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public User saveUser(User user) { //поменять передаваемое значение
        return userRepository.save(user);
    }

    @Override
    public User findUserByChatId(Long chatId) {
        User user = userRepository.findUserByChatId(chatId);
        if (user == null) {
           throw new NotFoundElement(chatId,User.class);
        }
        return user;
    }

    @Override
    public User chooseShelterForUser(Long chatId, Long id) {
        Shelter shelter = shelterService.findShelterById(id);
        User user = findUserByChatId(chatId);
        Session session = user.getSession();
        session.setSelectedShelter(shelter);
        sessionRepository.save(session);
        return user;
    }

    @Override
    public User createUser(Long chatId) {
        User user = new User();
        user.setChatId(chatId);
        Session session = new Session();
        user.setSession(session);
        sessionRepository.save(session);
        userRepository.save(user);
        return user;
    }
}


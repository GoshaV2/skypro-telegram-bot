package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.entity.Session;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;
import org.springframework.stereotype.Service;
import com.skypro.skyprotelegrambot.repository.UserRepository;

/**
 * Класс определяющий методы обработки пользовательских состояний
 * */

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ShelterService shelterService;

    public UserServiceImpl(UserRepository userRepository, ShelterService shelterService) {
        this.userRepository = userRepository;
        this.shelterService = shelterService;
    }

    @Override
    public User saveUser(User user) { //поменять передаваемое значение
        return userRepository.save(user);
    }

    @Override
    public User findUserByChatId(Long chatId) {
        User user = userRepository.findUserByChatId(chatId);
        if (user == null) {
            return new User();
        }
        return user;
    }

    @Override
    public User chooseShelterForUser(Long chatId, Long id) {
        Shelter shelter = shelterService.findShelterById(id);
        User user = new User();
        Session session = user.getSession();
        session.getSelectedShelter(shelter);
        return user;
    }

    @Override
    public User createUser(Long chatId) {
        User user = new User();
        user.setChatId(chatId);
        Session session = new Session();
        user.setSession(session);
        userRepository.save(user);
        return user;
    }
}


package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.entity.Session;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.exception.UserNotFoundException;
import com.skypro.skyprotelegrambot.repository.SessionRepository;
import com.skypro.skyprotelegrambot.repository.UserRepository;
import org.springframework.stereotype.Service;


/**
 * Класс определяющий методы обработки пользовательских состояний
 */

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ShelterService shelterService;
    private final SessionRepository sessionRepository;

    public UserServiceImpl(UserRepository userRepository,
                           ShelterService shelterService,
                           SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.shelterService = shelterService;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public User getUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with userId=" + userId));
    }

    @Override
    public boolean existUser(long chatId) {
        return userRepository.existsByChatId(chatId);
    }

    @Override
    public User saveUser(User user) { //поменять передаваемое значение
        sessionRepository.save(user.getSession());
        return userRepository.save(user);
    }

    /**
     * Метод находит пользователя по chat id
     */

    public User findUserByChatId(Long chatId) {
        return userRepository.findUserByChatId(chatId).orElseThrow(()
                -> new UserNotFoundException("User not found with chatId=" + chatId));
    }

    /**
     * Метод выбора приюта для пользователя
     */

    @Override
    public User chooseShelterForUser(Long chatId, Long id) {
        Shelter shelter = shelterService.findShelterById(id);
        User user = findUserByChatId(chatId);
        Session session = user.getSession();
        session.setSelectedShelter(shelter);
        sessionRepository.save(session);
        return user;
    }

    /**
     * Метод создания нового пользователя
     */

    @Override
    public User createUser(Long chatId, String name) {
        User user = new User();
        user.setChatId(chatId);
        user.setName(name);
        Session session = new Session();
        user.setSession(session);
        sessionRepository.save(session);
        userRepository.save(user);
        return user;
    }

    @Override
    public User turnOnReportSending(User user) {
        Session session = user.getSession();
        session.setReportSending(true);
        sessionRepository.save(session);
        return user;
    }

    @Override
    public User turnOffReportSending(User user) {
        Session session = user.getSession();
        session.setReportSending(false);
        sessionRepository.save(session);
        return user;
    }
}


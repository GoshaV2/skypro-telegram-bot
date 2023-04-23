package com.skypro.skyprotelegrambot.service;

import com.skypro.skyprotelegrambot.entity.User;
import org.springframework.stereotype.Service;
import com.skypro.skyprotelegrambot.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}


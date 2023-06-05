package com.skypro.skyprotelegrambot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User can has only 1 active probation in shelter")
public class UserAlreadyHasShelterProbation extends RuntimeException {
    public UserAlreadyHasShelterProbation(long userId, long shelterId) {
        super(String.format("User(id=%d) can't has any active probation in shelter(id=%d)", userId, shelterId));
    }
}

package com.skypro.skyprotelegrambot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You can change status only of active probation")
public class ProbationChangeStatusException extends RuntimeException {
    public ProbationChangeStatusException(long id) {
        super(String.format("Not active probation(id=%d) can be changed", id));
    }
}

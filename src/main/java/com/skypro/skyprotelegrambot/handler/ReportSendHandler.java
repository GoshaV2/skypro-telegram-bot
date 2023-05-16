package com.skypro.skyprotelegrambot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class ReportSendHandler implements CommandHandler {
    private final UserService userService;

    public ReportSendHandler(UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean apply(Update update) {
        Message message = update.message();
        CallbackQuery callbackQuery = update.callbackQuery();
        Long id = (message!= null) ? message.from().id(): callbackQuery.from().id();

        return userService.findUserByChatId(id).getSession().isReportSending();
    }

    @Override
    public void process(Update update) {
        Message message = update.message();
        CallbackQuery callbackQuery = update.callbackQuery();

        if (callbackQuery != null){
            User user = userService.findUserByChatId(callbackQuery.from().id());
            userService.turnOffReportSending(user);
            return;
        }


    }
}

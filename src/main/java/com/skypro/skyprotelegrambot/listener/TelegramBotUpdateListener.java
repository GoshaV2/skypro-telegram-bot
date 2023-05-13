package com.skypro.skyprotelegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.skypro.skyprotelegrambot.entity.User;
import com.skypro.skyprotelegrambot.exception.UserNotFoundException;
import com.skypro.skyprotelegrambot.handler.CommandHandler;
import com.skypro.skyprotelegrambot.service.AnswerService;
import com.skypro.skyprotelegrambot.service.UserService;
import com.skypro.skyprotelegrambot.service.message.ShelterMessageService;
import com.skypro.skyprotelegrambot.model.command.ShelterCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

@Component
public class TelegramBotUpdateListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final ShelterMessageService shelterMessageService;
    private final UserService userService;
    private final AnswerService answerService;
    private final Collection<CommandHandler> commandHandlers;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);

    public TelegramBotUpdateListener(TelegramBot telegramBot, ShelterMessageService shelterMessageService,
                                     UserService userService, AnswerService answerService,
                                     Collection<CommandHandler> commandHandlers) {
        this.telegramBot = telegramBot;
        this.shelterMessageService = shelterMessageService;
        this.userService = userService;
        this.answerService = answerService;
        this.commandHandlers = commandHandlers;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> list) {
        try {
            list.forEach(update -> {
                for(CommandHandler commandHandler:commandHandlers){
                    if(commandHandler.apply(update)){
                        commandHandler.process(update);
                        break;
                    }
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}

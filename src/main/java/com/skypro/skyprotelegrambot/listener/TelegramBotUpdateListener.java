package com.skypro.skyprotelegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.skyprotelegrambot.handler.CommandHandler;
import com.skypro.skyprotelegrambot.service.PropertyMessageService;
import com.skypro.skyprotelegrambot.service.TelegramMessageService;
import com.skypro.skyprotelegrambot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class TelegramBotUpdateListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final Collection<CommandHandler> commandHandlers;
    private final TelegramMessageService telegramMessageService;
    private final PropertyMessageService propertyMessageService;
    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);

    public TelegramBotUpdateListener(TelegramBot telegramBot,
                                     Collection<CommandHandler> commandHandlers,
                                     TelegramMessageService telegramMessageService, PropertyMessageService propertyMessageService, UserService userService) {
        this.telegramBot = telegramBot;
        this.commandHandlers = commandHandlers;
        this.telegramMessageService = telegramMessageService;
        this.propertyMessageService = propertyMessageService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Обработка запросов
     * Если пользователь первый раз обращается к боту, то создаётся аккаунт и сессия
     */
    @Override
    public int process(List<Update> list) {

        list.forEach(update -> {
            try {
                final long chatId;
                final User user;
                final String name;
                if (update.message() != null) {
                    chatId = update.message().chat().id();
                    user = update.message().from();
                } else {
                    chatId = update.callbackQuery().from().id();
                    user = update.callbackQuery().message().from();
                }
                name = String.format("%s %s", user.lastName(), user.firstName());
                if (!userService.existUser(chatId)) {
                    userService.createUser(chatId, name);
                    logger.info(String.format("User(chatId=%d) was be created", chatId));
                }
                executeHandler(update, chatId);

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Найти и выполнить обработчик из цепочки, в случае когда не был найден обработчик отправляется сообщение пользователю
     */
    private void executeHandler(Update update, long chatId) {
        boolean hasFoundHandler = false;
        for (CommandHandler commandHandler : commandHandlers) {
            if (commandHandler.apply(update)) {
                commandHandler.process(update);
                hasFoundHandler = true;
                break;
            }
        }
        if (!hasFoundHandler) {
            if (update.message() != null) {
                SendMessage sendMessage = new SendMessage(chatId,
                        propertyMessageService.getMessage("command.notFound"));
                telegramMessageService.execute(sendMessage);
            }
        }
    }
}

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
                logger.info("New update: {}", update);
                String text; //text message
                Long chatId; //chat id from telegram base. Bot can't work in group chats
                User user;
                Message message = update.message();
                CallbackQuery callbackQuery = update.callbackQuery();

                if (callbackQuery != null) {
                    chatId = callbackQuery.from().id();
                    text = callbackQuery.data();
                } else {
                    chatId = message.chat().id();
                    text = message.text();
                }
                try {
                    user = userService.findUserByChatId(chatId);
                } catch (UserNotFoundException e) {
                    user = userService.createUser(chatId);
                    logger.info("New user success created");
                }

                if ("/start".equals(text)) { //обработка входной точки
                   /* SendMessage sendMessage = new SendMessage(chatId,
                            "Привет. Я помогаю приютам для бездомных животных пристроить их питомцев. Какое животное ты бы выбрал?");

                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("кота или кошечку").callbackData("/cats"),
                            new InlineKeyboardButton("собаку").callbackData("/dogs"));
                    sendMessage.replyMarkup(inlineKeyboard);*/
                    SendMessage sendMessage = shelterMessageService.getMessageForChoosingShelter(chatId);
                    send(sendMessage);

                } else if (text.matches(ShelterCommand.CHOOSE_SHELTER.getStartPathPattern())) {
                    long shelterId = Long.parseLong(text
                            .replace(ShelterCommand.CHOOSE_SHELTER.getStartPath(), ""));
                    user = userService.chooseShelterForUser(chatId, shelterId);
                    SendMessage sendMessage = shelterMessageService.getMessageAfterChosenShelter(chatId);
                    send(sendMessage);
                } else if (ShelterCommand.GET_INFO_MENU.getStartPath().equals(text)) {
                    SendMessage sendMessage = shelterMessageService.getMessageWithInfo(chatId, user.getSession().getSelectedShelter());
                    send(sendMessage);
                } else if (answerService.hasCommand(text)) {
                    SendMessage sendMessage = shelterMessageService.getAnswer(chatId, text);
                    send(sendMessage);
                } else if ("/cats".equals(text)) { //выбран приют для кошек
                    SendMessage sendMessage = new SendMessage(chatId,
                            "Был выбран кошачий приют. Чем я могу быть полезен?");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Рассказать о приюте подробнее").callbackData("/cats_info"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Как взять животное из приюта").callbackData("/cats_get_pet"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Прислать отчет о взятом питомце").callbackData("/cats_send_report"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Позвать волонтера").callbackData("/cats_volunteer"),
                            new InlineKeyboardButton("вернуться к выбору приюта").callbackData("/start"));
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs".equals(text)) { //выбран приют для собак
                    SendMessage sendMessage = new SendMessage(chatId,
                            "Был выбран собачий приют. Чем я могу быть полезен?");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Рассказать о приюте подробнее").callbackData("/dogs_info"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Как взять животное из приюта").callbackData("/dogs_get_pet"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Прислать отчет о взятом питомце").callbackData("/dogs_send_report"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Позвать волонтера").callbackData("/dogs_volunteer"),
                            new InlineKeyboardButton("Вернуться к выбору приюта").callbackData("/start"));
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_info".equals(text)) { //инфо по приюту кошек (этап 1)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "Вы решили узнать о приюте подробнее. Чем я могу быть полезен?");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Рассказ о приюте").callbackData("/cats_info_general"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Расписание работы приюта, адрес и схему проезда.").callbackData("/cats_get_address"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Контактные данные охраны для оформления пропуска на машину").callbackData("/cats_guard"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Рекомендации о технике безопасности на территории приюта").callbackData("/cats_safety_rules"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Контакты").callbackData("/cats_contacts"),
                            new InlineKeyboardButton("Назад").callbackData("/cats"));
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_info_general".equals(text)) { //инфо по приюту кошек (этап 1)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/cats_info"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_get_address".equals(text)) { //инфо по приюту кошек (этап 1)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/cats_info"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_guard".equals(text)) { //инфо по приюту кошек (этап 1)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/cats_info"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_safety_rules".equals(text)) { //инфо по приюту кошек (этап 1)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/cats_info"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_contacts".equals(text)) { //инфо по приюту кошек (этап 1)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/cats_info"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_info".equals(text)) { //инфо по приюту собак (этап 1)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "Вы решили узнать о приюте подробнее. Чем я могу быть полезен?");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Рассказ о приюте").callbackData("/dogs_info_general"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Расписание работы приюта, адрес и схему проезда.").callbackData("/dogs_get_address"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Контактные данные охраны для оформления пропуска на машину").callbackData("/dogs_guard"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Рекомендации о технике безопасности на территории приюта").callbackData("/dogs_safety_rules"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Контакты").callbackData("/dogs_contacts"),
                            new InlineKeyboardButton("Назад").callbackData("/dogs"));
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_info_general".equals(text)) { //инфо по приюту собак (этап 1)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/dogs_info"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_get_address".equals(text)) { //инфо по приюту собак (этап 1)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/dogs_info"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_guard".equals(text)) { //инфо по приюту собак (этап 1)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/dogs_info"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_safety_rules".equals(text)) { //инфо по приюту собак (этап 1)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/dogs_info"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_contacts".equals(text)) { //инфо по приюту собак (этап 1)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/dogs_info"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_get_pet".equals(text)) { //как забрать кошку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "Вы решили забрать себе нового питомца. Чем я могу быть полезен?");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Правила знакомства с животным перед тем как его забрать").callbackData("/cats_get_pet_initial"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Список необходимых документов.").callbackData("/cats_get_pet_documents"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Список рекомендаций по транспортировке животного.").callbackData("/cats_get_pet_transporting"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Список рекомендаций по обустройству дома для котенка").callbackData("/cats_get_pet_little"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Список рекомендаций по обустройству дома для взрослого кота").callbackData("/cats_get_pet_big"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Список рекомендаций по обустройству дома для животного-инвалида")
                            .callbackData("/cats_get_pet_damaged"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Список причин для отказа взять животное").callbackData("/cats_get_pet_fail"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Отправить свои контактные данные").callbackData("/cats_send_contacts"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Позвать волонтера").callbackData("/cats_volunteer"),
                            new InlineKeyboardButton("Назад").callbackData("/cats"));
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_get_pet_initial".equals(text)) { //как забрать кошку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/cats_get_pet"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_get_pet_documents".equals(text)) { //как забрать кошку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/cats_get_pet"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_get_pet_transporting".equals(text)) { //как забрать кошку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/cats_get_pet"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_get_pet_little".equals(text)) { //как забрать кошку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/cats_get_pet"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_get_pet_big".equals(text)) { //как забрать кошку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/cats_get_pet"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_get_pet_damaged".equals(text)) { //как забрать кошку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/cats_get_pet"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_get_pet_fail".equals(text)) { //как забрать кошку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/cats_get_pet"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_send_contacts".equals(text)) { //как забрать кошку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/cats_get_pet"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_get_pet".equals(text)) { //как забрать собаку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "Вы решили забрать себе нового питомца. Чем я могу быть полезен?");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Правила знакомства с животным перед тем как его забрать").callbackData("/dogs_get_pet_initial"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Список необходимых документов.").callbackData("/dogs_get_pet_documents"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Список рекомендаций по транспортировке животного.").callbackData("/dogs_get_pet_transporting"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Список рекомендаций по обустройству дома для щенка").callbackData("/dogs_get_pet_little"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Список рекомендаций по обустройству дома для взрослую собаку").callbackData("/dogs_get_pet_big"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Список рекомендаций по обустройству дома для животного-инвалида")
                            .callbackData("/dogs_get_pet_damaged"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Советы кинолога по первичному общению с собакой").callbackData("/dogs_get_pet_advice"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Контакты проверенных кинологов").callbackData("/dogs_get_pet_cytologists"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Список причин для отказа взять животное").callbackData("/dogs_get_pet_fail"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Отправить свои контактные данные").callbackData("/dogs_send_contacts"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Позвать волонтера").callbackData("/dogs_volunteer"),
                            new InlineKeyboardButton("Назад").callbackData("/dogs"));
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_get_pet_initial".equals(text)) { //как забрать собаку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/dogs_get_pet"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_get_pet_documents".equals(text)) { //как забрать собаку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/dogs_get_pet"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_get_pet_transporting".equals(text)) { //как забрать собаку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/dogs_get_pet"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_get_pet_little".equals(text)) { //как забрать собаку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/dogs_get_pet"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_get_pet_big".equals(text)) { //как забрать собаку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/dogs_get_pet"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_get_pet_damaged".equals(text)) { //как забрать собаку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/dogs_get_pet"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_get_pet_advice".equals(text)) { //как забрать собаку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/dogs_get_pet"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_get_pet_cytologists".equals(text)) { //как забрать собаку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/dogs_get_pet"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_get_pet_fail".equals(text)) { //как забрать собаку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/dogs_get_pet"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_send_contacts".equals(text)) { //как забрать собаку (этап 2)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/dogs_get_pet"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_send_report".equals(text)) { //отчет по кошкам (этап 3)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "Вы хотите отправить отчет о вашем новом питомце. Чем я могу быть полезен?");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Форма отчета о питомце").callbackData("/cats_send_report_template"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Отправить отчет").callbackData("/cats_send_report_send"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Позвать волонтера").callbackData("/cats_volunteer"),
                            new InlineKeyboardButton("Назад").callbackData("/cats"));
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_send_report_template".equals(text)) { //отчет по кошкам (этап 3)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/cats_send_report"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_send_report_send".equals(text)) { //отчет по кошкам (этап 3)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/cats_send_report"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_send_report".equals(text)) { //отчет по собакам (этап 3)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "Вы хотите отправить отчет о вашем новом питомце. Чем я могу быть полезен?");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Форма отчета о питомце").callbackData("/dogs_send_report_template"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Отправить отчет").callbackData("/dogs_send_report_send"));
                    inlineKeyboard.addRow(new InlineKeyboardButton("Позвать волонтера").callbackData("/dogs_volunteer"),
                            new InlineKeyboardButton("Назад").callbackData("/dogs"));
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_send_report_template".equals(text)) { //отчет по собакам (этап 3)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/dogs_send_report"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_send_report_send".equals(text)) { //отчет по собакам (этап 3)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("Назад").callbackData("/dogs_send_report"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/cats_volunteer".equals(text)) { //отчет по собакам (этап 3)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("К выбору приюта").callbackData("/start"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                } else if ("/dogs_volunteer".equals(text)) { //отчет по собакам (этап 3)
                    SendMessage sendMessage = new SendMessage(chatId,
                            "В разработке...");
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup().addRow(
                            new InlineKeyboardButton("К выбору приюта").callbackData("/start"));
                    //to do some things...
                    sendMessage.replyMarkup(inlineKeyboard);
                    send(sendMessage);
                }

            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void send(SendMessage sendMessage) {
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

}

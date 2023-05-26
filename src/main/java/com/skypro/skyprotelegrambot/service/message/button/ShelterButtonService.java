package com.skypro.skyprotelegrambot.service.message.button;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.Shelter;
import org.apache.commons.lang3.tuple.Pair;

public interface ShelterButtonService {
    /**
     * Получение стартового меню
     *
     * @return кнопки по разделам приюта. Нужны после выбора приюта.
     */
    Keyboard getInfoMenu();

    /**
     * @return кнопки выбора приюта
     */
    Keyboard getChooseSheltersMenu();

    /**
     * Получение меню с выбором вопросов о приюте по категории
     *
     * @param shelter  выбранный пользователем приют
     * @param category категория, по которой будет выбираться подменю с различной информацией о приюте
     * @return возвращает пару из флага о том есть ли инфорация или нет и
     * клавиатуру по информации о приюте в аргументах метода
     */
    Pair<Boolean, Keyboard> getAnswerMenu(Shelter shelter, Category category);

    /**
     * @param shelter выбранный пользователем приют
     * @return возвращаем кнопки по информации о том как взять питомца из приюта в аргументах метода
     */
    Keyboard getTakePetInformationMenu(Shelter shelter);

    /**
     * @param shelter выбранный пользователем приют
     * @return Кнопка "назад" при выборе отправки отчета
     */
    Keyboard backFromReport(Shelter shelter);

}

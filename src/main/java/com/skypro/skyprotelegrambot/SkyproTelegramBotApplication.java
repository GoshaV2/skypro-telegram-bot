package com.skypro.skyprotelegrambot;

import com.skypro.skyprotelegrambot.entity.Answer;
import com.skypro.skyprotelegrambot.entity.Category;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.repository.AnswerRepository;
import com.skypro.skyprotelegrambot.repository.ShelterRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class SkyproTelegramBotApplication {

    private final AnswerRepository answerRepository;
    private final ShelterRepository shelterRepository;

    public SkyproTelegramBotApplication(AnswerRepository answerRepository, ShelterRepository shelterRepository) {
        this.answerRepository = answerRepository;
        this.shelterRepository = shelterRepository;
        addTestData();
    }

    public static void main(String[] args) {
        SpringApplication.run(SkyproTelegramBotApplication.class, args);
    }

    private void addTestData(){
        Shelter shelter=shelterRepository.findShelterById(1L);
        if(shelter==null){
            shelter=new Shelter();
            shelter.setName("Первый");
            shelterRepository.save(shelter);
        }
        if(!answerRepository.existsById(1L)){
            Answer answer=new Answer();
            answer.setCategory(Category.INFORMATION);
            answer.setCommand("/taskAboutShelter");
            answer.setShelter(shelter);
            answer.setTitle("Получить информацию о приюте");
            answer.setText("Наш приют самый лучший");
            answerRepository.save(answer);
        }
        if(!answerRepository.existsById(2L)){
            Answer answer=new Answer();
            answer.setCategory(Category.GETTING_ANIMAL);
            answer.setCommand("/taskAboutGetting");
            answer.setShelter(shelter);
            answer.setTitle("Как получить  животное");
            answer.setText("Вот так ...");
            answerRepository.save(answer);
        }
    }

}

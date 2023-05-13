package com.skypro.skyprotelegrambot.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Probation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    private Shelter shelter;
    @ManyToOne
    private User user;
    private String petName;
    private LocalDate startDate;
    private int countProbationDays; //количество дней испытательного срока
}

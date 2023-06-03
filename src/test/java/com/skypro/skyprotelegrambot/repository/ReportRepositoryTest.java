package com.skypro.skyprotelegrambot.repository;

import com.skypro.skyprotelegrambot.model.OverdueDayData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Sql(scripts = {"/script/clear-all-data.sql"})
@Sql(scripts = "/script/test-data-for-reportRepository.sql")
class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepository;

    @Test
    void getDataOfOverdueDays_whenListSizeIsTwo() {
        LocalDate localDate = LocalDate.of(2022, 1, 16);
        List<OverdueDayData> overdueDayDataList = reportRepository.getDataOfOverdueDays(localDate);
        assertEquals(2, overdueDayDataList.size());

        OverdueDayData overdueDayData1 = overdueDayDataList.get(0);
        long overdueDays1 = ChronoUnit.DAYS.between(overdueDayData1.getLastLoadDate(), localDate);
        assertEquals(overdueDays1, 2);

        OverdueDayData overdueDayData2 = overdueDayDataList.get(1);
        long overdueDays2 = ChronoUnit.DAYS.between(overdueDayData2.getLastLoadDate(), localDate);
        assertEquals(overdueDays2, 1);
    }
}
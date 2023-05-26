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
@Sql(scripts = "/script/test-data-report.sql")
class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepository;

    @Test
    void getDataOfOverdueDays_whenDataListSizeIsOne() {
        LocalDate localDate = LocalDate.of(2022, 1, 16);
        List<OverdueDayData> overdueDayDataList = reportRepository.getDataOfOverdueDays(localDate);
        assertEquals(1, overdueDayDataList.size());
        OverdueDayData overdueDayData = overdueDayDataList.get(0);
        long overdueDays = ChronoUnit.DAYS.between(overdueDayData.getLastLoadDate(), localDate);
        assertEquals(overdueDays, 2);
    }
    @Test
    void getDataOfOverdueDays_whenDataListSizeIsMoreThanOne() {
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
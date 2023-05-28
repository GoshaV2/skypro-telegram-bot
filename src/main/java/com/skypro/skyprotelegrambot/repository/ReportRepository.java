package com.skypro.skyprotelegrambot.repository;

import com.skypro.skyprotelegrambot.entity.Probation;
import com.skypro.skyprotelegrambot.entity.Report;
import com.skypro.skyprotelegrambot.model.OverdueDayData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findById(Long id);

    List<Report> findAllByProbation(Probation probation);

    List<Report> findAllByDateAndProbation(LocalDate date, Probation probation);


    @Query("select new com.skypro.skyprotelegrambot.model.OverdueDayData(max(r.date),r.probation)" +
            "from Report r where r.probation.probationStatus='APPOINTED' group by r.probation having max(r.date)<:to")
    List<OverdueDayData> getDataOfOverdueDays(@Param("to") LocalDate to);
}

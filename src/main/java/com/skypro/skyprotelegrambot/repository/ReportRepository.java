package com.skypro.skyprotelegrambot.repository;

import com.skypro.skyprotelegrambot.entity.Report;
import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;
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

    List<Report> findAllByUserAndShelter(User user, Shelter shelter);

    @Query("select new com.skypro.skyprotelegrambot.model.OverdueDayData(max(r.date),r.user,r.shelter)" +
            "from Report r group by r.shelter,r.user having max(r.date)<:to")
    List<OverdueDayData> getDataOfOverdueDays(@Param("to") LocalDate to);
}

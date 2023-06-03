package com.skypro.skyprotelegrambot.repository;

import com.skypro.skyprotelegrambot.entity.Shelter;
import com.skypro.skyprotelegrambot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {

    Optional<Shelter> findShelterById(Long id);

    Optional<Shelter> findByName(String name);

    @Query("select case when (count(s) > 0) then true else false end " +
            "from Shelter s where s=:shelter and :user member s.userSet")
    boolean hasUser(@Param("user") User user, @Param("shelter") Shelter shelter);
}

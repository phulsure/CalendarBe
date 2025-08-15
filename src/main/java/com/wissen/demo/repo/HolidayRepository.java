package com.wissen.demo.repo;

import com.wissen.demo.domain.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    @Query("SELECT h FROM Holiday h WHERE h.country = :country AND h.date BETWEEN :startDate AND :endDate")
    List<Holiday> findByCountryAndDateBetween(
            @Param("country") String country,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT DISTINCT h.country FROM Holiday h ORDER BY h.country")
    List<String> findAllCountries();
}
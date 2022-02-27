package com.example.cbrcurrency.repository;

import com.example.cbrcurrency.entity.CalendarDateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

public interface CalendarDateEntityRepository extends JpaRepository<CalendarDateEntity, Long> {
    Optional<CalendarDateEntity> findByDate(Calendar date);
}
package com.example.cbrcurrency.repository;

import com.example.cbrcurrency.entity.CalendarDateEntity;
import com.example.cbrcurrency.entity.CurrencyEntity;
import com.example.cbrcurrency.entity.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RateEntityRepository extends JpaRepository<RateEntity, Long> {
    Optional<RateEntity> findByCurrencyEntityAndAndCalendarDateEntity(CurrencyEntity entity, CalendarDateEntity calendarDateEntity);
}
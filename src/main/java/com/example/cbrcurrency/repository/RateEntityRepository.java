package com.example.cbrcurrency.repository;

import com.example.cbrcurrency.entity.CalendarDateEntity;
import com.example.cbrcurrency.entity.CurrencyEntity;
import com.example.cbrcurrency.entity.RateEntity;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RepositoryRestResource
@Repository
public interface RateEntityRepository extends BaseJpaRepository<RateEntity, Long> {
    Optional<RateEntity> findByCurrencyEntityAndCalendarDateEntity(CurrencyEntity entity, CalendarDateEntity calendarDateEntity);
}
package com.example.cbrcurrency.repository;

import com.example.cbrcurrency.entity.CalendarDateEntity;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Calendar;
import java.util.Optional;

@RepositoryRestResource
public interface CalendarDateEntityRepository extends BaseJpaRepository<CalendarDateEntity, Long> {
    Optional<CalendarDateEntity> findByDate(Calendar date);
}
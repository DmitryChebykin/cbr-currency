package com.example.cbrcurrency.repository;

import com.example.cbrcurrency.entity.CalendarDateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

@RepositoryRestResource
 public interface CalendarDateEntityRepository extends BaseJpaRepository<CalendarDateEntity, Long> {
    Optional<CalendarDateEntity> findByDate(Calendar date);
}
package com.example.cbrcurrency.repository;

import com.example.cbrcurrency.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyEntityRepository extends JpaRepository<CurrencyEntity, Long> {
    Optional<CurrencyEntity> findByValuteId(String valuteId);

    Optional<CurrencyEntity> findByName(String name);
}
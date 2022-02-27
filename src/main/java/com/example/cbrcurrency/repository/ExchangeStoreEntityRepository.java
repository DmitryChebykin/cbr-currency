package com.example.cbrcurrency.repository;

import com.example.cbrcurrency.entity.ExchangeStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeStoreEntityRepository extends JpaRepository<ExchangeStoreEntity, Long> {
}
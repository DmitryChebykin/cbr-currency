package com.example.cbrcurrency.repository;

import com.example.cbrcurrency.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@RepositoryRestResource
 public interface CurrencyEntityRepository extends BaseJpaRepository<CurrencyEntity, Long> {
    Optional<CurrencyEntity> findByValuteId(String valuteId);

    Optional<CurrencyEntity> findByName(String name);
}
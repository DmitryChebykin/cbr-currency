package com.example.cbrcurrency.repository;

import com.example.cbrcurrency.entity.ExchangeStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface ExchangeStoreEntityRepository extends BaseJpaRepository<ExchangeStoreEntity, Long> {
}
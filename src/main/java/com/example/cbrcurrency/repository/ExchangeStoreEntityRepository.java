package com.example.cbrcurrency.repository;

import com.example.cbrcurrency.entity.ExchangeStoreEntity;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ExchangeStoreEntityRepository extends BaseJpaRepository<ExchangeStoreEntity, Long> {
}
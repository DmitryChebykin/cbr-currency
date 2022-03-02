package com.example.cbrcurrency.service;

import com.example.cbrcurrency.entity.ExchangeStoreEntity;
import com.example.cbrcurrency.repository.ExchangeStoreEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExchangeStoreEntityService {
    private final ExchangeStoreEntityRepository exchangeStoreEntityRepository;

    @Transactional
    public void save(ExchangeStoreEntity exchangeStoreEntity) {
        exchangeStoreEntityRepository.save(exchangeStoreEntity);
    }

    @Transactional
    public void saveByQueryDsl(ExchangeStoreEntity exchangeStoreEntity) {
        exchangeStoreEntityRepository.save(exchangeStoreEntity);
    }
}

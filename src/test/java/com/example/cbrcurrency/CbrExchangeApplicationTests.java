package com.example.cbrcurrency;

import com.example.cbrcurrency.entity.*;
import com.example.cbrcurrency.repository.QueryDslRepository;
import com.example.cbrcurrency.repository.RateEntityRepository;
import com.example.cbrcurrency.service.CurrencyInfoDbSaverService;
import com.example.cbrcurrency.service.ExchangeService;
import com.example.cbrcurrency.util.QueryDslUtils;
import com.querydsl.core.types.EntityPath;
import com.querydsl.sql.RelationalPath;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.dml.SQLInsertClause;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.stream.IntStream;

@SpringBootTest
class CbrExchangeApplicationTests {
    @Autowired
    private RateEntityRepository rateEntityRepository;
    @Autowired
    private QueryDslRepository queryDslRepository;

    @Autowired
    private SQLQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CurrencyInfoDbSaverService currencyInfoDbSaverService;

    @Autowired
    private ExchangeService exchangeService;

    @Test
    void contextLoads() {
        currencyInfoDbSaverService.saveValutaBean();
        IntStream.range(0, 100).forEach(e -> currencyInfoDbSaverService.saveRatesAtDay(LocalDate.now().minusDays(e)));
        IntStream.range(99, 110).forEach(e -> currencyInfoDbSaverService.saveRatesAtDay(LocalDate.now().minusDays(e)));

        BigDecimal exchange = exchangeService.exchange("Евро", "Доллар США", BigDecimal.valueOf(100));

        IntStream.range(10, 200)
                .forEach(e -> exchangeService.exchange("Евро", "Доллар США", BigDecimal.valueOf(e)));

        CurrencyEntity source = CurrencyEntity.builder().build();
        source.setId(14L);
        CurrencyEntity dest = CurrencyEntity.builder().build();
        dest.setId(15L);
        ExchangeStoreEntity exchangeStoreEntity = ExchangeStoreEntity.builder()
                .rate(BigDecimal.valueOf(123))
                .amount(BigDecimal.valueOf(223))
                .sourceId(source)
                .destinationId(dest)
                .build();

        long l = queryDslRepository.insertExchangeStoreEntity(exchangeStoreEntity);

        IntStream.range(1, 12)
                .forEach(e -> queryDslRepository.insertExchangeStoreEntity(exchangeStoreEntity));
                        System.out.println(l);
    }
}
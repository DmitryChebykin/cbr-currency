package com.example.cbrcurrency;

import com.example.cbrcurrency.dto.CurrencyExchangeDto;
import com.example.cbrcurrency.dto.PeriodStatisticDto;
import com.example.cbrcurrency.service.QueryDslService;
import com.graphql.spring.boot.test.GraphQLTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@GraphQLTest
@SpringBootTest
class CbrExchangeApplicationTests {
//    @Autowired
//    private RateEntityRepository rateEntityRepository;

    @Autowired
    private QueryDslService queryDslService;

//    @Autowired
//    private SQLQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager entityManager;

//    @Autowired
//    private CurrencyInfoDbSaverService currencyInfoDbSaverService;
//
//    @Autowired
//    private ExchangeService exchangeService;

    @Test
    void contextLoads() {
//        currencyInfoDbSaverService.saveValutaBean();
//        IntStream.range(0, 100).forEach(e -> currencyInfoDbSaverService.saveRatesAtDay(LocalDate.now().minusDays(e)));
//        IntStream.range(99, 110).forEach(e -> currencyInfoDbSaverService.saveRatesAtDay(LocalDate.now().minusDays(e)));
//
//        BigDecimal exchange = exchangeService.exchange("Евро", "Доллар США", BigDecimal.valueOf(100));
//
//        IntStream.range(10, 200)
//                .forEach(e -> exchangeService.exchange("Евро", "Доллар США", BigDecimal.valueOf(e)));
//
//        CurrencyEntity source = CurrencyEntity.builder().build();
//        source.setId(14L);
//        CurrencyEntity dest = CurrencyEntity.builder().build();
//        dest.setId(15L);
//        ExchangeStoreEntity exchangeStoreEntity = ExchangeStoreEntity.builder()
//                .rate(BigDecimal.valueOf(123))
//                .amount(BigDecimal.valueOf(223))
//                .sourceId(source)
//                .destinationId(dest)
//                .build();
//
//        long l = queryDslRepository.insertExchangeStoreEntity(exchangeStoreEntity);

//        IntStream.range(1, 12)
//                .forEach(e -> queryDslRepository.insertExchangeStoreEntity(exchangeStoreEntity));
//                        System.out.println(l);

        List<CurrencyExchangeDto> currencyExchangeDto = queryDslService.getExchangesOfCurrencyPairFromDaysAgo(14L, 15L, 7);

        List resultList = entityManager.createNativeQuery("select * from currency").getResultList();

        System.out.println(resultList);

        PeriodStatisticDto averageRateAndTotalAmountForLastDays = queryDslService.getAverageRateAndTotalAmountForLastDays(14l, 15L, 6);

        System.out.println(averageRateAndTotalAmountForLastDays);
    }
}
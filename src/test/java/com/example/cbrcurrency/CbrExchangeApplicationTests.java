package com.example.cbrcurrency;

import com.example.cbrcurrency.repository.RateEntityRepository;
import com.example.cbrcurrency.service.CurrencyInfoDbSaverService;
import com.example.cbrcurrency.service.ExchangeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
class CbrExchangeApplicationTests {
    @Autowired
    RateEntityRepository rateEntityRepository;

    @Autowired
    private CurrencyInfoDbSaverService currencyInfoDbSaverService;

    @Autowired
    private ExchangeService exchangeService;

    @Test
    void contextLoads() {
        currencyInfoDbSaverService.saveValutaBean();
        IntStream.range(0, 100).forEach(e -> currencyInfoDbSaverService.saveRatesAtDay(LocalDate.now().minusDays(e)));
        IntStream.range(80, 110).forEach(e -> currencyInfoDbSaverService.saveRatesAtDay(LocalDate.now().minusDays(e)));
        BigDecimal exchange = exchangeService.exchange("Евро", "Доллар США", BigDecimal.valueOf(100.34));
        exchange = exchangeService.exchange("Евро", "Доллар США", BigDecimal.valueOf(100.34));
        IntStream.range(10, 100)
                .forEach(e -> exchangeService.exchange("Евро", "Доллар США", BigDecimal.valueOf(e)));

        System.out.println(exchange);
    }
}
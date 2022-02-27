package com.example.cbrcurrency.service;

import com.example.cbrcurrency.entity.CalendarDateEntity;
import com.example.cbrcurrency.entity.CurrencyEntity;
import com.example.cbrcurrency.entity.ExchangeStoreEntity;
import com.example.cbrcurrency.entity.RateEntity;
import com.example.cbrcurrency.repository.CalendarDateEntityRepository;
import com.example.cbrcurrency.repository.CurrencyEntityRepository;
import com.example.cbrcurrency.repository.ExchangeStoreEntityRepository;
import com.example.cbrcurrency.repository.RateEntityRepository;
import com.example.cbrcurrency.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final CurrencyEntityRepository currencyEntityRepository;

    private final RateEntityRepository rateEntityRepository;

    private final CalendarDateEntityRepository calendarDateEntityRepository;

    private final ExchangeStoreEntityRepository exchangeStoreEntityRepository;

    @Transactional
    public BigDecimal exchange(CurrencyEntity source, CurrencyEntity dest, BigDecimal amount) {
        Calendar today = Util.getCalendar(LocalDate.now());
        Optional<CalendarDateEntity> byDate = calendarDateEntityRepository.findByDate(today);
        CalendarDateEntity calendarDateEntity = byDate.get();
        Optional<RateEntity> optSource = rateEntityRepository.findByCurrencyEntityAndAndCalendarDateEntity(source, calendarDateEntity);
        Optional<RateEntity> optDest = rateEntityRepository.findByCurrencyEntityAndAndCalendarDateEntity(dest, calendarDateEntity);

        RateEntity sourceRate = optSource.get();
        RateEntity destRate = optDest.get();

        BigDecimal srcBgDcml = sourceRate.getCurrencyRate();
        BigDecimal dstBgDcml = destRate.getCurrencyRate();

        BigDecimal divide = srcBgDcml.divide(dstBgDcml, BigDecimal.ROUND_HALF_UP);
        BigDecimal multiply = divide.multiply(amount);

        ExchangeStoreEntity exchangeStoreEntity = ExchangeStoreEntity.builder()
                .sourceCurrency(source)
                .destinationCurrency(dest)
                .fromAmount(amount)
                .conversionRate(divide)
                .build();
        exchangeStoreEntityRepository.save(exchangeStoreEntity);
        return multiply;
    }

    @Transactional
    public BigDecimal exchange(String source, String dest, BigDecimal amount) {
        Optional<CurrencyEntity> optSource = currencyEntityRepository.findByName(source);
        Optional<CurrencyEntity> optDest = currencyEntityRepository.findByName(dest);
        CurrencyEntity currencySource = optSource.get();
        CurrencyEntity currencyDest = optDest.get();
        return exchange(currencySource, currencyDest, amount);
    }
}

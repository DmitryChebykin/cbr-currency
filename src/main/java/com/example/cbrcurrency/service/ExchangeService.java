package com.example.cbrcurrency.service;

import com.example.cbrcurrency.aspect.Journal;
import com.example.cbrcurrency.entity.CalendarDateEntity;
import com.example.cbrcurrency.entity.CurrencyEntity;
import com.example.cbrcurrency.entity.RateEntity;
import com.example.cbrcurrency.repository.CalendarDateEntityRepository;
import com.example.cbrcurrency.repository.CurrencyEntityRepository;
import com.example.cbrcurrency.repository.RateEntityRepository;
import com.example.cbrcurrency.service.exception.CalendarDateNotFoundException;
import com.example.cbrcurrency.service.exception.CurrencyNotFoundException;
import com.example.cbrcurrency.service.exception.RateNotFoundException;
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

    @Transactional
    public BigDecimal exchange(CurrencyEntity source, CurrencyEntity dest, BigDecimal amount) {
        Calendar today = Util.getCalendar(LocalDate.now());

        Optional<CalendarDateEntity> optionalCalendarDateEntity = calendarDateEntityRepository.findByDate(today);

        CalendarDateEntity calendarDateEntity = optionalCalendarDateEntity.orElseThrow(CalendarDateNotFoundException::new);

        Optional<RateEntity> optSource = rateEntityRepository.findByCurrencyEntityAndCalendarDateEntity(source, calendarDateEntity);
        RateEntity sourceRate = optSource.orElseThrow(RateNotFoundException::new);

        BigDecimal srcBgDcml = sourceRate.getCurrencyRate();

        Optional<RateEntity> optDest = rateEntityRepository.findByCurrencyEntityAndCalendarDateEntity(dest, calendarDateEntity);
        RateEntity destRate = optDest.orElseThrow(RateNotFoundException::new);

        BigDecimal dstBgDcml = destRate.getCurrencyRate();

        BigDecimal divide = srcBgDcml.divide(dstBgDcml, BigDecimal.ROUND_HALF_UP);

        return divide.multiply(amount);
    }

    @Journal
    @Transactional
    public BigDecimal exchange(String source, String dest, BigDecimal amount) {
        Optional<CurrencyEntity> optSource = currencyEntityRepository.findByName(source);
        CurrencyEntity currencySource = optSource.orElseThrow(CurrencyNotFoundException::new);

        Optional<CurrencyEntity> optDest = currencyEntityRepository.findByName(dest);
        CurrencyEntity currencyDest = optDest.orElseThrow(CurrencyNotFoundException::new);

        return exchange(currencySource, currencyDest, amount);
    }
}

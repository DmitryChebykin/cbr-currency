package com.example.cbrcurrency.aspect;

import com.example.cbrcurrency.entity.ExchangeStoreEntity;
import com.example.cbrcurrency.repository.CurrencyEntityRepository;
import com.example.cbrcurrency.service.CurrencyInfoDbSaverService;
import com.example.cbrcurrency.service.ExchangeStoreService;
import com.example.cbrcurrency.service.exception.CurrencyNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;

@Aspect
@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeServiceHandler {
    private final CurrencyInfoDbSaverService currencyInfoDbSaverService;

    private final ExchangeStoreService exchangeStoreService;

    private final CurrencyEntityRepository currencyEntityRepository;

    private final Clock clock;

    @Pointcut("@annotation(Journal)")
    public void journalPointcut() {
        // TODO document why this method is empty
    }

    @Before("journalPointcut()")
    public void before(JoinPoint jp) {
        currencyInfoDbSaverService.saveRatesAtDay(LocalDate.now(clock));
    }

    @AfterReturning(pointcut = "journalPointcut()", returning = "result")
    public void docAfterReturning(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();

        BigDecimal amount = (BigDecimal) args[2];
        ExchangeStoreEntity exchangeStoreEntity = ExchangeStoreEntity.builder()
                .sourceId(currencyEntityRepository.findByName((String) args[0]).orElseThrow(CurrencyNotFoundException::new))
                .destinationId(currencyEntityRepository.findByName((String) args[1]).orElseThrow(CurrencyNotFoundException::new))
                .amount(amount)
                .rate(((BigDecimal) result).divide(amount, BigDecimal.ROUND_HALF_UP))
                .build();

        log.info(String.format("Trying save exchangeStoreEntity : %s", exchangeStoreEntity.toString()));

        exchangeStoreService.save(exchangeStoreEntity);
    }
}

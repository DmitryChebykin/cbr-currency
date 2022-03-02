package com.example.cbrcurrency.aspect;

import com.example.cbrcurrency.entity.ExchangeStoreEntity;
import com.example.cbrcurrency.repository.CurrencyEntityRepository;
import com.example.cbrcurrency.service.ExchangeStoreEntityService;
import com.example.cbrcurrency.service.exception.CurrencyNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ExchangeServiceHandler {

    private final ExchangeStoreEntityService exchangeStoreEntityService;

    private final CurrencyEntityRepository currencyEntityRepository;

    @Pointcut("@annotation(Journal)")
    public void journalPointcut() {
    }

    @AfterReturning(pointcut = "journalPointcut()", returning = "result")
    public void docAfterReturning(JoinPoint joinPoint, Object result) throws Throwable {
        Object[] args = joinPoint.getArgs();

        BigDecimal amount = (BigDecimal) args[2];
        ExchangeStoreEntity exchangeStoreEntity = ExchangeStoreEntity.builder()
                .sourceId(currencyEntityRepository.findByName((String) args[0]).orElseThrow(CurrencyNotFoundException::new))
                .destinationId(currencyEntityRepository.findByName((String) args[1]).orElseThrow(CurrencyNotFoundException::new))
                .amount(amount)
                .rate(((BigDecimal) result).divide(amount, BigDecimal.ROUND_HALF_UP))
                .build();

        log.info(String.format("Trying save exchangeStoreEntity : %s", exchangeStoreEntity.toString()));

        exchangeStoreEntityService.save(exchangeStoreEntity);
    }
}

package com.example.cbrcurrency.service;

import com.example.cbrcurrency.dto.CurrencyExchangeDto;
import com.example.cbrcurrency.dto.ExchangeGraphQlDto;
import com.example.cbrcurrency.dto.PeriodStatisticDto;
import com.example.cbrcurrency.entity.ExchangeStoreEntity;
import com.example.cbrcurrency.entity.QCurrencyEntity;
import com.example.cbrcurrency.entity.QExchangeStoreEntity;
import com.example.cbrcurrency.util.QueryDslUtils;
import com.example.cbrcurrency.util.Util;
import com.querydsl.core.support.FetchableQueryBase;
import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.sql.RelationalPath;
import com.querydsl.sql.SQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

@Service
@RequiredArgsConstructor
public class QueryDslService {

    public static final QCurrencyEntity Q_CURRENCY_ENTITY = QCurrencyEntity.currencyEntity;

    private static final QExchangeStoreEntity Q_EXCHANGE_STORE_ENTITY = QExchangeStoreEntity.exchangeStoreEntity;

    private final SQLQueryFactory queryFactory;

    private final JPAQuery<?> jpaQuery;

    private final Clock clock;

    @Transactional
    public long insertExchangeStoreEntity(ExchangeStoreEntity exchangeStoreEntity) {

        RelationalPath<ExchangeStoreEntity> storeEntityRelationalPath = QueryDslUtils.asRelational(Q_EXCHANGE_STORE_ENTITY);

        Calendar calendar = Util.getCalendar(LocalDate.now(clock));
        return queryFactory.insert(storeEntityRelationalPath)
                .columns(Q_EXCHANGE_STORE_ENTITY.sourceId, Q_EXCHANGE_STORE_ENTITY.destinationId)
                .columns(Q_EXCHANGE_STORE_ENTITY.rate, Q_EXCHANGE_STORE_ENTITY.amount)
                .columns(Q_EXCHANGE_STORE_ENTITY.createAt, Q_EXCHANGE_STORE_ENTITY.modifiedAt)
                .values(exchangeStoreEntity.getSourceId().getId(), exchangeStoreEntity.getDestinationId().getId())
                .values(exchangeStoreEntity.getRate(), exchangeStoreEntity.getAmount())
                .values(calendar, calendar).execute();
    }

    @Transactional(readOnly = true)
    public List<CurrencyExchangeDto> getExchangesOfCurrencyPairFromDaysAgo(Long sourceId, Long destId, int fromDaysAgo) {
        RelationalPath<ExchangeStoreEntity> storeEntityRelationalPath = QueryDslUtils.asRelational(Q_EXCHANGE_STORE_ENTITY);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -fromDaysAgo);

        QueryBase<?> queryBase = jpaQuery.select(Projections.constructor(CurrencyExchangeDto.class,
                Q_EXCHANGE_STORE_ENTITY.rate,
                Q_EXCHANGE_STORE_ENTITY.amount,
                Q_EXCHANGE_STORE_ENTITY.sourceId.id,
                Q_EXCHANGE_STORE_ENTITY.destinationId.id))
                .from(storeEntityRelationalPath)
                .where((Q_EXCHANGE_STORE_ENTITY.destinationId.id.eq(sourceId).and(Q_EXCHANGE_STORE_ENTITY.sourceId.id.eq(destId)))
                        .or(Q_EXCHANGE_STORE_ENTITY.destinationId.id.eq(destId).and(Q_EXCHANGE_STORE_ENTITY.sourceId.id.eq(sourceId))))
                .where(Q_EXCHANGE_STORE_ENTITY.createAt.after(calendar));

        FetchableQueryBase fetchable = (FetchableQueryBase) queryBase;

        return fetchable.fetch();
    }

    @Transactional(readOnly = true)
    public PeriodStatisticDto getAverageRateAndTotalAmountForLastDays(Long sourceId, Long destId, int fromDaysAgo) {
        List<CurrencyExchangeDto> statisticExchangeDtoList = getExchangesOfCurrencyPairFromDaysAgo(sourceId, destId, fromDaysAgo);

        Map<Long, BigDecimal> totalAmountConvertedMap = statisticExchangeDtoList.stream()
                .collect(groupingBy(
                        CurrencyExchangeDto::getSourceId, reducing(BigDecimal.ZERO, e -> e.getAmount().multiply(e.getRate()), BigDecimal::add)));

        Map<Long, BigDecimal> totalAmountMap = statisticExchangeDtoList.stream()
                .collect(groupingBy(
                        CurrencyExchangeDto::getSourceId, reducing(BigDecimal.ZERO, CurrencyExchangeDto::getAmount, BigDecimal::add)));

        BigDecimal totalSourceAmount = totalAmountMap.get(sourceId);

        BigDecimal totalDestination = totalAmountConvertedMap.get(sourceId);
        BigDecimal averageRate = totalDestination.divide(totalSourceAmount, BigDecimal.ROUND_HALF_UP);

        return PeriodStatisticDto.builder().averageRate(averageRate).amount(totalSourceAmount).build();
    }

    @Transactional(readOnly = true)
    public List<ExchangeGraphQlDto> getExchangesDtoBetweenDates(Calendar dateFrom, Calendar dateTo) {
        RelationalPath<ExchangeStoreEntity> storeEntityRelationalPath = QueryDslUtils.asRelational(Q_EXCHANGE_STORE_ENTITY);

        QCurrencyEntity fromCharCode = new QCurrencyEntity("fromCharCode");
        QCurrencyEntity toCharCode = new QCurrencyEntity("toCharCode");

        QueryBase<?> queryBase = jpaQuery.select(Projections.constructor(ExchangeGraphQlDto.class,
                Q_EXCHANGE_STORE_ENTITY.createAt.as("date"),
                Q_EXCHANGE_STORE_ENTITY.amount,
                Q_EXCHANGE_STORE_ENTITY.rate,
                fromCharCode.isoCharCode,
                toCharCode.isoCharCode
        ))
                .from(storeEntityRelationalPath)
                .leftJoin(fromCharCode).on(fromCharCode.id.eq(Q_EXCHANGE_STORE_ENTITY.sourceId.id))
                .leftJoin(toCharCode).on(toCharCode.id.eq(Q_EXCHANGE_STORE_ENTITY.destinationId.id))
                .where(Q_EXCHANGE_STORE_ENTITY.createAt.between(dateFrom, dateTo));

        FetchableQueryBase fetchable = (FetchableQueryBase) queryBase;

        return fetchable.fetch();
    }
}

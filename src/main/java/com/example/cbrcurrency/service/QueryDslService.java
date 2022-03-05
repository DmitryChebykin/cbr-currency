package com.example.cbrcurrency.service;

import com.example.cbrcurrency.dto.CurrencyExchangeDto;
import com.example.cbrcurrency.entity.ExchangeStoreEntity;
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
import java.math.MathContext;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

@Service
@RequiredArgsConstructor
public class QueryDslService {
    private static final QExchangeStoreEntity qExchangeStoreEntity = QExchangeStoreEntity.exchangeStoreEntity;

    private final SQLQueryFactory queryFactory;

    private final JPAQuery<?> jpaQuery;

    @Transactional
    public long insertExchangeStoreEntity(ExchangeStoreEntity exchangeStoreEntity) {

        RelationalPath<ExchangeStoreEntity> storeEntityRelationalPath = QueryDslUtils.asRelational(qExchangeStoreEntity);

        Calendar calendar = Util.getCalendar(LocalDate.now());
        return queryFactory.insert(storeEntityRelationalPath)
                .columns(qExchangeStoreEntity.sourceId, qExchangeStoreEntity.destinationId)
                .columns(qExchangeStoreEntity.rate, qExchangeStoreEntity.amount)
                .columns(qExchangeStoreEntity.createAt, qExchangeStoreEntity.modifiedAt)
                .values(exchangeStoreEntity.getSourceId().getId(), exchangeStoreEntity.getDestinationId().getId())
                .values(exchangeStoreEntity.getRate(), exchangeStoreEntity.getAmount())
                .values(calendar, calendar).execute();
    }

    @Transactional(readOnly = true)
    public List<CurrencyExchangeDto> getStatisticExchangeDtoList(Long sourceId, Long destId, int fromDaysAgo) {
        RelationalPath<ExchangeStoreEntity> storeEntityRelationalPath = QueryDslUtils.asRelational(qExchangeStoreEntity);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -fromDaysAgo);

        QueryBase<?> queryBase = jpaQuery.select(Projections.constructor(CurrencyExchangeDto.class,
                qExchangeStoreEntity.rate,
                qExchangeStoreEntity.amount,
                qExchangeStoreEntity.sourceId.id,
                qExchangeStoreEntity.destinationId.id))
                .from(storeEntityRelationalPath)
                .where((qExchangeStoreEntity.destinationId.id.eq(sourceId).and(qExchangeStoreEntity.sourceId.id.eq(destId)))
                        .or(qExchangeStoreEntity.destinationId.id.eq(destId).and(qExchangeStoreEntity.sourceId.id.eq(sourceId))))
                .where(qExchangeStoreEntity.createAt.after(calendar));

        FetchableQueryBase fetchable = (FetchableQueryBase) queryBase;

        return (List<CurrencyExchangeDto>) fetchable.fetch();
    }

    @Transactional(readOnly = true)
    public PeriodStatisticDto getAverageRateAndTotalAmountForLastDays(Long sourceId, Long destId, int fromDaysAgo) {
        List<CurrencyExchangeDto> statisticExchangeDtoList = getStatisticExchangeDtoList(sourceId, destId, fromDaysAgo);

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
}

package com.example.cbrcurrency.repository;

import com.example.cbrcurrency.entity.ExchangeStoreEntity;
import com.example.cbrcurrency.entity.QExchangeStoreEntity;
import com.example.cbrcurrency.util.QueryDslUtils;
import com.example.cbrcurrency.util.Util;
import com.querydsl.core.types.EntityPath;
import com.querydsl.sql.RelationalPath;
import com.querydsl.sql.SQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;

@Repository
@RequiredArgsConstructor
public class QueryDslRepository {
    private final SQLQueryFactory queryFactory;
@Transactional
    public long
    insertExchangeStoreEntity(ExchangeStoreEntity exchangeStoreEntity){

        QExchangeStoreEntity qExchangeStoreEntity = QExchangeStoreEntity.exchangeStoreEntity;
        EntityPath<ExchangeStoreEntity> myEntity = qExchangeStoreEntity;
        RelationalPath<ExchangeStoreEntity> storeEntityRelationalPath = QueryDslUtils.asRelational(myEntity);

        Calendar calendar = Util.getCalendar(LocalDate.now());
        return queryFactory.insert(storeEntityRelationalPath)
                .columns(qExchangeStoreEntity.sourceId, qExchangeStoreEntity.destinationId)
                .columns(qExchangeStoreEntity.rate, qExchangeStoreEntity.amount)
                .columns(qExchangeStoreEntity.createAt, qExchangeStoreEntity.modifiedAt)
                .values(exchangeStoreEntity.getSourceId().getId(), exchangeStoreEntity.getDestinationId().getId())
                .values(exchangeStoreEntity.getRate(), exchangeStoreEntity.getAmount())
                .values(calendar, calendar).execute();

    }
}

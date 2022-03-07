package com.example.cbrcurrency.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Builder
public class CurrencyExchangeDto {
    private final BigDecimal amount;

    private final BigDecimal rate;

    private final Long sourceId;

    private final Long destinationId;

    @QueryProjection
    public CurrencyExchangeDto(BigDecimal rate, BigDecimal amount, Long sourceId, Long destinationId) {
        this.amount = amount;
        this.rate = rate;
        this.sourceId = sourceId;
        this.destinationId = destinationId;
    }
}

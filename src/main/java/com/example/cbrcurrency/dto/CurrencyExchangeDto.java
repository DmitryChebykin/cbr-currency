package com.example.cbrcurrency.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class CurrencyExchangeDto {
    private BigDecimal amount;

    private BigDecimal rate;

    private Long sourceId;

    private Long destinationId;

    @QueryProjection
    public CurrencyExchangeDto(BigDecimal rate, BigDecimal amount, Long sourceId, Long destinationId) {
        this.amount = amount;
        this.rate = rate;
        this.sourceId = sourceId;
        this.destinationId = destinationId;
    }
}

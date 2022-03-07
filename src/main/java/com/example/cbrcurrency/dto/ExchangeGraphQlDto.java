package com.example.cbrcurrency.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Calendar;


@Getter
@Builder
public class ExchangeGraphQlDto {
    private final Calendar date;

    private final BigDecimal amount;

    private final BigDecimal rate;

    private final String fromCharCode;

    private final String toCharCode;

    @QueryProjection
    public ExchangeGraphQlDto(Calendar date, BigDecimal amount, BigDecimal rate, String fromCharCode, String toCharCode) {
        this.date = date;
        this.amount = amount;
        this.rate = rate;
        this.fromCharCode = fromCharCode;
        this.toCharCode = toCharCode;
    }
}

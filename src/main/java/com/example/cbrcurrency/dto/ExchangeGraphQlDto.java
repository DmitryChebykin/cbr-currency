package com.example.cbrcurrency.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Calendar;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class ExchangeGraphQlDto {
    private Calendar date;

    private BigDecimal amount;

    private BigDecimal rate;

    private String fromCharCode;

    private String toCharCode;

    @QueryProjection
    public ExchangeGraphQlDto(Calendar date, BigDecimal amount, BigDecimal rate, String fromCharCode, String toCharCode) {
        this.date = date;
        this.amount = amount;
        this.rate = rate;
        this.fromCharCode = fromCharCode;
        this.toCharCode = toCharCode;
    }
}

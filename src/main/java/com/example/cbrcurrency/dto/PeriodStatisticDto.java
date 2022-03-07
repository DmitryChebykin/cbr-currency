package com.example.cbrcurrency.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class PeriodStatisticDto {
    private final BigDecimal amount;

    private final BigDecimal averageRate;
}

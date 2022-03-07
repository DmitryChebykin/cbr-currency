package com.example.cbrcurrency.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeriodStatisticDto {
    private BigDecimal amount;

    private BigDecimal averageRate;
}

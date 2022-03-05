package com.example.cbrcurrency.service;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PeriodStatisticDto {
    private BigDecimal amount;

    private BigDecimal averageRate;
}

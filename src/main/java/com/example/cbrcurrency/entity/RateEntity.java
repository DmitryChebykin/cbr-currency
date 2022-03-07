package com.example.cbrcurrency.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "rates")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateEntity extends BaseEntity<Long> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_date_id")
    private CalendarDateEntity calendarDateEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    private CurrencyEntity currencyEntity;

    @Column(name = "rate", precision = 20, scale = 4)
    private BigDecimal currencyRate;
}
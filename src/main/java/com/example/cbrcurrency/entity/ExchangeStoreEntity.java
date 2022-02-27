package com.example.cbrcurrency.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "exchanges")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeStoreEntity extends BaseEntity<Long> {
    @ManyToOne
    @JoinColumn(name = "source_id")
    private CurrencyEntity sourceCurrency;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private CurrencyEntity destinationCurrency;

    @Column(name = "amount", precision = 9, scale = 4)
    private BigDecimal fromAmount;

    @Column(name = "rate", precision = 9, scale = 4)
    private BigDecimal conversionRate;
}
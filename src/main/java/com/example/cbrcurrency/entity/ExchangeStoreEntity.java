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
    @JoinColumn(name = "sourceid")
    private CurrencyEntity sourceId;

    @ManyToOne
    @JoinColumn(name = "destinationid")
    private CurrencyEntity destinationId;

    @Column(name = "amount", precision = 20, scale = 4)
    private BigDecimal amount;

    @Column(name = "rate", precision = 20, scale = 4)
    private BigDecimal rate;
}
package com.example.cbrcurrency.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        ExchangeStoreEntity that = (ExchangeStoreEntity) o;

        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return 743726896;
    }
}
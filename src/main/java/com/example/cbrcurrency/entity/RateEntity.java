package com.example.cbrcurrency.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        RateEntity that = (RateEntity) o;

        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return 860094044;
    }
}
package com.example.cbrcurrency.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "currency")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyEntity extends BaseEntity<Long> {
    @Column(name = "valute_id", unique = true)
    private String valuteId;

    @Column(name = "eng_name")
    private String engName;

    @Column(name = "currency_name")
    private String name;

    @Column(name = "nominal")
    private String nominal;

    @Column(name = "parent_code")
    private String parentCode;

    @Column(name = "iso_num_code")
    private String isoNumCode;

    @Column(name = "iso_char_code")
    private String isoCharCode;

    @OneToMany(mappedBy = "currencyEntity")
    private List<RateEntity> rateEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        CurrencyEntity that = (CurrencyEntity) o;

        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return 835414062;
    }
}

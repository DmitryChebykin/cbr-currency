package com.example.cbrcurrency.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "currency")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyEntity extends BaseEntity<Long> {
    @Column(name = "valute_id", unique = true)
    String valuteId;

    @Column(name = "eng_name")
    String engName;

    @Column(name = "currency_name")
    String name;

    @Column(name = "nominal")
    String nominal;

    @Column(name = "parent_code")
    String parentCode;

    @Column(name = "iso_num_code")
    String isoNumCode;

    @Column(name = "iso_char_code")
    String isoCharCode;

    @OneToMany(mappedBy = "currencyEntity")
    List<RateEntity> rateEntity;
}

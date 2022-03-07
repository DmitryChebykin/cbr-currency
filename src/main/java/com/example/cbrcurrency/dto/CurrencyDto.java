package com.example.cbrcurrency.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDto {
    Long id;

    String valuteId;

    String engName;

    String name;

    String nominal;

    String parentCode;

    String isoNumCode;

    String isoCharCode;
}

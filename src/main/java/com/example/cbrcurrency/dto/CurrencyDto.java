package com.example.cbrcurrency.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class CurrencyDto {
    private final Long id;

    private final String valuteId;

    private final String engName;

    private final String name;

    private final String nominal;

    private final String parentCode;

    private final String isoNumCode;

    private final String isoCharCode;
}

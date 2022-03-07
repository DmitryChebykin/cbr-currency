package com.example.cbrcurrency.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDto {
    private Long id;

    private String valuteId;

    private String engName;

    private String name;

    private String nominal;

    private String parentCode;

    private String isoNumCode;

    private String isoCharCode;
}

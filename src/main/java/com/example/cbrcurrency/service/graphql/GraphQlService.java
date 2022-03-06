package com.example.cbrcurrency.service.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.cbrcurrency.dto.CurrencyDto;
import com.example.cbrcurrency.entity.CurrencyEntity;
import com.example.cbrcurrency.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.number.money.CurrencyUnitFormatter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GraphQlService implements GraphQLQueryResolver {
    private final CurrencyService currencyService;

    public List<CurrencyDto> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }
}

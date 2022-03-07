package com.example.cbrcurrency.service.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.cbrcurrency.dto.CurrencyDto;
import com.example.cbrcurrency.dto.ExchangeGraphQlDto;
import com.example.cbrcurrency.dto.PeriodStatisticDto;
import com.example.cbrcurrency.service.CurrencyService;
import com.example.cbrcurrency.service.QueryDslService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GraphQlQueryService implements GraphQLQueryResolver {
    private final CurrencyService currencyService;

    private final QueryDslService queryDslService;

    public List<CurrencyDto> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }

    public CurrencyDto getCurrencyByEngName(String engName) {
        return currencyService.getCurrencyByEngName(engName);
    }

    public CurrencyDto getCurrencyByName(String name) {
        return currencyService.getCurrencyByName(name);
    }

    public CurrencyDto getCurrencyByIsoCharCode(String engName) {
        return currencyService.getCurrencyByIsoCharCode(engName);
    }

    public CurrencyDto getCurrencyByValuteId(String valuteId) {
        return currencyService.getCurrencyByValuteId(valuteId);
    }

    public List<ExchangeGraphQlDto> getAllExchanges(Calendar dateFrom, Calendar dateTo) {
        return queryDslService.getExchangesDtoBetweenDates(dateFrom, dateTo);
    }

    public PeriodStatisticDto getAverageRateAndTotalAmountForLastDays(Long sourceId, Long destId, int fromDaysAgo) {
        return queryDslService.getAverageRateAndTotalAmountForLastDays(sourceId, destId, fromDaysAgo);
    }
}

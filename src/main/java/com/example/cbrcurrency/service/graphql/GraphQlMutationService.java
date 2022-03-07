package com.example.cbrcurrency.service.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.cbrcurrency.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class GraphQlMutationService implements GraphQLMutationResolver {
    private final ExchangeService exchangeService;


    public BigDecimal getExchangeResult(String sourceId, String targetId, BigDecimal amount){
        return exchangeService.exchange(sourceId, targetId, amount);
    }
}

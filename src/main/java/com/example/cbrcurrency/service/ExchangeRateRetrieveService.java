package com.example.cbrcurrency.service;

import com.example.cbrcurrency.configuration.CbrConfig;
import com.example.cbrcurrency.xml.currency_registry.ValutaBean;
import com.example.cbrcurrency.xml.quotes.ValCursBean;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class ExchangeRateRetrieveService {
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final WebClient dataClient;

    private final CbrConfig cbrConfig;

    @Cacheable(value = "dailyCurrencyCash", key = "#date")
    public ValCursBean getValCursBean(LocalDate date) {
        String requestUri = String.format("%s?date_req=%s", cbrConfig.getQuotesUrl(), DATE_FORMATTER.format(date));

        return dataClient
                .get().uri(requestUri)
                .exchangeToMono(e -> e.bodyToMono(ValCursBean.class)).block();
    }

    public ValutaBean getValutaBean() {
        return dataClient.get()
                .uri(cbrConfig.getThesaurusUrl())
                .exchangeToMono(e -> e.bodyToMono(ValutaBean.class)).block();
    }
}

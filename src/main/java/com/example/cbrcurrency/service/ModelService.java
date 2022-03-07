package com.example.cbrcurrency.service;

import com.example.cbrcurrency.dto.CurrencyDto;
import com.example.cbrcurrency.service.graphql.GraphQlMutationService;
import com.example.cbrcurrency.service.graphql.GraphQlQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import static com.example.cbrcurrency.util.Util.getCalendar;

@Service
@RequiredArgsConstructor
public class ModelService {
    private final Clock clock;

    private final GraphQlQueryService graphQlQueryService;

    private final GraphQlMutationService graphQlMutationService;

    public Model getModelForMainGetRequest(Model model) {
        List<CurrencyDto> allCurrencies = graphQlQueryService.getAllCurrencies();
        model.addAttribute("currencies", allCurrencies);
        model.addAttribute("date", getCalendar(LocalDate.now(clock)));
        model.addAttribute("title",  "Currency Conversion Service");
        return model;
    }

    public Model getModelForConversionResult(Model model) {
        return model;
    }
}

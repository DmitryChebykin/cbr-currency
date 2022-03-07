package com.example.cbrcurrency.startup;

import com.example.cbrcurrency.service.CurrencyInfoDbSaverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class AppStartupRunner implements ApplicationRunner {

    private final CurrencyInfoDbSaverService currencyInfoDbSaverService;

    @Override
    public void run(ApplicationArguments args) {

        log.info("Application started with option names : {}", args.getOptionNames());

        currencyInfoDbSaverService.saveValutaBean();

        if (args.containsOption("daysBefore")) {
            final List<String> daysBefore = args.getOptionValues("daysBefore");

            final String daysOption = daysBefore.get(0);

            if (daysOption != null && daysOption.matches("^(?:[1-9]\\d*|0)$")) {
                int days = Integer.parseInt(daysOption);

                log.info("Start load currency rates before, total days : {}", days);

                IntStream.range(1, days).forEach(e -> currencyInfoDbSaverService.saveRatesAtDay(LocalDate.now().minusDays(e)));
            }
        }
    }
}

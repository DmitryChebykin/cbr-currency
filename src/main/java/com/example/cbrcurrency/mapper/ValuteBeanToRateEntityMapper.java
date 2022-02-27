package com.example.cbrcurrency.mapper;

import com.example.cbrcurrency.entity.RateEntity;
import com.example.cbrcurrency.repository.CalendarDateEntityRepository;
import com.example.cbrcurrency.repository.CurrencyEntityRepository;
import com.example.cbrcurrency.util.Util;
import com.example.cbrcurrency.xml.quotes.ValuteBean;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

@Mapper
@Slf4j
public abstract class ValuteBeanToRateEntityMapper {
    private static final DecimalFormatSymbols symbols = new DecimalFormatSymbols();

    private static final DecimalFormat decimalFormat = new DecimalFormat();

    static {
        symbols.setDecimalSeparator(',');
        decimalFormat.setDecimalFormatSymbols(symbols);
        decimalFormat.setParseBigDecimal(true);
        decimalFormat.setMinimumFractionDigits(4);
    }

    @Autowired
    protected CurrencyEntityRepository createCurrencyEntityRepository;

    @Autowired
    protected CalendarDateEntityRepository calendarDateEntityRepository;

    @Named("getRateValue")
    public static BigDecimal getRateValue(String value) {
        return stringToBigDecimal(value).get();
    }

    private static Optional<BigDecimal> stringToBigDecimal(String value) {
        try {
            BigDecimal bigDecimal = (BigDecimal) decimalFormat.parse(value);
            log.info(String.format("Success parse %s to BigDecimal", value));

            return Optional.of(bigDecimal);
        } catch (ParseException e) {
            log.info(String.format("Can't parse %s to BigDecimal", value));

            return Optional.empty();
        }
    }

    @Mappings({
            @Mapping(target = "currencyEntity", expression = "java(createCurrencyEntityRepository.findByValuteId(valuteBean.getID()).orElse(null))"),
            @Mapping(target = "calendarDateEntity", expression = "java(calendarDateEntityRepository.findByDate(getCalendarOfLocalDate(localDate)).orElse(null))"),
            @Mapping(target = "currencyRate", source = "valuteBean.value", qualifiedByName = "getRateValue")
    })
    public abstract RateEntity valuteBeanToRateEntity(ValuteBean valuteBean, LocalDate localDate);

    Calendar getCalendarOfLocalDate(LocalDate localDate) {
        return Util.getCalendar(localDate);
    }
}

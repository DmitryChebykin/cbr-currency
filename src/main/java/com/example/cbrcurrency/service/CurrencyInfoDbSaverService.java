package com.example.cbrcurrency.service;

import com.example.cbrcurrency.entity.CalendarDateEntity;
import com.example.cbrcurrency.entity.CurrencyEntity;
import com.example.cbrcurrency.entity.RateEntity;
import com.example.cbrcurrency.mapper.ItemBeanCurrencyEntityMapper;
import com.example.cbrcurrency.mapper.ValuteBeanToRateEntityMapper;
import com.example.cbrcurrency.repository.CalendarDateEntityRepository;
import com.example.cbrcurrency.repository.RateEntityRepository;
import com.example.cbrcurrency.xml.currencyThesaurus.ValutaBean;
import com.example.cbrcurrency.xml.quotes.ValCursBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.cbrcurrency.util.Util.getCalendar;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurrencyInfoDbSaverService {
    private final ExchangeRateRetrieveService exchangeRateRetrieveService;

    private final RateEntityRepository rateEntityRepository;

    private final CalendarDateEntityRepository calendarDateEntityRepository;

    private final ItemBeanCurrencyEntityMapper itemBeanCurrencyEntityMapper;

    private final JdbcTemplate jdbcTemplate;

    private final ValuteBeanToRateEntityMapper valuteBeanToRateEntityMapper;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveValutaBean() {
        log.info("Trying connect to cbr to get currency reference");
        ValutaBean valutaBean = exchangeRateRetrieveService.getValutaBean();

        log.info("Convert cbr xml data to CurrencyEntity list");

        List<CurrencyEntity> currencyEntityList = valutaBean.getItemBean().stream()
                .map(itemBeanCurrencyEntityMapper::itemBeanToCurrencyEntity).collect(Collectors.toList());

        final String sql = "INSERT INTO currency (create_time, last_modified_time" +
                ", eng_name, iso_char_code, iso_num_code, currency_name, nominal, parent_code, valute_id" +
                ") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (valute_id) DO UPDATE " +
                "SET create_time = excluded.create_time, " +
                "eng_name = excluded.eng_name, " +
                "iso_char_code = excluded.iso_char_code, " +
                "iso_num_code = excluded.iso_num_code, " +
                "currency_name = excluded.currency_name, " +
                "nominal = excluded.nominal, " +
                "parent_code = excluded.parent_code, " +
                "last_modified_time = excluded.last_modified_time";

        log.info("Trying batch save CurrencyEntity list to database");

        jdbcTemplate.batchUpdate(sql, getBatchPreparedStatementSetter(currencyEntityList));
    }

    @Cacheable(value = "storeRatesAtDay", key = "#date")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveRatesAtDay(LocalDate date) {
        ValCursBean valCursBean = exchangeRateRetrieveService.getValCursBean(date);
        List<RateEntity> rateEntities = valCursBean.getValuteBeanList().stream()
                .map(e -> valuteBeanToRateEntityMapper.valuteBeanToRateEntity(e, date)).collect(Collectors.toList());

        final String sql = "WITH rows AS (" +
                "INSERT INTO calendar_dates (create_time, last_modified_time, day) " +
                "VALUES (:create_time, :last_modified_time, :day) ON CONFLICT (day) DO NOTHING RETURNING id) " +
                "SELECT id FROM ROWS UNION ALL " +
                "SELECT ID FROM currency WHERE valute_id = :day LIMIT 1";

        Calendar calendarNow = getCalendar(LocalDate.now());

        Calendar historyCalendar = getCalendar(date);

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("create_time", calendarNow)
                .addValue("last_modified_time", calendarNow)
                .addValue("day", historyCalendar);

        Optional<Long> optionalDay = Optional.empty();

        try {
            Long indexOfCalendarDay = namedParameterJdbcTemplate.queryForObject(sql, parameters, Long.class);
            optionalDay = Optional.of(Objects.requireNonNull(indexOfCalendarDay));
        } catch (EmptyResultDataAccessException e) {
            log.info("namedParameterJdbcTemplate receive empty result from DB");
        }

        optionalDay.ifPresent(e -> {
            CalendarDateEntity calendarDateEntity = calendarDateEntityRepository.findById(e).orElseThrow(() -> new CalendarDateNotFoundException("date with id %d not found in DB"));
            rateEntities.stream().forEach(r -> r.setCalendarDateEntity(calendarDateEntity));
            rateEntityRepository.saveAll(rateEntities);
        });

        log.info(rateEntities.toString());
    }

    private BatchPreparedStatementSetter getBatchPreparedStatementSetter(List<CurrencyEntity> currencyEntityList) {
        return new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                CurrencyEntity currencyEntity = currencyEntityList.get(i);
                Timestamp x = new Timestamp(Instant.now().getEpochSecond());
                ps.setTimestamp(1, x);
                ps.setTimestamp(2, x);
                ps.setString(3, currencyEntity.getEngName());
                ps.setString(4, currencyEntity.getIsoCharCode());
                ps.setString(5, currencyEntity.getIsoNumCode());
                ps.setString(6, currencyEntity.getName());
                ps.setString(7, currencyEntity.getNominal());
                ps.setString(8, currencyEntity.getParentCode());
                ps.setString(9, currencyEntity.getValuteId());

                log.info(ps.toString());
            }

            @Override
            public int getBatchSize() {
                return currencyEntityList.size();
            }
        };
    }
}

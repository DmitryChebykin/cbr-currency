package com.example.cbrcurrency.configuration;

import com.example.cbrcurrency.util.Util;
import graphql.language.StringValue;
import graphql.schema.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;

import static com.example.cbrcurrency.util.Util.getCalendar;

@Configuration
@Slf4j
public class GraphQLConfig {

    @Bean
    public GraphQLScalarType dateScalar() {
        return GraphQLScalarType.newScalar()
                .name("Calendar")
                .description("Java 8 Calendar as scalar.")
                .coercing(new Coercing<Calendar, String>() {
                    @Override
                    public String serialize(final Object dataFetcherResult) {
                        if (dataFetcherResult instanceof Calendar) {

                            Calendar cal = Calendar.getInstance();
                            cal.add(Calendar.DATE, 1);
                            Date date = cal.getTime();

                            String inActiveDate = Util.SIMPLE_DATE_FORMAT.format(date);

                            log.info(inActiveDate);

                            return inActiveDate;
                        } else {
                            throw new CoercingSerializeException("Expected a Calendar object.");
                        }
                    }

                    @Override
                    public Calendar parseValue(final Object input) {
                        try {
                            if (input instanceof String) {
                                return getCalendar(LocalDate.parse((String) input));
                            } else {
                                throw new CoercingParseValueException("Expected a String");
                            }
                        } catch (DateTimeParseException e) {
                            throw new CoercingParseValueException(String.format("Not a valid date: '%s'.", input), e
                            );
                        }
                    }

                    @Override
                    public Calendar parseLiteral(final Object input) {
                        if (input instanceof StringValue) {
                            try {
                                return getCalendar(LocalDate.parse(((StringValue) input).getValue()));
                            } catch (DateTimeParseException e) {
                                throw new CoercingParseLiteralException("Wrong date");
                            }
                        } else {
                            throw new CoercingParseLiteralException("Expected a StringValue.");
                        }
                    }
                }).build();
    }
}



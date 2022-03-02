package com.example.cbrcurrency.configuration;

import com.querydsl.sql.Configuration;
import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringConnectionProvider;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
public class QueryDslConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public Configuration configuration() {

        SQLTemplates templates = PostgreSQLTemplates.builder().build();

        Configuration configuration = new Configuration(templates);

        configuration.setExceptionTranslator(new SpringExceptionTranslator());

        return configuration;
    }

    @Bean
    public SQLQueryFactory queryFactory() {

//        SpringConnectionProvider springConnectionProvider = new SpringConnectionProvider(dataSource);

        return new SQLQueryFactory(configuration(), dataSource);
    }
}
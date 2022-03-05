package com.example.cbrcurrency.configuration;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
@AllArgsConstructor
public class QueryDslConfig {

    private final DataSource dataSource;

    private final EntityManager entityManager;

    @Bean
    public Configuration configuration() {

        SQLTemplates templates = PostgreSQLTemplates.builder().build();

        Configuration configuration = new Configuration(templates);

        configuration.setExceptionTranslator(new SpringExceptionTranslator());

        return configuration;
    }

    @Bean
    public SQLQueryFactory queryFactory() {

        return new SQLQueryFactory(configuration(), dataSource);
    }

    @Bean
    public JPAQuery<?> jpaQuery() {
        return new JPAQuery<>(entityManager);
    }
}
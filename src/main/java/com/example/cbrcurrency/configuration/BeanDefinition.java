package com.example.cbrcurrency.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class BeanDefinition {
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}

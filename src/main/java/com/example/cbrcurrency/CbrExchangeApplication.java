package com.example.cbrcurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CbrExchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CbrExchangeApplication.class, args);
    }
}

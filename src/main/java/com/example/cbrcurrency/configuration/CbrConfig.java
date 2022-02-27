package com.example.cbrcurrency.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cbr")
@Setter
@Getter
public class CbrConfig {
    private String thesaurusUrl;

    private String quotesUrl;
}

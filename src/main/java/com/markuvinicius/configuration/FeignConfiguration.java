package com.markuvinicius.configuration;

import com.markuvinicius.models.properties.WordsApiProperties;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients
@Configuration
public class FeignConfiguration {

    private WordsApiProperties wordsApiProperties;

    @Autowired
    public FeignConfiguration(WordsApiProperties wordsApiProperties) {
        this.wordsApiProperties = wordsApiProperties;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("x-rapidapi-host", wordsApiProperties.getWordsApiHost());
            requestTemplate.header("x-rapidapi-key", wordsApiProperties.getWordsAPIKey());
        };
    }
}

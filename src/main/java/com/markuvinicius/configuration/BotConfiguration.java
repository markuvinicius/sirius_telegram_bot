package com.markuvinicius.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markuvinicius.models.properties.TelegramProperties;
import com.markuvinicius.models.properties.WordsApiProperties;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class BotConfiguration {

    @Value("${words.api.url}")
    private String wordsApiUrl;

    @Value("${words.api.host}")
    private String wordsApiHost;

    @Value("${TELEGRAM_TOKEN}")
    private String telegramToken;

    @Value("${BOT_USER_NAME}")
    private String botUserName;

    @Value("${WORDS_API_KEY}")
    private String wordsAPIKey;

    @Autowired
    public BotConfiguration(Environment environment){

    }

    @Bean
    public TelegramProperties configureTelegramProperties(){
        return TelegramProperties.builder()
                .botTelegramToken(telegramToken)
                .botUserName(botUserName)
                .build();
    }

    @Bean
    public WordsApiProperties configureWordsApiProperties(){
        return WordsApiProperties.builder()
                .wordsAPIKey(wordsAPIKey)
                .wordsApiHost(wordsApiHost)
                .wordsApiUrl(wordsApiUrl)
                .build();
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}



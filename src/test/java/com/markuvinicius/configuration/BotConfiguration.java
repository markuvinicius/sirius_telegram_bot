package com.markuvinicius.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import static org.mockito.Mockito.mock;

@Configuration
public class BotConfiguration {
    @Bean
    public TelegramBotsApi telegramBotsApi() {
        return mock(TelegramBotsApi.class);
    }
}

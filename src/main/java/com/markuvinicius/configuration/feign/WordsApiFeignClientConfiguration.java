package com.markuvinicius.configuration.feign;

import feign.Logger;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import org.springframework.context.annotation.Bean;

public class WordsApiFeignClientConfiguration {

    @Bean
    public Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

    @Bean
    public Decoder decoder(){
        return new GsonDecoder();
    }
}

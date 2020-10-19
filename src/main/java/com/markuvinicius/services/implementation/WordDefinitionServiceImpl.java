package com.markuvinicius.services.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markuvinicius.exceptions.EnvironmentVariablesNotFoundException;
import com.markuvinicius.models.words.WordComposition;
import com.markuvinicius.services.WordDefinitionService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class WordDefinitionServiceImpl implements WordDefinitionService {

    private OkHttpClient http;

    @Value("${words.api.url}")
    private String wordsApiUrl;

    @Value("${words.api.host}")
    private String wordsApiHost;

    private String wordsApiKey;
    private Environment environment;

    @Autowired
    public WordDefinitionServiceImpl(Environment environment) throws EnvironmentVariablesNotFoundException {
        this.http = new OkHttpClient();
        this.environment = environment;

        this.wordsApiKey = this.environment.getProperty("WORDS_API_KEY");

        if (this.wordsApiKey.isEmpty()){
            throw new EnvironmentVariablesNotFoundException("Environment Variables Not Found [WORDS_API_KEY]");
        }
    }

    @Override
    public Optional<WordComposition> defineWord(String word) throws IOException {

        Request request = new Request.Builder()
                .url(this.wordsApiUrl + word)
                .get()
                .addHeader("x-rapidapi-host", this.wordsApiHost)
                .addHeader("x-rapidapi-key", this.wordsApiKey)
                .build();

        Response response = http.newCall(request).execute();

        if ( response.code() == HttpStatus.SC_OK ){
            ObjectMapper objectMapper = new ObjectMapper();
            WordComposition wordComposition = objectMapper.readValue(response.body().string(), WordComposition.class);

            return Optional.of(wordComposition);
        }else{
            return Optional.empty();
        }
    }




}

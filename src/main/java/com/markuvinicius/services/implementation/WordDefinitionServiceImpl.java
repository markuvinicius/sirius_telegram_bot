package com.markuvinicius.services.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markuvinicius.exceptions.EnvironmentVariablesNotFoundException;
import com.markuvinicius.models.properties.WordsApiProperties;
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

    @Autowired
    private OkHttpClient http;

    @Autowired
    private WordsApiProperties wordsApiProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Optional<WordComposition> defineWord(String word) throws IOException {

        Request request = new Request.Builder()
                .url(wordsApiProperties.getWordsApiUrl() + word)
                .get()
                .addHeader("x-rapidapi-host", this.wordsApiProperties.getWordsApiHost())
                .addHeader("x-rapidapi-key", this.wordsApiProperties.getWordsAPIKey())
                .build();

        Response response = http.newCall(request).execute();

        if ( response.code() == HttpStatus.SC_OK ){
            WordComposition wordComposition = objectMapper.readValue(response.body().string(), WordComposition.class);

            return Optional.of(wordComposition);
        }else{
            return Optional.empty();
        }
    }




}

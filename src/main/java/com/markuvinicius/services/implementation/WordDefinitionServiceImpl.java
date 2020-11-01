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

    private OkHttpClient http;



    private WordsApiProperties wordsApiProperties;

    @Autowired
    public WordDefinitionServiceImpl(WordsApiProperties properties) throws EnvironmentVariablesNotFoundException {
        this.http = new OkHttpClient();
        this.wordsApiProperties = properties;

        if (!this.isWordsAPIKeyValid()){
            throw new EnvironmentVariablesNotFoundException("Environment Variables Not Found [WORDS_API_KEY]");
        }
    }

    private boolean isWordsAPIKeyValid(){
        return ( ( this.wordsApiProperties.getWordsAPIKey() != null )
                && ( !this.wordsApiProperties.getWordsAPIKey().isEmpty()) );
    }

    @Override
    public Optional<WordComposition> defineWord(String word) throws IOException {

        Request request = new Request.Builder()
                .url(this.wordsApiProperties.getWordsApiUrl() + word)
                .get()
                .addHeader("x-rapidapi-host", this.wordsApiProperties.getWordsApiHost())
                .addHeader("x-rapidapi-key", this.wordsApiProperties.getWordsAPIKey())
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

package com.markuvinicius.tests.unit;

import com.markuvinicius.exceptions.EnvironmentVariablesNotFoundException;
import com.markuvinicius.models.properties.WordsApiProperties;
import com.markuvinicius.services.implementation.WordDefinitionServiceImpl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpStatus;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class WordDefinitionServiceImplTest extends BasicUnitTest{

    @Mock
    private OkHttpClient okHttpClient;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private WordsApiProperties properties;
    private WordDefinitionServiceImpl wordDefinitionService;

    @Test
    public void serviceInstantiationWithoutPropertiesShouldThrownException() throws EnvironmentVariablesNotFoundException {
        thrown.expect(EnvironmentVariablesNotFoundException.class);
        thrown.expectMessage("Environment Variables Not Found [WORDS_API_KEY]");

        properties = WordsApiProperties.builder().build();
        wordDefinitionService = new WordDefinitionServiceImpl(properties);
    }

    /*@Test
    public void defineWordShouldReturnEmptyWhenResponseIsNotOk() throws EnvironmentVariablesNotFoundException, IOException {
        doAnswer(i -> {
            // Mock the response here based on the request
            Request request = i.getArgument(0);
            Response response = mock(Response.class);
            // TODO Set up the response here
            when(response.code()).thenReturn(HttpStatus.SC_OK);

            return response;
        }).when(okHttpClient).newCall(Mockito.any());

        properties = WordsApiProperties.builder()
                .wordsApiHost("host")
                .wordsAPIKey("key")
                .wordsApiUrl("http://teste.com")
                .build();

        wordDefinitionService = new WordDefinitionServiceImpl(properties);
        wordDefinitionService.defineWord("test");

    }*/
}

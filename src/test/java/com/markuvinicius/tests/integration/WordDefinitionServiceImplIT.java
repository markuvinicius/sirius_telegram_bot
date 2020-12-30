package com.markuvinicius.tests.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.markuvinicius.composer.WordCompositionComposer;
import com.markuvinicius.models.properties.TelegramProperties;
import com.markuvinicius.models.properties.WordsApiProperties;
import com.markuvinicius.services.implementation.WordDefinitionServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.Times;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.StringBody.exact;

public class WordDefinitionServiceImplIT extends BasicIntegrationTest{

    @Autowired
    private WordsApiProperties wordsApiProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WordDefinitionServiceImpl wordDefinitionService;

    private ClientAndServer mockServer;
    private final String word = new String("challenge");

    @Before
    public void startServer() {
        mockServer = startClientAndServer(1080);
    }

    @After
    public void stopServer() {
        mockServer.stop();
    }

    @Test
    public void test() throws JsonProcessingException {
        new MockServerClient("127.0.0.1", 1080)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath(wordsApiProperties.getWordsApiUrl() + word)
                                .withHeader("x-rapidapi-host", wordsApiProperties.getWordsApiHost())
                                .withHeader("x-rapidapi-key", wordsApiProperties.getWordsAPIKey()),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(200)
//                                .withHeaders(
//                                        new Header("Content-Type", "application/json; charset=utf-8"),
//                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody(objectMapper.writeValueAsString(WordCompositionComposer.build()))
                                .withDelay(TimeUnit.SECONDS,1)
                );



    }


}

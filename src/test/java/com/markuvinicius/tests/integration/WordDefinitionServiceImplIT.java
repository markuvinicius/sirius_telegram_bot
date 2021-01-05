package com.markuvinicius.tests.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.markuvinicius.models.properties.WordsApiProperties;
import com.markuvinicius.services.implementation.WordDefinitionServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

public class WordDefinitionServiceImplIT extends BasicIT {

    @Autowired
    private WordsApiProperties wordsApiProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WordDefinitionServiceImpl wordDefinitionService;

    //private ClientAndServer mockServer;
    private final String word = new String("challenge");
    private MockRestServiceServer mockServer;

    @Before
    public void startServer() {
        mockServer = MockRestServiceServer.createServer(new RestTemplate());
    }

    @After
    public void stopServer() {
        //mockServer.stop();
    }

    @Test
    public void test() throws JsonProcessingException, URISyntaxException {


        /*new MockServerClient("127.0.0.1", 1080)
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



    }*/
    }



}

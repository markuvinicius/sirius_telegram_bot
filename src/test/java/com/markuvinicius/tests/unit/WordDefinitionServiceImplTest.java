package com.markuvinicius.tests.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markuvinicius.composer.WordCompositionComposer;
import com.markuvinicius.models.properties.WordsApiProperties;
import com.markuvinicius.models.words.WordComposition;
import com.markuvinicius.services.implementation.WordDefinitionServiceImpl;
import okhttp3.*;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class WordDefinitionServiceImplTest extends BasicUnitTest{

    @Mock
    private OkHttpClient okHttpClient;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private WordsApiProperties wordsApiProperties;

    @Mock
    private Call call;

    @InjectMocks
    private WordDefinitionServiceImpl wordDefinitionService ;

    private final String stubbyUrl = "http://stubby.com";
    private Response response;

    @Before
    public void setup(){
        when( wordsApiProperties.getWordsApiUrl() ).thenReturn( stubbyUrl );
        when( wordsApiProperties.getWordsAPIKey() ).thenReturn( "foo" );
        when( wordsApiProperties.getWordsApiHost() ).thenReturn( "bar" );

        when( okHttpClient.newCall(any(Request.class))).thenReturn(call);
    }


    @Test
    public void defineWordShouldReturnNonEmptyOptionalWhenRequestIsOK() throws IOException {
        ResponseBody responseBody = ResponseBody.create("", MediaType.parse("application/json"));

        this.response = new Response.Builder()
                .code(200)
                .request(new Request.Builder().url(stubbyUrl).build())
                .protocol(Protocol.HTTP_1_0)
                .message("message")
                .body(responseBody)
                .build();

        when( call.execute() ).thenReturn(response);
        when( objectMapper.readValue(response.body().string(), WordComposition.class)).thenReturn(WordCompositionComposer.build());

        Optional<WordComposition> wordComposition = wordDefinitionService.defineWord("check");
        Assertions.assertThat( wordComposition.isPresent() ).isTrue();
    }

    @Test
    public void defineWordShouldReturnEmptyOptionalWhenRequestIsNotOK() throws IOException {
        ResponseBody responseBody = ResponseBody.create("", MediaType.parse("application/json"));

        this.response = new Response.Builder()
                .code(404)
                .request(new Request.Builder().url(stubbyUrl).build())
                .protocol(Protocol.HTTP_1_0)
                .message("message")
                .body(responseBody)
                .build();

        when( call.execute() ).thenReturn(response);

        Optional<WordComposition> wordComposition = wordDefinitionService.defineWord("check");
        Assertions.assertThat( wordComposition.isEmpty() ).isTrue();
    }

}

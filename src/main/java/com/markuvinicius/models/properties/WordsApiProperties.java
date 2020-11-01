package com.markuvinicius.models.properties;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WordsApiProperties {
    private String wordsApiUrl;
    private String wordsApiHost;
    private String wordsAPIKey;
}

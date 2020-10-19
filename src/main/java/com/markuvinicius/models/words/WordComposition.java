package com.markuvinicius.models.words;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WordComposition {

    private String word;
    private List<WordDefinition> results;
    private WordSyllables syllables;
    private double frequency;
    private WordPronunciation pronunciation;
}

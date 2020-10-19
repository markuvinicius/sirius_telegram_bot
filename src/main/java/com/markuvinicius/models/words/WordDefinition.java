package com.markuvinicius.models.words;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.markuvinicius.commons.HashDigestUtil;
import lombok.Data;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WordDefinition {
    private String definition;
    private String partOfSpeech;

    private List<String> synonyms;
    private List<String> hasTypes;
    private List<String> typeOf;
    private List<String> examples;
    private List<String> similarTo;
    private List<String> derivation;

    public boolean hasSynonyms(){
        return this.synonyms != null;
    }

    public boolean hasSimilarTo(){
        return this.similarTo != null;
    }

    public boolean hasExamples(){
        return this.examples != null ;
    }
}

package com.markuvinicius.services;

import com.markuvinicius.models.words.WordComposition;

import java.io.IOException;
import java.util.Optional;

public interface WordDefinitionService {

    public Optional<WordComposition> defineWord(String word) throws IOException ;

}

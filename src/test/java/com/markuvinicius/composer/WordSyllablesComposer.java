package com.markuvinicius.composer;

import com.markuvinicius.models.words.WordSyllables;

import java.util.Arrays;

public class WordSyllablesComposer {

    public static WordSyllables build(){
        WordSyllables wordSyllables = new WordSyllables();
        wordSyllables.setCount(5);
        wordSyllables.setList(Arrays.asList("s1","s2","s3"));

        return wordSyllables;
    }
}

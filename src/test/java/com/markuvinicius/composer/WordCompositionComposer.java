package com.markuvinicius.composer;

import com.markuvinicius.models.words.WordComposition;

public class WordCompositionComposer {

    public static WordComposition build(){
        WordComposition wordComposition = new WordComposition();
        wordComposition.setWord("word");
        wordComposition.setFrequency(1.1);
        wordComposition.setPronunciation(WordPronunciationComposer.build());
        wordComposition.setResults(WordDefinitionComposer.buildWords());
        wordComposition.setSyllables(WordSyllablesComposer.build());

        return wordComposition;
    }



}

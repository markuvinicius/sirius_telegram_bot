package com.markuvinicius.composer;

import com.markuvinicius.models.words.WordDefinition;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class WordDefinitionComposer {

    public static WordDefinition build(){
        WordDefinition wordDefinition = new WordDefinition();

        wordDefinition.setDefinition("dummyDefinition");
        wordDefinition.setPartOfSpeech("part-of-speech");
        wordDefinition.setDerivation(buildDerivations());
        wordDefinition.setSynonyms(buildSynonims());
        wordDefinition.setTypeOf(buildTypeOf());
        wordDefinition.setSimilarTo(buildSimilarTo());
        wordDefinition.setHasTypes(buildHasTypes());
        wordDefinition.setExamples(buildExamples());

        return wordDefinition;
    }

    public static List<WordDefinition> buildWords(){
        return Arrays.asList( build() , build() , build() );
    }

    private static List<String> buildExamples(){
        return Arrays.asList("dummyExample1","dummyExample2","dummyExample3");
    }

    private static List<String> buildDerivations(){
        return Arrays.asList("dummyderivation1" , "dummyDerivation2" , "dummyDerivation3");
    }

    private static List<String> buildSynonims(){
        return Arrays.asList("dummySyno1" , "dummySyno2" , "dummySyno3");
    }

    private static List<String> buildTypeOf(){
        return Arrays.asList("dummyTypeOf1" , "dummyTypeOf2" , "dummyTypeOf3");
    }

    private static List<String> buildSimilarTo(){
        return Arrays.asList("dummyTypeOf1" , "dummyTypeOf2" , "dummyTypeOf3");
    }

    private static List<String> buildHasTypes(){
        return Arrays.asList("dummyHasType1","dummyHasType2","dummyHasType3");
    }

}

package com.markuvinicius.composer;

import com.markuvinicius.models.words.WordComposition;
import com.markuvinicius.models.words.WordDefinition;
import com.markuvinicius.mvc.ModelAndView;
import com.markuvinicius.views.implementation.WordDefinitionsView;

public class ModelAndViewWordComposer {

    public static ModelAndView getMVCWord(){
        ModelAndView mvc = new ModelAndView();



        WordComposition wordComposition = new WordComposition();
        wordComposition.setWord("word");
        wordComposition.setFrequency(1.1);

      
        mvc.addObject("word_composition", wordComposition);
        mvc.setView(new WordDefinitionsView());
    }



}

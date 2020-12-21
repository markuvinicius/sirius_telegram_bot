package com.markuvinicius.composer;

import com.markuvinicius.models.words.WordDefinition;
import com.markuvinicius.mvc.ModelAndView;
import com.markuvinicius.views.implementation.WordDefinitionsView;

public class ModelAndViewWordDefinitionComposer {

    public static ModelAndView build(){
        ModelAndView mvc = new ModelAndView();

        mvc.addObject("word_definition", WordDefinitionComposer.build());
        mvc.addObject("chat_id", Long.MAX_VALUE);
        mvc.setView(new WordDefinitionsView());
        return mvc;
    }

}

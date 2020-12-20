package com.markuvinicius.composer;

import com.markuvinicius.mvc.ModelAndView;
import com.markuvinicius.views.implementation.WordDefinitionsView;

public class ModelAndViewWordComposer {

    public static ModelAndView build(){
        ModelAndView mvc = new ModelAndView();

        mvc.addObject("word_composition", WordCompositionComposer.build());
        mvc.addObject("chat_id", Long.MAX_VALUE);
        mvc.setView(new WordDefinitionsView());
        return mvc;
    }

}

package com.markuvinicius.composer;

import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.mvc.ModelAndView;
import com.markuvinicius.views.implementation.BotExceptionView;
import com.markuvinicius.views.implementation.WordDefinitionsView;

public class ModelAndViewBotExceptionComposer {

    public static ModelAndView build(BotException exception){
        ModelAndView mvc = new ModelAndView();

        mvc.addObject("exception", exception);
        mvc.addObject("chat_id", Long.MAX_VALUE);
        mvc.setView(new BotExceptionView());
        return mvc;
    }

}

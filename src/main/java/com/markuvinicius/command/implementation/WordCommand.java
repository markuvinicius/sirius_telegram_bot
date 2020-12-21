package com.markuvinicius.command.implementation;

import com.markuvinicius.command.BotCommand;
import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.exceptions.WordNotFoundException;
import com.markuvinicius.exceptions.WordServiceNotAvailableException;
import com.markuvinicius.models.words.WordComposition;
import com.markuvinicius.mvc.ModelAndView;
import com.markuvinicius.services.WordDefinitionService;
import com.markuvinicius.views.implementation.WordDefinitionsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class WordCommand implements BotCommand {
    @Autowired
    public WordDefinitionService wordDefinitionService;

    @Override
    public ModelAndView execute(String arguments) throws BotException {
        Optional<WordComposition> translations;

        try {
            translations = this.wordDefinitionService.defineWord(arguments);
        } catch (IOException e) {
            throw new WordServiceNotAvailableException("It was not possible to connect to WordService: " + e.getMessage());
        }

        if ( translations.isPresent() ) {
            ModelAndView mvc = new ModelAndView();
            mvc.addObject("word_composition", translations.get());
            mvc.setView(new WordDefinitionsView());
            return mvc;
        }else{
            throw new WordNotFoundException("Word " + arguments + " - Not Found");
        }
    }
}

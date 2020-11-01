package com.markuvinicius.handlers.implementation;

import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.handlers.AbstractUpdateHandler;
import com.markuvinicius.models.commands.Command;
import com.markuvinicius.models.commands.CommandDetails;
import com.markuvinicius.models.words.WordComposition;
import com.markuvinicius.models.words.WordDefinition;
import com.markuvinicius.mvc.ModelAndView;
import com.markuvinicius.services.WordDefinitionService;
import com.markuvinicius.views.implementation.WordDefinitionDetailsView;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
public class CallBackQueryUpdateHandler extends AbstractUpdateHandler {

    private WordDefinitionService wordDefinitionService;

    @Autowired
    public CallBackQueryUpdateHandler(WordDefinitionService wordDefinitionService) {
        this.wordDefinitionService = wordDefinitionService;
    }

    @Override
    public ModelAndView execute(Update update) throws BotException {
        if (update.hasCallbackQuery()){
            log.info("Processing CallBackQuery");
            try {
                return answerUpdate(update);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return checkNext(update);
    }

    private ModelAndView answerUpdate(Update update) throws IOException {
        CommandDetails commandDetails = extractCommandDetails(update.getCallbackQuery().getData());

        if ( commandDetails.getCommandName().equals("word") ){
            return this.processWordCallBack(commandDetails.getArguments(),
                                            commandDetails.getIndex(),
                                            update.getCallbackQuery().getMessage().getChatId());
        }

        return null;
    }

    private CommandDetails extractCommandDetails(String callBackData){
        return CommandDetails.builder()
                .commandName(callBackData.split("/")[0])
                .arguments(callBackData.split("/")[1])
                .index(Integer.valueOf(callBackData.split("/")[2]))
                .build();
    }

    private ModelAndView processWordCallBack(String word, int definitionIndex, Long chatId) throws IOException {
        Optional<WordComposition> wordComposition = this.wordDefinitionService.defineWord(word);
        ModelAndView mvc = null;

        if (wordComposition.isPresent()){
            WordComposition wc = wordComposition.get();
            WordDefinition wordDefinition = wc.getResults().get(definitionIndex);
            mvc = new ModelAndView();
            mvc.addObject("word_definition",wordDefinition);
            mvc.addObject("chat_id", chatId);
            mvc.setView(new WordDefinitionDetailsView());

        }

        return mvc;
    }
}

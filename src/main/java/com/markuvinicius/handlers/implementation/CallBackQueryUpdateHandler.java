package com.markuvinicius.handlers.implementation;

import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.handlers.AbstractUpdateHandler;
import com.markuvinicius.models.words.WordComposition;
import com.markuvinicius.models.words.WordDefinition;
import com.markuvinicius.mvc.ModelAndView;
import com.markuvinicius.services.WordDefinitionService;
import com.markuvinicius.views.implementation.WordDefinitionDetailsView;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
public class CallBackQueryUpdateHandler extends AbstractUpdateHandler {

    private WordDefinitionService wordDefinitionService;


    private class CallBackCommand{
        private String command;
        private String detail;
        private String info;
    }

    @Autowired
    public CallBackQueryUpdateHandler(WordDefinitionService wordDefinitionService) {
        this.wordDefinitionService = wordDefinitionService;
    }

    @Override
    public ModelAndView execute(Update update) throws BotException {
        if (update.hasCallbackQuery()){
            log.info("Processing CallBackQuery");
            try {
                return processCallBackQuery(update);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return checkNext(update);
    }

    private ModelAndView processCallBackQuery(Update update) throws IOException {
        String callBackData = update.getCallbackQuery().getData();

        String callBackCommand = callBackData.split("/")[0];
        String callBackDetail = callBackData.split("/")[1];
        String callBackInfo = callBackData.split("/")[2];

        if ( callBackCommand.equals("word") ){
            return this.processWordCallBack(callBackDetail,Integer.parseInt(callBackInfo),update.getCallbackQuery().getMessage().getChatId());
        }


        return null;
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

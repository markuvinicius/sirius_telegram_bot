package com.markuvinicius.handlers.implementation;

import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.handlers.AbstractUpdateHandler;
import com.markuvinicius.models.words.WordComposition;
import com.markuvinicius.models.words.WordDefinition;
import com.markuvinicius.mvc.ModelAndView;
import com.markuvinicius.services.WordDefinitionService;
import com.markuvinicius.views.implementation.WordDefinitionDetailsView;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.Optional;

@Component
public class CallBackQueryUpdateHandler extends AbstractUpdateHandler {

    private WordDefinitionService wordDefinitionService;

    @Autowired
    public CallBackQueryUpdateHandler(WordDefinitionService wordDefinitionService) {
        this.wordDefinitionService = wordDefinitionService;
    }

    private ModelAndView processCallBackQuery(Update update, Optional<Session> session) throws IOException {
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

    @Override
    public ModelAndView execute(Update update, Optional<Session> session) throws BotException {

        if (update.hasCallbackQuery()){

            String callBackData = update.getCallbackQuery().getData();
            System.out.println("Processando CallBackQuery");
            try {
                return processCallBackQuery(update,session);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return checkNext(update,session);
    }
}

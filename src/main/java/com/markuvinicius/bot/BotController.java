package com.markuvinicius.bot;

import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.exceptions.EnvironmentVariablesNotFoundException;
import com.markuvinicius.models.properties.TelegramProperties;
import com.markuvinicius.mvc.ModelAndView;
import com.markuvinicius.services.UpdateHandleService;
import com.markuvinicius.views.ResponseView;
import com.markuvinicius.views.implementation.BotExceptionView;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.session.TelegramLongPollingSessionBot;

import java.util.Optional;

@Component
@Slf4j
public class BotController extends TelegramLongPollingBot {
    private UpdateHandleService updateHandler;
    private TelegramProperties telegramProperties;

    @Autowired
    public BotController(UpdateHandleService updateHandler,
                         TelegramProperties properties) throws EnvironmentVariablesNotFoundException {
        this.updateHandler = updateHandler;
        this.telegramProperties = properties;

        if (!this.isTelegramCredentialsValid()){
            throw new EnvironmentVariablesNotFoundException("Environment Variables Not Found or invalid [BOT_USER_NAME, TELEGRAM_TOKEN]");
        }
    }

    private boolean isTelegramCredentialsValid(){
        return ( ( telegramProperties.getBotUserName() != null)
                    && ( !telegramProperties.getBotUserName().isEmpty() )
                    && ( telegramProperties.getBotTelegramToken() != null )
                    && ( !telegramProperties.getBotTelegramToken().isEmpty() ) );
    }

    @Override
    public void onUpdateReceived(Update update) {
        ModelAndView modelAndView = this.buildModelAndViewFromUpdate(update);

        ResponseView view = modelAndView.getView();
        ModelMap objects = modelAndView.getModelObjects();
        SendMessage message = view.render(objects);

        answerMessage(message);
    }

    private void answerMessage(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private ModelAndView buildModelAndViewFromUpdate(Update update){
        try {
            return this.updateHandler.handleUpdate(update);
        }catch (BotException exception){
            return this.buildModelAndViewFromException(exception,update);
        }
    }

    private ModelAndView buildModelAndViewFromException(BotException exception, Update update){
        ModelAndView mvc = new ModelAndView();
        mvc.addObject("exception",exception);
        mvc.addObject("chat_id",update.getMessage().getChatId());
        mvc.setView(new BotExceptionView());

        return mvc;
    }

    @Override
    public String getBotUsername() {
        return this.telegramProperties.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return this.telegramProperties.getBotTelegramToken();
    }


}

package com.markuvinicius.bot;

import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.exceptions.EnvironmentVariablesNotFoundException;
import com.markuvinicius.mvc.ModelAndView;
import com.markuvinicius.services.UpdateHandleService;
import com.markuvinicius.views.ResponseView;
import com.markuvinicius.views.implementation.BotExceptionView;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.session.TelegramLongPollingSessionBot;


import java.util.Optional;

@Component
@Slf4j
public class BotController extends TelegramLongPollingSessionBot {

    private UpdateHandleService updateHandler;
    private String botUserName;
    private String botTelegramToken;

    private Environment environment;

    @Autowired
    public BotController(UpdateHandleService updateHandler,
                         Environment env) throws EnvironmentVariablesNotFoundException {
        this.updateHandler = updateHandler;
        this.environment = env;

        this.botUserName = this.environment.getProperty("BOT_USER_NAME");
        this.botTelegramToken = this.environment.getProperty("TELEGRAM_TOKEN");

        if ((this.botUserName.isEmpty()) || (this.botTelegramToken.isEmpty())){
            throw new EnvironmentVariablesNotFoundException("Environment Variables Not Found [BOT_USER_NAME, TELEGRAM_TOKEN]");
        }
    }

    @Override
    public void onUpdateReceived(Update update, Optional<Session> optional) {
        ModelAndView modelAndView = null;

        try {
            modelAndView = this.updateHandler.handleUpdate(update, optional);
        }catch (BotException exception){
            modelAndView = this.buildModelAndViewFromException(exception,update);
        }

        if (modelAndView != null) {
            ResponseView view = modelAndView.getView();
            ModelMap objects = modelAndView.getModelObjects();
            SendMessage message = view.render(objects);

            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
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
        return this.botUserName;
    }

    @Override
    public String getBotToken() {
        return botTelegramToken;
    }


}

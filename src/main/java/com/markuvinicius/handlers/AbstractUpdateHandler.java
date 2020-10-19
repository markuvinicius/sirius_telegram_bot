package com.markuvinicius.handlers;

import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.mvc.ModelAndView;
import org.apache.shiro.session.Session;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public abstract class AbstractUpdateHandler implements UpdateHandler {
    private UpdateHandler nextHandler;

    @Override
    public UpdateHandler linkWith(UpdateHandler handler) {
        this.nextHandler = handler;
        return handler;
    }

    protected ModelAndView checkNext(Update update, Optional<Session> session) throws BotException {
        if ( this.nextHandler == null) {
            return null;
        }

        return this.nextHandler.execute(update, session);
    }

    public abstract ModelAndView execute(Update update, Optional<Session> session) throws BotException;
}

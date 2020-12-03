package com.markuvinicius.handlers;

import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.mvc.ModelAndView;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractUpdateHandler implements UpdateHandler {
    private UpdateHandler nextHandler;

    @Override
    public UpdateHandler linkWith(UpdateHandler handler) {
        this.nextHandler = handler;
        return handler;
    }

    protected ModelAndView checkNext(Update update) throws BotException {
        if ( this.nextHandler == null) {
            return null;
        }

        return this.nextHandler.execute(update);
    }

    public abstract ModelAndView execute(Update update) throws BotException;
}

package com.markuvinicius.handlers;

import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.mvc.ModelAndView;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateHandler {

    public UpdateHandler linkWith(UpdateHandler handler);
    public ModelAndView execute(Update update) throws BotException;
}

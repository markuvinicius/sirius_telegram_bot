package com.markuvinicius.handlers;

import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.mvc.ModelAndView;
import org.apache.shiro.session.Session;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public interface UpdateHandler {

    public UpdateHandler linkWith(UpdateHandler handler);
    public ModelAndView execute(Update update, Optional<Session> session) throws BotException;
}

package com.markuvinicius.handlers.implementation;

import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.handlers.AbstractUpdateHandler;
import com.markuvinicius.mvc.ModelAndView;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
@Slf4j
public class LinkUpdataHandler extends AbstractUpdateHandler {
    @Override
    public ModelAndView execute(Update update, Optional<Session> session) throws BotException {
        return checkNext(update,session);
    }
}

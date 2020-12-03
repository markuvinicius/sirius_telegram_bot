package com.markuvinicius.handlers.implementation;

import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.handlers.AbstractUpdateHandler;
import com.markuvinicius.mvc.ModelAndView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class LinkUpdataHandler extends AbstractUpdateHandler {
    @Override
    public ModelAndView execute(Update update) throws BotException {
        return checkNext(update);
    }
}

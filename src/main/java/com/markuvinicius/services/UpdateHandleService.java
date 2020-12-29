package com.markuvinicius.services;

import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.handlers.UpdateHandler;
import com.markuvinicius.handlers.implementation.CallBackQueryUpdateHandler;
import com.markuvinicius.handlers.implementation.CommandUpdateHandler;
import com.markuvinicius.handlers.implementation.LinkUpdataHandler;
import com.markuvinicius.mvc.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UpdateHandleService {
    private UpdateHandler handler;

    @Autowired
    public UpdateHandleService(CommandUpdateHandler commandUpdateHandler,
                               CallBackQueryUpdateHandler callBackUpdateHandler,
                               LinkUpdataHandler linkUpdateHandler){
        this.handler = commandUpdateHandler;

        this.handler
                .linkWith(callBackUpdateHandler)
                .linkWith(linkUpdateHandler);
    }

    public ModelAndView handleUpdate(Update update) throws BotException {
        return this.handler.execute(update);
    }
}

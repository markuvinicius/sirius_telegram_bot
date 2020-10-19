package com.markuvinicius.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BotException extends Exception{

    public BotException(String message){
        super(message);
        log.error("Exception Handled: " + message);
    }
}

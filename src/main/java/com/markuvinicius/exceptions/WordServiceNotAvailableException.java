package com.markuvinicius.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WordServiceNotAvailableException extends BotException{
    public WordServiceNotAvailableException(String msg){
        super(msg);
    }
}

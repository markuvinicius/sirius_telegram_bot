package com.markuvinicius.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WordNotFoundException extends BotException{
    public WordNotFoundException(String message) {
        super(message);
    }
}

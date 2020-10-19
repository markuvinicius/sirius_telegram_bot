package com.markuvinicius.command.implementation;

import com.markuvinicius.command.BotCommand;
import com.markuvinicius.command.CommandFactory;
import com.markuvinicius.exceptions.BotCommandNotFoundException;
import com.markuvinicius.exceptions.BotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandFactoryImpl implements CommandFactory {
    @Autowired
    private WordCommand wordCommand;

    @Override
    public BotCommand getCommand(String commandName) throws BotException {
        switch (commandName){
            case "/word":return wordCommand;
            default: throw new BotCommandNotFoundException("Command " + commandName + " - NOT FOUND");
        }
    }
}

package com.markuvinicius.command;

import com.markuvinicius.exceptions.BotException;

public interface CommandFactory {
    BotCommand getCommand(String commandName) throws BotException;
}

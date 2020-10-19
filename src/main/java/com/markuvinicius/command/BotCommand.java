package com.markuvinicius.command;

import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.mvc.ModelAndView;

public interface BotCommand {

    ModelAndView execute(String argument) throws BotException;
}

package com.markuvinicius.handlers.implementation;

import com.markuvinicius.command.BotCommand;
import com.markuvinicius.command.CommandFactory;
import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.handlers.AbstractUpdateHandler;
import com.markuvinicius.mvc.ModelAndView;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class CommandUpdateHandler  extends AbstractUpdateHandler {
    private CommandFactory commandFactory;

    @Autowired
    public CommandUpdateHandler(CommandFactory factory){
        this.commandFactory = factory;
    }


    @Override
    public ModelAndView execute(Update update) throws BotException {
        if (update.hasMessage()){
            if (update.getMessage().hasEntities()){
                if (this.hasCommand( update.getMessage().getEntities()) ) {
                    log.info("Processing Command");
                    return this.processBotCommand(update.getMessage());
                }
            }
        }

        return this.checkNext(update);
    }

    private boolean hasCommand(List<MessageEntity> entities){
        MessageEntity commandEntity = entities.stream()
                .filter( messageEntity -> messageEntity.getType().equals("bot_command"))
                .findFirst()
                .orElse( null);

        return commandEntity != null;
    }

    private MessageEntity getCommand(List<MessageEntity> entities){
        return entities.stream()
                    .filter( messageEntity -> messageEntity.getType().equals("bot_command"))
                    .findFirst()
                    .get();
    }

    private ModelAndView processBotCommand(Message message) throws BotException{
        MessageEntity commandEntity = this.getCommand(message.getEntities());
        String command = commandEntity.getText().trim();
        String[] arguments = message.getText().toLowerCase().replaceAll(command,"").trim().split(" ");
        ModelAndView mvc = null;

        BotCommand botCommand = commandFactory.getCommand(command.toLowerCase());
        mvc = botCommand.execute(arguments[0]);
        mvc.addObject("chat_id",message.getChatId());

        return mvc;
    }
}

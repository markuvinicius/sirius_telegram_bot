package com.markuvinicius.handlers.implementation;

import com.markuvinicius.command.BotCommand;
import com.markuvinicius.command.CommandFactory;
import com.markuvinicius.constants.BotDomainConstants;
import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.handlers.AbstractUpdateHandler;
import com.markuvinicius.models.commands.Command;
import com.markuvinicius.mvc.ModelAndView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@Slf4j
public class CommandUpdateHandler  extends AbstractUpdateHandler {

    @Autowired
    private CommandFactory commandFactory;

    @Override
    public ModelAndView execute(Update update) throws BotException {
        if (this.hasCommand( update ) ) {
            log.info("Processing Command");
            return this.answerMessage(update.getMessage());
        }
        return this.checkNext(update);
    }

    private boolean hasCommand(Update update){
        if ( update.hasMessage() ) {
            if (update.getMessage().hasEntities()) {
                return update.getMessage().getEntities().stream()
                        .filter(messageEntity -> messageEntity.getType().equals(BotDomainConstants.MessageEntityType.BOT_COMMAND))
                        .findFirst()
                        .isPresent();
            }
        }

        return false;
    }

    private ModelAndView answerMessage(Message message) throws BotException{
        Command command = extractCommandDetailsFromMessage(message);
        BotCommand botCommand = commandFactory.getCommand(command.getCommandName());

        ModelAndView mvc  = botCommand.execute(command.getArgument());
        mvc.addObject("chat_id",message.getChatId());

        return mvc;
    }

    private Command extractCommandDetailsFromMessage(Message message){
        MessageEntity commandEntity = this.findBotCommand(message.getEntities());
        String messageText = message.getText();
        String commandText = commandEntity.getText();

        String commandName = commandText
                                .replaceAll("/","")
                                .trim()
                                .toLowerCase();

        String argument = messageText
                            .replace( commandText , "")
                            .toLowerCase()
                            .trim();

        return Command.builder()
                    .commandName(commandName)
                    .argument(argument)
                    .build();
    }

    private MessageEntity findBotCommand(List<MessageEntity> entities){
        return entities.stream()
                .filter( messageEntity -> messageEntity.getType().equals(BotDomainConstants.MessageEntityType.BOT_COMMAND))
                .findFirst()
                .get();
    }
}

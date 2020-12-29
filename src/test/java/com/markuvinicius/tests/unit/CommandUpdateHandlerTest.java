package com.markuvinicius.tests.unit;

import com.markuvinicius.command.BotCommand;
import com.markuvinicius.command.CommandFactory;
import com.markuvinicius.composer.ModelAndViewWordCompositionComposer;
import com.markuvinicius.constants.BotDomainConstants;
import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.handlers.implementation.CommandUpdateHandler;
import com.markuvinicius.models.words.WordComposition;
import com.markuvinicius.mvc.ModelAndView;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

public class CommandUpdateHandlerTest extends BasicUnitTest{

    @Mock private Message message;
    @Mock private MessageEntity messageEntity;
    @InjectMocks private Update update;

    @Mock private BotCommand botCommand;
    @Mock private CommandFactory commandFactory;
    @InjectMocks private CommandUpdateHandler commandUpdateHandler;

    private String wordCommandText;
    private String messageText;
    private String argumentText;
    private Long chatId;
    private String wordCommand;
    private final String OBJECT_KEY_NAME = "word_composition";

    @Before
    public void setup(){
        wordCommandText = new String("/word");
        wordCommand = new String("word");
        messageText = new String("/word word");
        argumentText = new String( "word");
        chatId = 123L;
    }

    @Test
    public void CommandUpdateHandlerShouldReturnModelAndViewWithAChatID() throws BotException {
        Mockito.when( message.hasEntities() ).thenReturn(true);
        Mockito.when( message.getEntities() ).thenReturn( Arrays.asList(messageEntity) );
        Mockito.when( message.getText() ).thenReturn(messageText);
        Mockito.when( message.getChatId() ).thenReturn(chatId);

        Mockito.when( messageEntity.getText() ).thenReturn( wordCommandText);
        Mockito.when( messageEntity.getType() ).thenReturn(BotDomainConstants.MessageEntityType.BOT_COMMAND);

        Mockito.when( commandFactory.getCommand(wordCommand) ).thenReturn( botCommand );
        Mockito.when( botCommand.execute(argumentText) ).thenReturn( new ModelAndView() );

        ModelAndView mvc = commandUpdateHandler.execute(update);

        Assertions.assertThat(mvc.getModelObjects().getAttribute("chat_id")).isEqualTo(chatId);
    }

    @Test
    public void CommandUpdateHandlerShouldReturnModelAndViewWithObjectsAndView() throws BotException {
        Mockito.when( message.hasEntities() ).thenReturn(true);
        Mockito.when( message.getEntities() ).thenReturn( Arrays.asList(messageEntity) );
        Mockito.when( message.getText() ).thenReturn(messageText);
        Mockito.when( message.getChatId() ).thenReturn(chatId);

        Mockito.when( messageEntity.getText() ).thenReturn( wordCommandText);
        Mockito.when( messageEntity.getType() ).thenReturn(BotDomainConstants.MessageEntityType.BOT_COMMAND);

        Mockito.when( commandFactory.getCommand(wordCommand) ).thenReturn( botCommand );
        Mockito.when( botCommand.execute(argumentText) ).thenReturn(ModelAndViewWordCompositionComposer.build());

        ModelAndView mvc = commandUpdateHandler.execute(update);

        Mockito.verify( commandFactory ,Mockito.times(1)).getCommand(wordCommand);
        Mockito.verify( botCommand , Mockito.times(1) ).execute(argumentText);

        Assertions.assertThat(mvc.getModelObjects().getAttribute("word_composition")).isInstanceOf(WordComposition.class);
    }

    @Test
    public void CommandUpdateHandlerShouldReturnNullWhenUpdateHasNoEntity() throws BotException {
        Mockito.when( message.hasEntities() ).thenReturn( false );
        ModelAndView mvc = commandUpdateHandler.execute(update);
        Assertions.assertThat(mvc).isNull();
    }

}

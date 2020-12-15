package com.markuvinicius.tests.unit;

import com.markuvinicius.command.CommandFactory;
import com.markuvinicius.command.implementation.WordCommand;
import com.markuvinicius.constants.BotDomainConstants;
import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.handlers.implementation.CommandUpdateHandler;
import com.markuvinicius.mvc.ModelAndView;
import com.markuvinicius.services.WordDefinitionService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

//@RunWith(SpringRunner.class)
public class CommandUpdateHandlerTest extends BasicUnitTest{

    @Mock private Message message;
    @Mock private MessageEntity messageEntity;
    @InjectMocks private Update update;

    @Mock private CommandFactory commandFactory;
    @InjectMocks private CommandUpdateHandler commandUpdateHandler;

    @Mock private WordDefinitionService wordDefinitionService;
    @InjectMocks private WordCommand wordCommand;

    private String wordCommandText;
    private String messageText;
    private String argumentText;

    @Before
    public void setup(){
        wordCommandText = new String("/word");
        messageText = new String("/word wordToSearch");
        argumentText = new String( "wordToSearch");
    }

    @Test
    public void test() throws BotException {
        Mockito.when( message.hasEntities() ).thenReturn(true);
        Mockito.when( message.getEntities() ).thenReturn( Arrays.asList(messageEntity) );
        Mockito.when( message.getText() ).thenReturn(messageText);
        Mockito.when( message.getChatId() ).thenReturn(123L);

        Mockito.when( messageEntity.getText() ).thenReturn( wordCommandText);
        Mockito.when( messageEntity.getOffset() ).thenReturn( 0 );
        Mockito.when( messageEntity.getLength() ).thenReturn( 5 );
        Mockito.when( messageEntity.getType() ).thenReturn(BotDomainConstants.MessageEntityType.BOT_COMMAND);

        Mockito.when( commandFactory.getCommand("word") ).thenReturn( wordCommand );
        Mockito.when( wordCommand.execute(argumentText) ).thenReturn(null);

        ModelAndView mvc = commandUpdateHandler.execute(update);

        Assertions.assertThat(mvc).isNull();
    }
}

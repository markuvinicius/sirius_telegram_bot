package com.markuvinicius.tests.unit;

import com.markuvinicius.command.CommandFactory;
import com.markuvinicius.composer.ModelAndViewWordCompositionComposer;
import com.markuvinicius.constants.BotDomainConstants;
import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.handlers.implementation.CallBackQueryUpdateHandler;
import com.markuvinicius.handlers.implementation.CommandUpdateHandler;
import com.markuvinicius.handlers.implementation.LinkUpdataHandler;
import com.markuvinicius.models.words.WordComposition;
import com.markuvinicius.mvc.ModelAndView;
import com.markuvinicius.services.UpdateHandleService;
import com.markuvinicius.services.WordDefinitionService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class UpdateHandleServiceTest extends BasicUnitTest{

    @Mock private CommandFactory factory;
    @Mock private WordDefinitionService wordDefinitionService;

    @InjectMocks private CommandUpdateHandler commandUpdateHandler = spy(CommandUpdateHandler.class);
    @InjectMocks private CallBackQueryUpdateHandler callBackQueryUpdateHandler = spy(CallBackQueryUpdateHandler.class);

    @Spy private LinkUpdataHandler linkUpdataHandler;

    @InjectMocks
    private UpdateHandleService updateHandleService;

    //@Mock private Message message;
    //@Mock private MessageEntity messageEntity;
    @InjectMocks private Update update;

    private String messageText;
    //private long chatId;

    @Test
    public void setUp(){
        messageText = new String("/word word");

    }

    @Test
    public void handleUpdateShouldProcessCommand() throws BotException {
        when( commandUpdateHandler.execute(update) ).thenReturn( ModelAndViewWordCompositionComposer.build() );
        ModelAndView mvc = updateHandleService.handleUpdate(update);

        //Mockito.verify( callBackQueryUpdateHandler , Mockito.times(0)).execute(update);
        //Mockito.verify( linkUpdataHandler , Mockito.times(0)).execute(update);

        Assertions.assertThat(mvc.getModelObjects().getAttribute("word_composition")).isInstanceOf(WordComposition.class);

        Assertions.assertThat(true).isTrue();
    }

}

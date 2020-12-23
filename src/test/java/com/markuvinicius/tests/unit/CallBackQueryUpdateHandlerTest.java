package com.markuvinicius.tests.unit;

import com.markuvinicius.command.BotCommand;
import com.markuvinicius.command.CommandFactory;
import com.markuvinicius.composer.WordCompositionComposer;
import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.handlers.implementation.CallBackQueryUpdateHandler;
import com.markuvinicius.models.words.WordDefinition;
import com.markuvinicius.mvc.ModelAndView;
import com.markuvinicius.services.WordDefinitionService;
import com.markuvinicius.views.implementation.WordDefinitionDetailsView;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.Optional;

public class CallBackQueryUpdateHandlerTest  extends BasicUnitTest{

    @Mock private Message message;
    @InjectMocks private CallbackQuery callbackQuery = Mockito.spy(CallbackQuery.class);
    @InjectMocks private Update update = Mockito.spy(Update.class);

    @Mock private BotCommand botCommand;
    @Mock private CommandFactory commandFactory;
    @Mock private WordDefinitionService wordDefinitionService;
    @InjectMocks  private CallBackQueryUpdateHandler callBackQueryUpdateHandler;

    @Rule public ExpectedException expectedException = ExpectedException.none();

    private String wordCommandText;
    private String argumentText;
    private Long chatId;
    private final String OBJECT_KEY_NAME = "word_definition";



    @Before
    public void setup(){
        wordCommandText = new String("word/word-to-search/1");
        argumentText = new String("word-to-search");
        chatId = 123L;
    }

    @Test
    public void CallBackQueryUpdateHandlerShouldReturnModelAndView() throws BotException, IOException {
        Mockito.when( update.getCallbackQuery().getData() ).thenReturn(wordCommandText);
        Mockito.when( update.getCallbackQuery().getMessage().getChatId() ).thenReturn( chatId );
        Mockito.when( wordDefinitionService.defineWord(argumentText)).thenReturn( Optional.of( WordCompositionComposer.build()) );
        ModelAndView mvc = callBackQueryUpdateHandler.execute(update);

        Assertions.assertThat( mvc ).isNotNull();
        Assertions.assertThat( mvc ).isInstanceOf( ModelAndView.class);
    }

    @Test
    public void handlerShouldCallNextInTheChainWhenNoCallBackQuery() throws BotException, IOException {
        Mockito.when( update.hasCallbackQuery() ).thenReturn(false);
        ModelAndView mvc = callBackQueryUpdateHandler.execute(update);

        Assertions.assertThat( mvc ).isNull();
    }

    @Test
    public void executeShouldThrowBotExceptionWhenError() throws IOException, BotException {
        expectedException.expect(BotException.class);
        Mockito.when( update.getCallbackQuery().getData() ).thenReturn(wordCommandText);
        Mockito.when( update.getCallbackQuery().getMessage().getChatId() ).thenReturn( chatId );

        Mockito.when( wordDefinitionService.defineWord(argumentText) ).thenThrow(IOException.class);
        callBackQueryUpdateHandler.execute(update);
        //Mockito.verify( wordDefinitionService.defineWord(argumentText),Mockito.times(1));

    }

    @Test
    public void callBackQueryUpdateHandlerShouldReturnModelAndViewWithWordDefinition() throws BotException, IOException {
        Mockito.when( update.getCallbackQuery().getData() ).thenReturn(wordCommandText);
        Mockito.when( update.getCallbackQuery().getMessage().getChatId() ).thenReturn( chatId );
        Mockito.when( wordDefinitionService.defineWord(argumentText)).thenReturn( Optional.of( WordCompositionComposer.build()) );
        ModelAndView mvc = callBackQueryUpdateHandler.execute(update);


        Assertions.assertThat( mvc.getObject(OBJECT_KEY_NAME).isPresent() ).isTrue();
        Assertions.assertThat( mvc.getObject(OBJECT_KEY_NAME).get() ).isInstanceOf(WordDefinition.class);
        Assertions.assertThat( mvc.getObject(OBJECT_KEY_NAME).get() ).isNotNull();
    }

    @Test
    public void CallBackQueryUpdateHandlerShouldReturnModelAndViewWithChatId() throws BotException, IOException {
        Mockito.when( update.getCallbackQuery().getData() ).thenReturn(wordCommandText);
        Mockito.when( update.getCallbackQuery().getMessage().getChatId() ).thenReturn( chatId );
        Mockito.when( wordDefinitionService.defineWord(argumentText)).thenReturn( Optional.of( WordCompositionComposer.build()) );
        ModelAndView mvc = callBackQueryUpdateHandler.execute(update);

        Assertions.assertThat( mvc.getObject("chat_id").isPresent() ).isTrue();
        Assertions.assertThat( mvc.getObject("chat_id").get() ).isEqualTo(chatId);
    }

    @Test
    public void CallBackQueryUpdateHandlerShouldReturnModelAndViewWithWordDefinitionDetailView() throws BotException, IOException {
        Mockito.when( update.getCallbackQuery().getData() ).thenReturn(wordCommandText);
        Mockito.when( update.getCallbackQuery().getMessage().getChatId() ).thenReturn( chatId );
        Mockito.when( wordDefinitionService.defineWord(argumentText)).thenReturn( Optional.of( WordCompositionComposer.build()) );
        ModelAndView mvc = callBackQueryUpdateHandler.execute(update);

        Assertions.assertThat( mvc.getView() ).isInstanceOf(WordDefinitionDetailsView.class);
    }

    @Test
    public void CallBackQueryUpdateHandlerShouldReturnNullModelAndViewWhenWordIsNotFound() throws BotException, IOException {
        Mockito.when( update.getCallbackQuery().getData() ).thenReturn(wordCommandText);
        Mockito.when( update.getCallbackQuery().getMessage().getChatId() ).thenReturn( chatId );
        Mockito.when( wordDefinitionService.defineWord(argumentText)).thenReturn( Optional.empty() );
        ModelAndView mvc = callBackQueryUpdateHandler.execute(update);

        Assertions.assertThat( mvc ).isNull();
    }
}

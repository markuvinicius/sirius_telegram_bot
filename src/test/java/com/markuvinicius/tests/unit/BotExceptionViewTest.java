package com.markuvinicius.tests.unit;

import com.markuvinicius.composer.ModelAndViewBotExceptionComposer;
import com.markuvinicius.exceptions.*;
import com.markuvinicius.mvc.ModelAndView;
import com.markuvinicius.views.ResponseView;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractComparableAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BotExceptionViewTest extends BasicUnitTest{

    @Test
    public void renderShouldReturnSendMessage(){
        ModelAndView modelAndView = ModelAndViewBotExceptionComposer.build( new WordNotFoundException("foo"));

        ResponseView view = modelAndView.getView();
        ModelMap map = modelAndView.getModelObjects();

        Assertions.assertThat(view.render(map) ).isInstanceOf(SendMessage.class);
    }

    @Test
    public void botExceptionShouldHaveMessageText(){
        ModelAndView modelAndView = ModelAndViewBotExceptionComposer.build( new WordNotFoundException("foo"));

        ResponseView view = modelAndView.getView();
        ModelMap map = modelAndView.getModelObjects();

        SendMessage message = view.render(map);
        Assertions.assertThat( message.getText() ).isNotBlank();
    }

    @Test
    public void wordNotFoundExceptionViewTest(){
        BotException exception = new WordNotFoundException("word not found");
        botExceptionBasicTest(exception);
    }

    @Test
    public void botCommandNotFoundExceptionViewTest(){
        BotException exception = new BotCommandNotFoundException("command not found");
        botExceptionBasicTest(exception);
    }

    @Test
    public void environmentVariablesNotFoundExceptionViewTest(){
        BotException exception = new EnvironmentVariablesNotFoundException("environment not found");
        botExceptionBasicTest(exception);
    }

    @Test
    public void wordServiceNotFoundExceptionViewTest(){
        BotException exception = new WordServiceNotAvailableException("environment not found");
        botExceptionBasicTest(exception);
    }

    private void botExceptionBasicTest( BotException exception){
        ModelAndView modelAndView = ModelAndViewBotExceptionComposer.build( exception );
        String exceptionText = exception.getMessage();


        ResponseView view = modelAndView.getView();
        ModelMap map = modelAndView.getModelObjects();
        SendMessage message = view.render(map);

        Pattern pattern = Pattern.compile(exceptionText);
        Matcher matcher = pattern.matcher(message.getText());

        Assertions.assertThat( message.getText() ).isNotBlank();
        Assertions.assertThat( message.getChatId() ).isNotBlank();
        Assertions.assertThat( matcher.find() ).isTrue();
    }

}

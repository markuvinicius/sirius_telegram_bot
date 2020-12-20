package com.markuvinicius.tests.unit;

import com.markuvinicius.composer.ModelAndViewWordComposer;
import com.markuvinicius.views.ResponseView;
import com.markuvinicius.views.implementation.WordDefinitionsView;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordDefinitionsViewTest extends BasicUnitTest{

    private ModelMap modelMap;
    private ResponseView responseView;

    @Before
    public void setup(){
        modelMap = ModelAndViewWordComposer.build().getModelObjects();
        responseView = new WordDefinitionsView();
    }

    @Test
    public void renderShouldReturnSendMessage(){
        Assertions.assertThat( responseView.render(modelMap) ).isInstanceOf(SendMessage.class);
    }

    @Test
    public void messageParseModeShoulHaveAtLeastOnePartOfSpeech(){
        SendMessage message = responseView.render(modelMap);
        String messageText = message.getText();
        String regex = "<b>#";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(messageText);

        Assertions.assertThat( matcher.find() ).isTrue();
    }

    @Test
    public void messageTextShouldHaveWordDefinition(){
        SendMessage message = responseView.render(modelMap);
        String messageText = message.getText();
        String regex = "word";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(messageText);

        Assertions.assertThat( matcher.find() ).isTrue();
    }

    @Test
    public void messageTextShouldHavePronuntiation(){
        SendMessage message = responseView.render(modelMap);
        String messageText = message.getText();
        String regex = "pronunciation";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(messageText);

        Assertions.assertThat( matcher.find() ).isTrue();
    }

}

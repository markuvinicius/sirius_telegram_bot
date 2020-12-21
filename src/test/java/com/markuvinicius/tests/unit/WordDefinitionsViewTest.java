package com.markuvinicius.tests.unit;

import com.markuvinicius.composer.ModelAndViewWordCompositionComposer;
import com.markuvinicius.composer.ModelAndViewWordDefinitionComposer;
import com.markuvinicius.views.ResponseView;
import com.markuvinicius.views.implementation.WordDefinitionsView;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordDefinitionsViewTest extends BasicUnitTest{

    private ModelMap modelMap;
    private ResponseView responseView;

    private final String OBJECT_KEY_NAME = "word_composition";

    @Before
    public void setup(){
        modelMap = ModelAndViewWordCompositionComposer.build().getModelObjects();
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

    @Test
    public void messageTextShouldHaveAtLeastOneReplyKeyBoard(){
        SendMessage message = responseView.render(modelMap);

        InlineKeyboardMarkup replyKeyboard = (InlineKeyboardMarkup) message.getReplyMarkup();
        Assertions.assertThat( replyKeyboard.getKeyboard() ).isNotEmpty();
    }

    @Test
    public void messageTextShouldHaveOneReplyKeyBoardButton(){
        SendMessage message = responseView.render(modelMap);

        InlineKeyboardMarkup replyKeyboard = (InlineKeyboardMarkup) message.getReplyMarkup();
        InlineKeyboardButton keyboardButton = (InlineKeyboardButton) replyKeyboard.getKeyboard().get(0).get(0);

        Assertions.assertThat( keyboardButton.getText() ).isNotBlank();
        Assertions.assertThat( keyboardButton.getCallbackData() ).isNotBlank();
        //Assertions.assertThat( keyboardButton.getUrl() ).isNotBlank();
    }

}

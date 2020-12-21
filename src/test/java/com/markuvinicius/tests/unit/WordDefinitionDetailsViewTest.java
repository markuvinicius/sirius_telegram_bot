package com.markuvinicius.tests.unit;

import com.markuvinicius.composer.ModelAndViewWordCompositionComposer;
import com.markuvinicius.composer.ModelAndViewWordDefinitionComposer;
import com.markuvinicius.views.ResponseView;
import com.markuvinicius.views.implementation.WordDefinitionDetailsView;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordDefinitionDetailsViewTest extends BasicUnitTest{

    private ModelMap modelMap;
    private ResponseView responseView;
    private final String OBJECT_KEY_NAME = "word_definition";

    @Before
    public void setup(){
        modelMap = ModelAndViewWordDefinitionComposer.build().getModelObjects();
        responseView = new WordDefinitionDetailsView();
    }

    @Test
    public void renderShouldReturnSendMessage(){
        Assertions.assertThat( responseView.render(modelMap) ).isInstanceOf(SendMessage.class);
    }

    //TODO Create some tests to validate WordDefinitionDetails format
}

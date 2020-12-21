package com.markuvinicius.tests.unit;

import com.markuvinicius.command.implementation.WordCommand;
import com.markuvinicius.composer.WordCompositionComposer;
import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.exceptions.WordNotFoundException;
import com.markuvinicius.exceptions.WordServiceNotAvailableException;
import com.markuvinicius.models.words.WordComposition;
import com.markuvinicius.mvc.ModelAndView;
import com.markuvinicius.services.WordDefinitionService;
import com.markuvinicius.views.implementation.WordDefinitionsView;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Optional;

public class WordCommandTest extends BasicUnitTest{
    @Mock
    private WordDefinitionService wordDefinitionService;
    @InjectMocks
    private WordCommand wordCommand;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private String wordToSearch;

    @Before
    public void setup(){
        this.wordToSearch = new String("foo");
    }

    @Test
    public void executeShouldThrowExceptionWhenWordServiceIsNotAvailable() throws IOException, BotException {
        thrown.expect(WordServiceNotAvailableException.class);
        thrown.expectMessage("It was not possible to connect to WordService: foo");
        Mockito.when( wordDefinitionService.defineWord(wordToSearch) ).thenThrow( new IOException("foo"));

        wordCommand.execute(wordToSearch);
    }

    @Test
    public void executeShouldThrowExceptionWhenWordIsNotFound() throws IOException, BotException {
        thrown.expect(WordNotFoundException.class);
        thrown.expectMessage("Word " + wordToSearch + " - Not Found");

        Mockito.when( wordDefinitionService.defineWord(wordToSearch)).thenReturn(Optional.empty());
        wordCommand.execute(wordToSearch);
    }

    @Test
    public void executeShouldReturnMVCWithWordCompositionWhenFound() throws IOException, BotException {
        WordComposition wordComposition = WordCompositionComposer.build();
        Mockito.when( wordDefinitionService.defineWord(wordToSearch) ).thenReturn(Optional.of(wordComposition));

        ModelAndView mvc = wordCommand.execute(wordToSearch);
        Assertions.assertThat( mvc.getModelObjects().getAttribute("word_composition") ).isInstanceOf(WordComposition.class);
        Assertions.assertThat( mvc.getModelObjects().getAttribute("word_composition") ).isEqualTo(wordComposition);
        Assertions.assertThat( mvc.getView() ).isInstanceOf(WordDefinitionsView.class);
    }

}

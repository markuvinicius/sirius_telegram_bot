package com.markuvinicius.tests.unit;

import com.markuvinicius.command.BotCommand;
import com.markuvinicius.command.implementation.CommandFactoryImpl;
import com.markuvinicius.command.implementation.WordCommand;
import com.markuvinicius.exceptions.BotCommandNotFoundException;
import com.markuvinicius.exceptions.BotException;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class CommandFactoryImplTest extends BasicUnitTest {
    @Mock
    private WordCommand wordCommand;
    @InjectMocks
    private CommandFactoryImpl commandFactory;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();


    @Test
    public void getCommandWithWordArgumentShouldReturnWordCommand() throws BotException {
        BotCommand command = commandFactory.getCommand("word");
        Assertions.assertThat( command ).isInstanceOf(WordCommand.class);
    }

    @Test
    public void getCommandWithNotFoundCommandShoulrReturnException() throws BotException {
        thrown.expect(BotCommandNotFoundException.class);
        thrown.expectMessage("Command foo - NOT FOUND");

        BotCommand command = commandFactory.getCommand("foo");
    }
}

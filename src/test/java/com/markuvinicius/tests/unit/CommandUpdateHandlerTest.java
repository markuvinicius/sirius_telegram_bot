package com.markuvinicius.tests.unit;

import com.markuvinicius.exceptions.BotException;
import com.markuvinicius.handlers.implementation.CommandUpdateHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest
public class CommandUpdateHandlerTest {

    @Mock
    private Update update;

    @Autowired
    private CommandUpdateHandler commandUpdateHandler;

    @Test
    public void test() throws BotException {
        //given
        Mockito.when( update.hasMessage() ).thenReturn(true);
        Mockito.when( update.getMessage() ).thenReturn( new Message() );
        Mockito.when( update.getMessage().hasEntities() ).thenReturn(true);

        commandUpdateHandler.execute(update);

    }
}

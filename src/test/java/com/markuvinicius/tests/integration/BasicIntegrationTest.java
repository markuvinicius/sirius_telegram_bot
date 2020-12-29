package com.markuvinicius.tests.integration;

import com.markuvinicius.bot.BotController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class BasicIntegrationTest {

    @Autowired
    private BotController botController;

    @Test
    public void testBotController(){

    }

}

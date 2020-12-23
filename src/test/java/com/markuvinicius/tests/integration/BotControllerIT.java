package com.markuvinicius.tests.integration;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BotControllerIT {

    @Test
    public void test(){
        Assertions.assertThat(true).isTrue();
    }
}

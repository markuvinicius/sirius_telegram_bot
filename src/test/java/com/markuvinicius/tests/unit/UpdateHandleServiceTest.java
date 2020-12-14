package com.markuvinicius.tests.unit;

import com.markuvinicius.services.UpdateHandleService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest
public class UpdateHandleServiceTest {

    @Autowired
    private UpdateHandleService service;

}

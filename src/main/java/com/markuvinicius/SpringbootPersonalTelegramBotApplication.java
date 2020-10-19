package com.markuvinicius;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class SpringbootPersonalTelegramBotApplication implements CommandLineRunner {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(SpringbootPersonalTelegramBotApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
	}
}

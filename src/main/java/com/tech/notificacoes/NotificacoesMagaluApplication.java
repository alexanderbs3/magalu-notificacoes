package com.tech.notificacoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
    public class NotificacoesMagaluApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificacoesMagaluApplication.class, args);
	}

}

package com.BookStore.OnlineBookExchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OnlineBookExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineBookExchangeApplication.class, args);
	}

}

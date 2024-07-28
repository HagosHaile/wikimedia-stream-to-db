package com.wikimedia.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WikimediaStreamToDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(WikimediaStreamToDbApplication.class, args);
	}

}

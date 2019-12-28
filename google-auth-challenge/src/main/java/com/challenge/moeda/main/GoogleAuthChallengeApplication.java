package com.challenge.moeda.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.challenge"})
public class GoogleAuthChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoogleAuthChallengeApplication.class, args);
	}

}

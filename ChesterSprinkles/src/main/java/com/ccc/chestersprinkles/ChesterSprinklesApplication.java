package com.ccc.chestersprinkles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ccc.chestersprinkles.carnivaltours.service.CTAdventureSessionService;
import com.ccc.chestersprinkles.service.SlackUserService;

@SpringBootApplication(scanBasePackages = {"me.ramswaroop.jbot", "com.ccc.chestersprinkles"})
public class ChesterSprinklesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChesterSprinklesApplication.class, args);
	}
	
	@Bean
	public SlackUserService slackUserService() {
		return new SlackUserService();
	}
	
	@Bean
	public CTAdventureSessionService ctAdventureSessionService() {
		return new CTAdventureSessionService();
	}
}

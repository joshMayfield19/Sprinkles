package com.ccc.chestersprinkles;

import java.sql.DriverManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.sqlite.JDBC;
import org.sqlite.SQLiteJDBCLoader;

import com.ccc.chestersprinkles.carnivaltours.service.CTAdventureSessionService;
import com.ccc.chestersprinkles.service.SlackUserService;

@SpringBootApplication(scanBasePackages = {"me.ramswaroop.jbot", "com.ccc.chestersprinkles"})
public class ChesterSprinklesApplication {

	public static void main(String[] args) throws Exception {
		initializeSqlite();
		SpringApplication.run(ChesterSprinklesApplication.class, args);
	}
	
	private static void initializeSqlite() throws Exception {
		SQLiteJDBCLoader.initialize();
		DriverManager.registerDriver(new JDBC());
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

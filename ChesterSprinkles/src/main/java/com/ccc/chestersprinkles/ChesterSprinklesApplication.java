package com.ccc.chestersprinkles;

import java.sql.DriverManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.sqlite.JDBC;
import org.sqlite.SQLiteJDBCLoader;

import com.ccc.chestersprinkles.dao.ChallengeDao;
import com.ccc.chestersprinkles.dao.PirateDao;
import com.ccc.chestersprinkles.dao.PresentationDao;
import com.ccc.chestersprinkles.dao.SlackUserDao;
import com.ccc.chestersprinkles.dao.SqliteDao;
import com.ccc.chestersprinkles.service.PirateService;
import com.ccc.chestersprinkles.service.ChallengeService;
import com.ccc.chestersprinkles.service.PresentationService;
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
		SqliteDao sqliteDao = new SqliteDao();
		sqliteDao.buildDatabase();
	}
	
	@Bean
	public SlackUserService slackUserService() {
		return new SlackUserService();
	}
	
	@Bean
	public SlackUserDao slackUserDao() {
		return new SlackUserDao();
	}
	
	@Bean
	public ChallengeService challengeService() {
		return new ChallengeService();
	}
	
	@Bean
	public ChallengeDao challengeDao() {
		return new ChallengeDao();
	}
	
	@Bean
	public PresentationService presentationService() {
		return new PresentationService();
	}
	
	@Bean
	public PresentationDao presentationDao() {
		return new PresentationDao();
	}
	
	@Bean
	public PirateService pirateService() {
		return new PirateService();
	}
	
	@Bean
	public PirateDao pirateDao() {
		return new PirateDao();
	}
}

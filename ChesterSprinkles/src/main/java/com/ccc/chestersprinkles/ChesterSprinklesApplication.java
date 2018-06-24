package com.ccc.chestersprinkles;

import java.sql.DriverManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.sqlite.JDBC;
import org.sqlite.SQLiteJDBCLoader;

import com.ccc.chestersprinkles.dao.ChallengeDao;
import com.ccc.chestersprinkles.dao.ChallengeIdeaDao;
import com.ccc.chestersprinkles.dao.CommandIdeaDao;
import com.ccc.chestersprinkles.dao.ParrotPhraseDao;
import com.ccc.chestersprinkles.dao.PirateDao;
import com.ccc.chestersprinkles.dao.PiratePointsHistoryDao;
import com.ccc.chestersprinkles.dao.PirateShipDao;
import com.ccc.chestersprinkles.dao.PresentationDao;
import com.ccc.chestersprinkles.dao.SlackUserDao;
import com.ccc.chestersprinkles.dao.SqliteDao;
import com.ccc.chestersprinkles.service.PirateService;
import com.ccc.chestersprinkles.service.PirateShipService;
import com.ccc.chestersprinkles.service.ChallengeIdeaService;
import com.ccc.chestersprinkles.service.ChallengeService;
import com.ccc.chestersprinkles.service.CommandIdeaService;
import com.ccc.chestersprinkles.service.ParrotPhraseService;
import com.ccc.chestersprinkles.service.PiratePointsHistoryService;
import com.ccc.chestersprinkles.service.PresentationService;
import com.ccc.chestersprinkles.service.SlackUserService;

@SpringBootApplication(scanBasePackages = {"me.ramswaroop.jbot", "com.ccc.chestersprinkles", "com.ccc.chestersprinkles.utility"})
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
	
	@Bean
	public PirateShipService pirateShipService() {
		return new PirateShipService();
	}
	
	@Bean
	public PirateShipDao pirateShipDao() {
		return new PirateShipDao();
	}
	
	@Bean
	public ParrotPhraseService parrotPhraseService() {
		return new ParrotPhraseService();
	}
	
	@Bean
	public ParrotPhraseDao parrotPhraseDao() {
		return new ParrotPhraseDao();
	}
	
	@Bean
	public CommandIdeaService commandIdeaService() {
		return new CommandIdeaService();
	}
	
	@Bean
	public CommandIdeaDao commandIdeaDao() {
		return new CommandIdeaDao();
	}
	
	@Bean
	public ChallengeIdeaService challengeIdeaService() {
		return new ChallengeIdeaService();
	}
	
	@Bean
	public ChallengeIdeaDao challengeIdeaDao() {
		return new ChallengeIdeaDao();
	}
	
	@Bean
	public PiratePointsHistoryService piratePointsHistoryService() {
		return new PiratePointsHistoryService();
	}
	
	@Bean
	public PiratePointsHistoryDao piratePointsHistoryDao() {
		return new PiratePointsHistoryDao();
	}
}

package com.ccc.chestersprinkles;

import java.sql.DriverManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.sqlite.JDBC;
import org.sqlite.SQLiteJDBCLoader;

import com.ccc.chestersprinkles.dao.BottleEventDao;
import com.ccc.chestersprinkles.dao.ChallengeDao;
import com.ccc.chestersprinkles.dao.ChallengeIdeaDao;
import com.ccc.chestersprinkles.dao.CommandIdeaDao;
import com.ccc.chestersprinkles.dao.DoubloonActivityDao;
import com.ccc.chestersprinkles.dao.DoubloonItemHistoryDao;
import com.ccc.chestersprinkles.dao.DoubloonShopItemDao;
import com.ccc.chestersprinkles.dao.PointEventDao;
import com.ccc.chestersprinkles.dao.ParrotPhraseDao;
import com.ccc.chestersprinkles.dao.PirateDao;
import com.ccc.chestersprinkles.dao.PiratePointsHistoryDao;
import com.ccc.chestersprinkles.dao.PirateShipDao;
import com.ccc.chestersprinkles.dao.PresentationDao;
import com.ccc.chestersprinkles.dao.SlackUserDao;
import com.ccc.chestersprinkles.dao.SqliteDao;
import com.ccc.chestersprinkles.service.PirateService;
import com.ccc.chestersprinkles.service.PirateShipService;
import com.ccc.chestersprinkles.service.BottleEventService;
import com.ccc.chestersprinkles.service.ChallengeIdeaService;
import com.ccc.chestersprinkles.service.ChallengeService;
import com.ccc.chestersprinkles.service.CommandIdeaService;
import com.ccc.chestersprinkles.service.DoubloonActivityService;
import com.ccc.chestersprinkles.service.DoubloonItemHistoryService;
import com.ccc.chestersprinkles.service.DoubloonShopItemService;
import com.ccc.chestersprinkles.service.PointEventService;
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
}

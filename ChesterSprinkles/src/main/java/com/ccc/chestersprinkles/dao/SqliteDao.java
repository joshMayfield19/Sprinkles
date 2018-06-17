package com.ccc.chestersprinkles.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class SqliteDao {
	public static final String JDBC_STRING = "jdbc:sqlite:./chester.sqlite";
	
	public void buildDatabase() throws FileNotFoundException, IOException, SQLException {
		File db = new File("chester.sqlite");
		File build = new File("build.sql");

		if (!build.exists()) {
			BufferedInputStream buildScriptIn = new BufferedInputStream(this.getClass().getResourceAsStream("/build.sql"));
			BufferedOutputStream buildScriptOut = new BufferedOutputStream(new FileOutputStream(build));
			IOUtils.copy(buildScriptIn, buildScriptOut);
	
			buildScriptIn.close();
			buildScriptOut.close();
		}

		if (!db.exists()) {
			Connection conn = DriverManager.getConnection(JDBC_STRING);
			BufferedInputStream buildScriptIn = new BufferedInputStream(this.getClass().getResourceAsStream("/build.sql"));
			List<String> buildScript = IOUtils.readLines(buildScriptIn, Charset.defaultCharset());
			buildScriptIn.close();
			
			for (String line : buildScript) {
				if (StringUtils.isNotBlank(line)) {
					conn.createStatement().executeUpdate(line);
				}
			}
		}
	}
	
	public static Connection openDb() throws SQLException {
		return openDb(false);
	}
	
	public static Connection openDb(boolean startTransaction) throws SQLException {
		Connection conn = DriverManager.getConnection(JDBC_STRING);
		
		if (startTransaction) {
			conn.setAutoCommit(false);
		}
		
		return conn;
	}
	
	public static void closeDb(Connection conn) throws SQLException {
		closeDb(conn, false);
	}
	
	public static void closeDb(Connection conn, boolean shouldCommitTransaction) throws SQLException {
		if (shouldCommitTransaction) {
			conn.commit();
		}
		
		conn.close();
	}
}
package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ccc.chestersprinkles.model.Challenge;

public class ChallengeDao {
	private static final String GET_CHALLENGES = "select challenge_id, name, challenge_link, presentation_date, is_current from challenge";
	private static final String GET_CURRENT_CHALLENGE = "select challenge_id, name, challenge_link, presentation_date, is_current from challenge where is_current=1";
	
	public List<Challenge> getChallenges() throws SQLException {
		List<Challenge> challenges = new ArrayList<Challenge>(); 
		
		Connection con = null;
		Statement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(GET_CHALLENGES);
	        while (rs.next()) {
	            Challenge challenge = new Challenge();
	            challenge.setChallengeId(rs.getInt("challenge_id"));
	            challenge.setChallengeName(rs.getString("name"));
	            challenge.setChallengeLink(rs.getString("challenge_link"));
	            challenge.setChallengeDate(rs.getString("presentation_date"));
	            challenge.setCurrent(rs.getInt("is_current") == 1 ? true : false);
	            challenges.add(challenge);
	        }
	        
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) { 
	        	stmt.close(); 
	    		SqliteDao.closeDb(con);
	        }
	    }
	    
	    return challenges;
	}
	
	public Challenge getCurrentChallenge() throws SQLException {
		Challenge challenge = new Challenge();
		
		Connection con = null;
		Statement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.createStatement();
	    	ResultSet rs = stmt.executeQuery(GET_CURRENT_CHALLENGE);
	        
	    	while (rs.next()) {
	            challenge.setChallengeId(rs.getInt("challenge_id"));
	        	challenge.setChallengeName(rs.getString("name"));
	        	challenge.setChallengeLink(rs.getString("challenge_link"));
	        	challenge.setChallengeDate(rs.getString("presentation_date"));
	        }
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) { 
	        	stmt.close(); 
	    		SqliteDao.closeDb(con);
	        }
	    }
	    
	    return challenge;
	}
}

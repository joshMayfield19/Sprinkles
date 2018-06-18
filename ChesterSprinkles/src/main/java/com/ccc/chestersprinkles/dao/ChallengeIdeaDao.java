package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ccc.chestersprinkles.model.ChallengeIdea;

public class ChallengeIdeaDao {
	private static final String GET_ALL_CHALLENGES = "select ch_idea_id, user_id, description from challenge_idea";
	
	public List<ChallengeIdea> getAllIdeas() throws SQLException {
		List<ChallengeIdea> challengeIdeas = new ArrayList<ChallengeIdea>(); 
		
		Connection con = null;
		Statement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(GET_ALL_CHALLENGES);
	        while (rs.next()) {
	            ChallengeIdea challengeIdea = new ChallengeIdea();
	            challengeIdea.setChallengeIdeaId(rs.getInt("ch_idea_id"));
	            challengeIdea.setUserId(rs.getInt("user_id"));
	            challengeIdea.setIdea(rs.getString("description"));
	            challengeIdeas.add(challengeIdea);
	        }
	        
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) { 
	        	stmt.close(); 
	    		SqliteDao.closeDb(con);
	        }
	    }
	    
	    return challengeIdeas;
	}
}

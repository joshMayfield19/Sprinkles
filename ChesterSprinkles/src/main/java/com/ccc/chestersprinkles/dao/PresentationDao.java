package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ccc.chestersprinkles.model.Presentation;

public class PresentationDao {
	private static final String GET_PRESENTATION_BY_CHALLENGE = "select presentation_id, submitter_user_id, challenge_id "
			+ "from presentation where challenge_id=?";
	
	public Presentation getPresentationByChallengeId(int challengeId) throws SQLException {
		Presentation presentation = new Presentation();
		
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(GET_PRESENTATION_BY_CHALLENGE);
	    	stmt.setInt(1, challengeId);
	    	
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            presentation.setPresentationId(rs.getInt("presentation_id"));
	            presentation.setPresenterId(rs.getInt("submitter_user_id"));
	            presentation.setChallengeId(rs.getInt("challenge_id"));
	        }
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) { 
	        	stmt.close(); 
	    		SqliteDao.closeDb(con);
	        }
	    }
	    
	    return presentation;
	}
}

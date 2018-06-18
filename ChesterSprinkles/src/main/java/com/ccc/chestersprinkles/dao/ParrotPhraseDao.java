package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ccc.chestersprinkles.model.ParrotPhrase;

public class ParrotPhraseDao {
	private static final String GET_PHRASE_BY_ID = "select phrase_id, phrase from parrot_phrase where phrase_id = ?";
	
	public ParrotPhrase getPhraseById(int phraseId) throws SQLException {
		ParrotPhrase parrotPhrase = new ParrotPhrase();
		
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(GET_PHRASE_BY_ID);
	    	stmt.setInt(1, phraseId);
	    	
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	        	parrotPhrase.setPhraseId(rs.getInt("phrase_id"));
	        	parrotPhrase.setPhrase(rs.getString("phrase"));
	        }
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) { 
	        	stmt.close(); 
	    		SqliteDao.closeDb(con);
	        }
	    }
	    
	    return parrotPhrase;
	}
}

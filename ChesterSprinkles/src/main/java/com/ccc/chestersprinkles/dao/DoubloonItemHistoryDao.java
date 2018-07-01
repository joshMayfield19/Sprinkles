package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoubloonItemHistoryDao {
	private static final String ADD_NEW_EVENT = "INSERT INTO doubloon_shop_hist (pirate_id, command, price, notes) "
			+ "VALUES (?, ?, ?, ?)";
		
	public void addNewEvent(int pirateId, String command, String notes, int price) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(ADD_NEW_EVENT);
	    	
	    	stmt.setInt(1, pirateId);
	    	stmt.setString(2,  command);
	    	stmt.setInt(3, price);
	    	stmt.setString(4,  notes);

			// execute update SQL stetement
	    	stmt.executeUpdate();
	    	
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
		    		SqliteDao.closeDb(con);
	        	} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	        }
	    }
	}
}

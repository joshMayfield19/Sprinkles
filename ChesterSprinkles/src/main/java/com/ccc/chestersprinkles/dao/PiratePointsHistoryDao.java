package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ccc.chestersprinkles.model.PiratePointsHistory;

public class PiratePointsHistoryDao {
	private static final String ADD_NEW_EVENT = "INSERT INTO pirate_points_hist (pirate_id, points, event, date_of_event) "
			+ "VALUES (?, ?, ?, ?)";
		
	public void addNewEvent(PiratePointsHistory piratePointsHistory) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(ADD_NEW_EVENT);
	    	
	    	stmt.setInt(1, piratePointsHistory.getPirateId());
	    	stmt.setInt(2,  piratePointsHistory.getPoints());
	    	stmt.setString(3, piratePointsHistory.getEvent());
	    	stmt.setString(4,  piratePointsHistory.getDateOfEvent());

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

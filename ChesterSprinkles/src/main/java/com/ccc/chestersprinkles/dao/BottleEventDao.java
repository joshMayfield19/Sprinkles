package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ccc.chestersprinkles.model.BottleEvent;

public class BottleEventDao {
	private static final String GET_BOTTLE_EVENT_BY_PIRATE_ID = "select bottle_event_id, bottle_start_date, bottle_end_date, doub_reward, point_reward, "
													+ "pirate_id, collected from bottle_event where pirate_id = ?";
	private static final String INSERT_NEW_EVENT = "insert into bottle_event (bottle_start_date, bottle_end_date, doub_reward, point_reward, pirate_id, collected) "
			+ "values (?,?,?,?,?,?)";
	private static final String UPDATE_DOUB = "update bottle_event set doub_reward = ?, collected = 1 where pirate_id = ?";
	private static final String UPDATE_POINT = "update bottle_event set point_reward = ?, collected = 1 where pirate_id = ?";
	private static final String UPDATE_DATES = "update bottle_event set bottle_start_date = ?, bottle_end_date = ?, collected = ? where pirate_id = ?";
	
	public BottleEvent getBottleEventByPirateId(int pirateId) {
		BottleEvent bottleEvent = new BottleEvent();
		
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(GET_BOTTLE_EVENT_BY_PIRATE_ID);
	    	stmt.setInt(1, pirateId);
	    	
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            bottleEvent.setBottleEventId(rs.getInt(1));
	            bottleEvent.setBottleStartDate(rs.getString(2));
	            bottleEvent.setBottleEndDate(rs.getString(3));
	            bottleEvent.setDoubReward(rs.getInt(4));
	            bottleEvent.setPointReward(rs.getInt(5));
	            bottleEvent.setPirateId(rs.getInt(6));
	            bottleEvent.setRewardCollected(rs.getInt(7) == 1 ? true : false);
	        }
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
	    
	    return bottleEvent;
	}
	
	public void updateDoubloons(int doubloons, int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_DOUB);
	    	
	    	stmt.setInt(1, doubloons);
	    	stmt.setInt(2, pirateId);

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
	
	public void updatePoints(int points, int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_POINT);
	    	
	    	stmt.setInt(1, points);
	    	stmt.setInt(2, pirateId);

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
	
	public void updateDates(String startDate, String endDate, int collected, int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_DATES);
	    	
	    	stmt.setString(1, startDate);
	    	stmt.setString(2, endDate);
	    	stmt.setInt(3, collected);
	    	stmt.setInt(4, pirateId);

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
	
	public void addNewBottleEvent(BottleEvent bottleEvent) {
		Connection con = null;
		PreparedStatement stmt = null;
		
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(INSERT_NEW_EVENT);
	    	
	    	stmt.setString(1, bottleEvent.getBottleStartDate());
	    	stmt.setString(2, bottleEvent.getBottleEndDate());
	    	stmt.setInt(3, bottleEvent.getDoubReward());
	    	stmt.setInt(4, bottleEvent.getPointReward());
	    	stmt.setInt(5, bottleEvent.getPirateId());
	    	stmt.setInt(6, bottleEvent.isRewardCollected() ? 1 : 0);
	    	
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

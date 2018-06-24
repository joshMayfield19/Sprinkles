package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ccc.chestersprinkles.model.Pirate;

public class PirateDao {
	private static final String GET_TOP_FIVE_PIRATES = "select pirate_id, user_id, ship_id, current_points, total_points, "
			+ "doubloons, pirate_name, top_five, captain from pirate where rownum <= 5 order by current_points desc";
	private static final String GET_PIRATE_BY_USER_ID = "select pirate_id, user_id, ship_id, current_points, total_points, "
			+ "doubloons, pirate_name, top_five, captain from pirate where user_id=?";
	private static final String GET_PIRATE_BY_NAME = "select pirate_id, user_id, ship_id, current_points, total_points, "
			+ "doubloons, pirate_name, top_five, captain from pirate inner join slack_user on pirate.user_id = slack_user.slack_user_id "
			+ "where slack_user.first_name=? and slack_user.last_name=?";
	private static final String UPDATE_PIRATE_POINTS = "update pirate set total_points = ?, current_points = ? where user_id = ?";
	
	public List<Pirate> getTopFivePirates() throws SQLException {
		List<Pirate> pirates = new ArrayList<Pirate>(); 
		
		Connection con = null;
		Statement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(GET_TOP_FIVE_PIRATES);
	        while (rs.next()) {
	            Pirate pirate = new Pirate();
	            pirate.setPirateId(rs.getInt("pirate_id"));
	            pirate.setUserId(rs.getInt("user_id"));
	            pirate.setPirateShipId(rs.getInt("ship_id"));
	            pirate.setPiratePoints(rs.getInt("current_points"));
	            pirate.setOverallPiratePoints(rs.getInt("total_points"));
	            pirate.setDoubloons(rs.getInt("doubloons"));
	            pirate.setPirateName(rs.getString("pirate_name"));
	            pirate.setTopFivePirate(rs.getInt("top_five") == 1 ? true : false);
	            pirate.setCaptain(rs.getInt("captain") == 1 ? true : false);
	            pirates.add(pirate);
	        }
	        
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) { 
	        	stmt.close(); 
	    		SqliteDao.closeDb(con);
	        }
	    }
	    
	    return pirates;
	}
	
	public Pirate getPirateByUser(int userId) throws SQLException {
		Pirate pirate = new Pirate();
		
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(GET_PIRATE_BY_USER_ID);
	    	stmt.setInt(1, userId);
	    	
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            pirate.setPirateId(rs.getInt("pirate_id"));
	            pirate.setUserId(rs.getInt("user_id"));
	            pirate.setPirateShipId(rs.getInt("ship_id"));
	            pirate.setPiratePoints(rs.getInt("current_points"));
	            pirate.setOverallPiratePoints(rs.getInt("total_points"));
	            pirate.setDoubloons(rs.getInt("doubloons"));
	            pirate.setPirateName(rs.getString("pirate_name"));
	            pirate.setTopFivePirate(rs.getInt("top_five") == 1 ? true : false);
	            pirate.setCaptain(rs.getInt("captain") == 1 ? true : false);
	        }
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) { 
	        	stmt.close(); 
	    		SqliteDao.closeDb(con);
	        }
	    }
	    
	    return pirate;
	}
	
	public Pirate getPirateByName(String firstName, String lastName) {
		Pirate pirate = new Pirate();
		
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(GET_PIRATE_BY_NAME);
	    	stmt.setString(1, firstName);
	    	stmt.setString(2, lastName);
	    	
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            pirate.setPirateId(rs.getInt("pirate_id"));
	            pirate.setUserId(rs.getInt("user_id"));
	            pirate.setPirateShipId(rs.getInt("ship_id"));
	            pirate.setPiratePoints(rs.getInt("current_points"));
	            pirate.setOverallPiratePoints(rs.getInt("total_points"));
	            pirate.setDoubloons(rs.getInt("doubloons"));
	            pirate.setPirateName(rs.getString("pirate_name"));
	            pirate.setTopFivePirate(rs.getInt("top_five") == 1 ? true : false);
	            pirate.setCaptain(rs.getInt("captain") == 1 ? true : false);
	        }
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } catch (Exception ex) {
	    	
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
	    
	    return pirate;
	}
	
	public void updatePoints(int points, int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_PIRATE_POINTS);
	    	
	    	stmt.setInt(1, points);
	    	stmt.setInt(2,  points);
	    	stmt.setInt(3, pirateId);

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

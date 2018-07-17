package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ccc.chestersprinkles.model.PirateShip;

public class PirateShipDao {
	private static final String GET_TOP_SHIPS = "select ship_id, name, captain_user_id, ship_points, total_points, crew from pirate_ship order by ship_points desc";
	private static final String GET_SHIP_BY_ID = "select ship_id, name, captain_user_id, ship_points, total_points, crew"
			+ " from pirate_ship where ship_id =?";
	private static final String GET_SHIP_CREW = "select ship_id, min(crew), name from pirate_ship";
	private static final String UPDATE_SHIP_POINTS = "update pirate_ship set ship_points = ?, total_points = ? where ship_id = ?";
	private static final String UPDATE_SHIP_CAPT = "update pirate_ship set captain_user_id = ? where ship_id = ?";
	private static final String UPDATE_SHIP_CREW = "update pirate_ship set crew = ? where ship_id = ?";
	
	public List<PirateShip> getTopShips() throws SQLException {
		List<PirateShip> pirateShips = new ArrayList<PirateShip>(); 
		
		Connection con = null;
		Statement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(GET_TOP_SHIPS);
	        while (rs.next()) {
	            PirateShip pirateShip = new PirateShip();
	            pirateShip.setShipId(rs.getInt("ship_id"));
	            pirateShip.setShipName(rs.getString("name"));
	            pirateShip.setShipCaptain(rs.getInt("captain_user_id"));
	            pirateShip.setShipPoints(rs.getInt("ship_points"));
	            pirateShip.setOverallShipPoints(rs.getInt("total_points"));
	            pirateShip.setShipCrew(rs.getInt("crew"));
	            pirateShips.add(pirateShip);
	        }
	        
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) { 
	        	stmt.close(); 
	    		SqliteDao.closeDb(con);
	        }
	    }
	    
	    return pirateShips;
	}
	
	public PirateShip getShipById(int shipId) throws SQLException {
		PirateShip pirateShip = new PirateShip();
		
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(GET_SHIP_BY_ID);
	    	stmt.setInt(1, shipId);
	    	
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            pirateShip.setShipId(rs.getInt("ship_id"));
	            pirateShip.setShipName(rs.getString("name"));
	            pirateShip.setShipCaptain(rs.getInt("captain_user_id"));
	            pirateShip.setShipPoints(rs.getInt("ship_points"));
	            pirateShip.setOverallShipPoints(rs.getInt("total_points"));
	            pirateShip.setShipCrew(rs.getInt("crew"));
	        }
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) { 
	        	stmt.close(); 
	    		SqliteDao.closeDb(con);
	        }
	    }
	    
	    return pirateShip;
	}
	
	public void updatePoints(int points, int shipId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_SHIP_POINTS);
	    	
	    	stmt.setInt(1, points);
	    	stmt.setInt(2,  points);
	    	stmt.setInt(3, shipId);

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
	
	public void updateCaptainByShipId (int shipId, int captain) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_SHIP_CAPT);
	    	
	    	stmt.setInt(1, captain);
	    	stmt.setInt(2, shipId);

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
	
	public void updateShipCrew (PirateShip pirateShip) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_SHIP_CREW);
	    	
	    	stmt.setInt(1, pirateShip.getShipCrew());
	    	stmt.setInt(2, pirateShip.getShipId());

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
	
	public PirateShip getShipToAddCrew() {
		PirateShip pirateShip = new PirateShip();
		
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(GET_SHIP_CREW);
	    	
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            pirateShip.setShipId(rs.getInt("ship_id"));
	            pirateShip.setShipCrew(rs.getInt("min(crew)"));
	            pirateShip.setShipName(rs.getString("name"));
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
	    
	    return pirateShip;
	}
}

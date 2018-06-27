package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ccc.chestersprinkles.model.DoubloonActivity;
import com.ccc.chestersprinkles.model.Pirate;
import com.ccc.chestersprinkles.model.SlackUser;

public class PirateDao {
	private static final String GET_TOP_FIVE_PIRATES = "select pirate.pirate_id, pirate.user_id, pirate.current_points, "
			+ "pirate.pirate_name, pirate.top_five, slack_user.first_name, slack_user.last_name, from pirate "
			+ "inner join slack_user on pirate.user_id = slack_user.slack_user_id order by pirate.current_points desc limit 5";
	private static final String GET_PIRATE_BY_SLACK_ID = "select pirate.pirate_id, pirate.user_id, pirate.ship_id, pirate.current_points, pirate.total_points, "
			+ "pirate.doubloons, pirate.pirate_name, pirate.top_five, pirate.captain, pirate.winning_ship, pirate.plank_num, slack_user.first_name, slack_user.last_name, slack_user.slack_id,"
			+ "doubloon_act.command_start, doubloon_act.command_end, doubloon_act.shoreleave_dte, doubloon_act.explore_dte, doubloon_act.battle_dte, doubloon_act.set_sail_dte "
			+ "from pirate "
			+ "inner join slack_user on pirate.user_id = slack_user.slack_user_id "
			+ "inner join doubloon_act on pirate.pirate_id = doubloon_act.pirate_id "
			+ "where slack_user.slack_id=?";
	private static final String GET_PIRATE_BY_SHIP_ID = "select pirate.pirate_id, pirate.user_id, pirate.pirate_name, pirate.ship_id, pirate.current_points, "
			+ "pirate.top_five, pirate.captain, pirate.winning_ship, doubloon_act.command_start, doubloon_act.command_end "
			+ "from pirate "
			+ "inner join doubloon_act on pirate.pirate_id = doubloon_act.pirate_id "
			+ "where pirate.ship_id=?";
	private static final String GET_PIRATE_BY_NAME = "select pirate_id, user_id, ship_id, current_points, total_points, "
			+ "doubloons, pirate_name, top_five, captain, winning_ship, plank_num from pirate inner join slack_user on pirate.user_id = slack_user.slack_user_id "
			+ "where slack_user.first_name=? and slack_user.last_name=?";
	private static final String UPDATE_PIRATE_POINTS = "update pirate set total_points = ?, current_points = ? where user_id = ?";
	private static final String UPDATE_PLANK = "update pirate set plank_num = ? where pirate_id = ?";
	private static final String UPDATE_DOUBLOONS = "update pirate set doubloons = ? where pirate_id = ?";
	private static final String UPDATE_DOUB_ACT = "update pirate set captain = ?, winning_ship = ? where pirate_id = ?";
	
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
	            pirate.setPirateId(rs.getInt(1));
	            pirate.setUserId(rs.getInt(2));
	            pirate.setPiratePoints(rs.getInt(3));
	            pirate.setPirateName(rs.getString(4));
	            pirate.setTopFivePirate(rs.getInt(5) == 1 ? true : false);
	            
	            SlackUser slackUser = new SlackUser();
	            slackUser.setFirstName(rs.getString(6));
	            slackUser.setLastName(rs.getString(7));
	            pirate.setSlackUser(slackUser);
	            
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
	
	public List<Pirate> getPiratesByShipId(int shipId) {
		List<Pirate> pirates = new ArrayList<Pirate>(); 
		
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
	    	con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(GET_PIRATE_BY_SHIP_ID);
	    	stmt.setInt(1, shipId);
	    	
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Pirate pirate = new Pirate();
	            pirate.setPirateId(rs.getInt(1));
	            pirate.setUserId(rs.getInt(2));
	            pirate.setPirateName(rs.getString(3));
	            pirate.setPirateShipId(rs.getInt(4));
	            pirate.setPiratePoints(rs.getInt(5));
	            pirate.setTopFivePirate(rs.getInt(6) == 1 ? true : false);
	            pirate.setCaptain(rs.getInt(7) == 1 ? true : false);
	            pirate.setOnWinningShip(rs.getInt(8) == 1 ? true : false);
	            
	            DoubloonActivity doubloonActivity = new DoubloonActivity();
	            doubloonActivity.setCommandStartDate(rs.getString(9));
	            doubloonActivity.setCommandEndDate(rs.getString(10));
	            pirate.setDoubloonActivity(doubloonActivity);
	            
	            pirates.add(pirate);
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
	    
	    return pirates;
	}
	
	public Pirate getPirateBySlackId(String slackId) throws SQLException {
		Pirate pirate = new Pirate();
		
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(GET_PIRATE_BY_SLACK_ID);
	    	stmt.setString(1, slackId);
	    	
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            pirate.setPirateId(rs.getInt(1));
	            pirate.setUserId(rs.getInt(2));
	            pirate.setPirateShipId(rs.getInt(3));
	            pirate.setPiratePoints(rs.getInt(4));
	            pirate.setOverallPiratePoints(rs.getInt(5));
	            pirate.setDoubloons(rs.getInt(6));
	            pirate.setPirateName(rs.getString(7));
	            pirate.setTopFivePirate(rs.getInt(8) == 1 ? true : false);
	            pirate.setCaptain(rs.getInt(9) == 1 ? true : false);
	            pirate.setOnWinningShip(rs.getInt(10) == 1 ? true : false);
	            pirate.setPlankNum(rs.getInt(11));
	            
	            SlackUser slackUser = new SlackUser();
	            slackUser.setFirstName(rs.getString(12));
	            slackUser.setLastName(rs.getString(13));
	            slackUser.setSlackId(rs.getString(14));
	            pirate.setSlackUser(slackUser);
	            
	            DoubloonActivity doubloonActivity = new DoubloonActivity();
	            doubloonActivity.setCommandStartDate(rs.getString(15));
	            doubloonActivity.setCommandEndDate(rs.getString(16));
	            doubloonActivity.setLastShoreleaveDate(rs.getString(17));
	            doubloonActivity.setLastExploreDate(rs.getString(18));
	            doubloonActivity.setLastBattleDate(rs.getString(19));
	            doubloonActivity.setLastSetSailDate(rs.getString(20));
	            pirate.setDoubloonActivity(doubloonActivity);
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
	            pirate.setOnWinningShip(rs.getInt("winning_ship") == 1 ? true : false);
	            pirate.setPlankNum(rs.getInt("plank_num"));
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
	
	public void updateDoubloonsActivation(Pirate pirate) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_DOUB_ACT);
	    	
	    	stmt.setInt(1, (pirate.isCaptain() ? 1 : 0));
	    	stmt.setInt(2,  (pirate.isOnWinningShip() ? 1 : 0));
	    	stmt.setInt(3, pirate.getPirateId());

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
	
	public void updatePoints(int points, int userId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_PIRATE_POINTS);
	    	
	    	stmt.setInt(1, points);
	    	stmt.setInt(2,  points);
	    	stmt.setInt(3, userId);

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
	
	public void updateWalkThePlank(int plankNum, int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_PLANK);
	    	
	    	stmt.setInt(1, plankNum);
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
	
	public void updateDoubloons(int doubloons, int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_DOUBLOONS);
	    	
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
}

package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ccc.chestersprinkles.model.DoubloonActivity;
import com.ccc.chestersprinkles.model.Pirate;
import com.ccc.chestersprinkles.model.SlackUser;

public class PirateDao {
	private static final String GET_TOP_FIVE_PIRATES = "select pirate.pirate_id, pirate.user_id, pirate.current_points, pirate.total_points, "
			+ "pirate.pirate_name, pirate.top_five, slack_user.first_name, slack_user.last_name from pirate "
			+ "inner join slack_user on pirate.user_id = slack_user.slack_user_id order by pirate.current_points desc limit 5";
	private static final String GET_TOP_PLANK_WALKERS = "select pirate.pirate_id, pirate.user_id, pirate.current_points, pirate.total_points, "
			+ "pirate.pirate_name, pirate.plank_num, slack_user.first_name, slack_user.last_name from pirate "
			+ "inner join slack_user on pirate.user_id = slack_user.slack_user_id order by pirate.plank_num desc limit 5";
	private static final String GET_PIRATE_BY_SLACK_ID = "select pirate.pirate_id, pirate.user_id, pirate.ship_id, pirate.current_points, pirate.total_points, "
			+ "pirate.doubloons, pirate.pirate_name, pirate.top_five, pirate.captain, pirate.winning_ship, pirate.plank_num, pirate.polly, pirate.channel, slack_user.first_name, slack_user.last_name, slack_user.slack_id,"
			+ "doubloon_act.command_start, doubloon_act.command_end, doubloon_act.shoreleave_dte, doubloon_act.explore_dte, doubloon_act.battle_dte, doubloon_act.set_sail_dte,"
			+ "doubloon_act.top_five_start, doubloon_act.top_five_end, doubloon_act.plunder_dte, doubloon_act.grog_dte "
			+ "from pirate "
			+ "inner join slack_user on pirate.user_id = slack_user.slack_user_id "
			+ "inner join doubloon_act on pirate.pirate_id = doubloon_act.pirate_id "
			+ "where slack_user.slack_id=?";
	private static final String GET_PIRATE_BY_SHIP_ID = "select pirate.pirate_id, pirate.user_id, pirate.pirate_name, pirate.ship_id, pirate.current_points, "
			+ "pirate.total_points, pirate.top_five, pirate.captain, pirate.winning_ship, doubloon_act.command_start, doubloon_act.command_end, slack_user.first_name, "
			+ "slack_user.last_name "
			+ "from pirate "
			+ "inner join doubloon_act on pirate.pirate_id = doubloon_act.pirate_id "
			+ "inner join slack_user on pirate.user_id = slack_user.slack_user_id "
			+ "where pirate.ship_id=?";
	private static final String GET_PIRATE_BY_NAME = "select pirate_id, user_id, ship_id, current_points, total_points, "
			+ "doubloons, pirate_name, top_five, captain, winning_ship, plank_num from pirate inner join slack_user on pirate.user_id = slack_user.slack_user_id "
			+ "where slack_user.first_name=? and slack_user.last_name=?";
	private static final String UPDATE_PIRATE_POINTS = "update pirate set total_points = ?, current_points = ? where user_id = ?";
	private static final String UPDATE_PLANK = "update pirate set plank_num = ? where pirate_id = ?";
	private static final String UPDATE_DOUBLOONS = "update pirate set doubloons = ? where pirate_id = ?";
	private static final String UPDATE_DOUB_ACT = "update pirate set captain = ?, winning_ship = ? where pirate_id = ?";
	private static final String UPDATE_TOP_FIVE = "update pirate set top_five = ? where pirate_id = ?";
	private static final String UPDATE_NAME = "update pirate set pirate_name = ?, doubloons = ? where pirate_id = ?";
	private static final String UPDATE_POLLY = "update pirate set polly = 1, doubloons = ? where pirate_id = ?";
	private static final String UPDATE_CHANNEL = "update pirate set channel = ? where pirate_id = ?";
	private static final String UPDATE_PIRATE_POINTS_ZERO = "update pirate set current_points = 0";
	private static final String ADD_NEW_PIRATE = "insert into pirate (user_id, ship_id, current_points, total_points, doubloons, pirate_name, top_five, captain, plank_num, winning_ship, polly, channel) "
			+ "values (last_insert_rowid(),?,?,?,?,?,?,?,?,?,?,?)";
	
	public List<Pirate> getTopFivePirates() {
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
	            pirate.setOverallPiratePoints(rs.getInt(4));
	            pirate.setPirateName(rs.getString(5));
	            pirate.setTopFivePirate(rs.getInt(6) == 1 ? true : false);
	            
	            SlackUser slackUser = new SlackUser();
	            slackUser.setFirstName(rs.getString(7));
	            slackUser.setLastName(rs.getString(8));
	            pirate.setSlackUser(slackUser);
	            
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
	
	public List<Pirate> getTopPlankWalkers() {
		List<Pirate> pirates = new ArrayList<Pirate>(); 
		
		Connection con = null;
		Statement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(GET_TOP_PLANK_WALKERS);
	        while (rs.next()) {
	            Pirate pirate = new Pirate();
	            pirate.setPirateId(rs.getInt(1));
	            pirate.setUserId(rs.getInt(2));
	            pirate.setPiratePoints(rs.getInt(3));
	            pirate.setOverallPiratePoints(rs.getInt(4));
	            pirate.setPirateName(rs.getString(5));
	            pirate.setPlankNum(rs.getInt(6));
	            
	            SlackUser slackUser = new SlackUser();
	            slackUser.setFirstName(rs.getString(7));
	            slackUser.setLastName(rs.getString(8));
	            pirate.setSlackUser(slackUser);
	            
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
	            pirate.setOverallPiratePoints(rs.getInt(6));
	            pirate.setTopFivePirate(rs.getInt(7) == 1 ? true : false);
	            pirate.setCaptain(rs.getInt(8) == 1 ? true : false);
	            pirate.setOnWinningShip(rs.getInt(9) == 1 ? true : false);
	            
	            DoubloonActivity doubloonActivity = new DoubloonActivity();
	            doubloonActivity.setCommandStartDate(rs.getString(10));
	            doubloonActivity.setCommandEndDate(rs.getString(11));
	            pirate.setDoubloonActivity(doubloonActivity);
	            
	            SlackUser slackUser = new SlackUser();
	            slackUser.setFirstName(rs.getString(12));
	            slackUser.setLastName(rs.getString(13));
	            pirate.setSlackUser(slackUser);
	            
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
	
	public Pirate getPirateBySlackId(String slackId) {
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
	            pirate.setCanPolly(rs.getInt(12) == 1 ? true : false);
	            pirate.setChannelId(rs.getString(13));
	            
	            SlackUser slackUser = new SlackUser();
	            slackUser.setFirstName(rs.getString(14));
	            slackUser.setLastName(rs.getString(15));
	            slackUser.setSlackId(rs.getString(16));
	            pirate.setSlackUser(slackUser);
	            
	            DoubloonActivity doubloonActivity = new DoubloonActivity();
	            doubloonActivity.setCommandStartDate(rs.getString(17));
	            doubloonActivity.setCommandEndDate(rs.getString(18));
	            doubloonActivity.setLastShoreleaveDate(rs.getString(19));
	            doubloonActivity.setLastExploreDate(rs.getString(20));
	            doubloonActivity.setLastBattleDate(rs.getString(21));
	            doubloonActivity.setLastSetSailDate(rs.getString(22));
	            doubloonActivity.setTopFiveCommandStartDate(rs.getString(23));
	            doubloonActivity.setTopFiveCommandEndDate(rs.getString(24));
	            doubloonActivity.setLastPlunderDate(rs.getString(25));
	            doubloonActivity.setLastGrogDate(rs.getString(26));
	            pirate.setDoubloonActivity(doubloonActivity);
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
	
	public void updateTopFivePirate(Pirate pirate) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_TOP_FIVE);
	    	
	    	stmt.setInt(1, (pirate.isTopFivePirate() ? 1 : 0));
	    	stmt.setInt(2, pirate.getPirateId());

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
	
	public void updatePoints(int points, int totalPoints, int userId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_PIRATE_POINTS);
	    	
	    	stmt.setInt(1, totalPoints);
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
	
	public void updateNameChange(int pirateId, String name, int doubloons) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_NAME);
	    	
	    	stmt.setString(1, name);
	    	stmt.setInt(2, doubloons);
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
	
	public void updatePollyCommand(int pirateId, int doubloons) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_POLLY);
	    	
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
	
	public void updateChannelId(int pirateId, String channel) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_CHANNEL);
	    	
	    	stmt.setString(1, channel);
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
	
	public void updateZeroPoints() {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_PIRATE_POINTS_ZERO);

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
	
	public void addNewPirate(int shipId, String pirateName) {
		Connection con = null;
		PreparedStatement stmt = null;
		
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(ADD_NEW_PIRATE);
	    	
	    	stmt.setInt(1, shipId);
	    	stmt.setInt(2, 0);
	    	stmt.setInt(3, 0);
	    	stmt.setInt(4, 0);
	    	stmt.setString(5, pirateName);
	    	stmt.setInt(6, 0);
	    	stmt.setInt(7, 0);
	    	stmt.setInt(8, 0);
	    	stmt.setInt(9, 0);
	    	stmt.setInt(10, 0);
	    	stmt.setString(11, StringUtils.EMPTY);
	    	
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

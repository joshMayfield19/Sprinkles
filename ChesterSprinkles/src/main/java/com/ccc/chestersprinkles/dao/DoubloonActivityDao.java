package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

public class DoubloonActivityDao {
	private static final String UPDATE_SHORELEAVE_DATE = "update doubloon_act set shoreleave_dte = ? where pirate_id = ?";
	private static final String UPDATE_BATTLE_DATE = "update doubloon_act set battle_dte = ? where pirate_id = ?";
	private static final String UPDATE_EXPLORE_DATE = "update doubloon_act set explore_dte = ? where pirate_id = ?";
	private static final String UPDATE_SET_SAIL_DATE = "update doubloon_act set set_sail_dte = ? where pirate_id = ?";
	private static final String UPDATE_START_END = "update doubloon_act set command_start = ?, command_end = ? where pirate_id = ?";
	private static final String UPDATE_RESET = "update doubloon_act set command_start = ?, command_end = ?, battle_dte = ?, explore_dte = ?, "
			+ "set_sail_dte = ?, shoreleave_dte = ? where pirate_id = ?";
	private static final String UPDATE_TOP_5_RESET = "update doubloon_act set top_five_start = ?, top_five_end = ?, plunder_dte = ?, grog_dte = ? where pirate_id = ?";
	private static final String UPDATE_TOP_START_END = "update doubloon_act set top_five_start = ?, top_five_end = ? where pirate_id = ?";
	private static final String UPDATE_PLUNDER_DATE = "update doubloon_act set plunder_dte = ? where pirate_id = ?";
	private static final String UPDATE_GROG_DATE = "update doubloon_act set grog_dte = ? where pirate_id = ?";
	private static final String ADD_NEW_ACT = "insert into doubloon_act (pirate_id, command_start, command_end, shoreleave_dte, explore_dte, battle_dte, set_sail_dte, top_five_start, top_five_end,"
			+ " plunder_dte, grog_dte) "
			+ "values (last_insert_rowid(),?,?,?,?,?,?,?,?,?,?)";
	
	public void updateShoreleave(String date, int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_SHORELEAVE_DATE);
	    	
	    	stmt.setString(1, date);
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
	
	public void updateBattle(String date, int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_BATTLE_DATE);
	    	
	    	stmt.setString(1, date);
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
	
	public void updateExplore(String date, int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_EXPLORE_DATE);
	    	
	    	stmt.setString(1, date);
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
	
	public void updateSetSail(String date, int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_SET_SAIL_DATE);
	    	
	    	stmt.setString(1, date);
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
	
	public void updateCommandStartEndDate(String startDate, String endDate, int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_START_END);
	    	
	    	stmt.setString(1, startDate);
	    	stmt.setString(2, endDate);
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
	
	public void updateTopFiveStartEndDate(String startDate, String endDate, int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_TOP_START_END);
	    	
	    	stmt.setString(1, startDate);
	    	stmt.setString(2, endDate);
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
	
	public void updatePlunder(String date, int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_PLUNDER_DATE);
	    	
	    	stmt.setString(1, date);
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
	
	public void updateGrog(String date, int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_GROG_DATE);
	    	
	    	stmt.setString(1, date);
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
	
	public void deactivateCommands(int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_RESET);
	    	
	    	stmt.setString(1, StringUtils.EMPTY);
	    	stmt.setString(2, StringUtils.EMPTY);
	    	stmt.setString(3, StringUtils.EMPTY);
	    	stmt.setString(4, StringUtils.EMPTY);
	    	stmt.setString(5, StringUtils.EMPTY);
	    	stmt.setString(6, StringUtils.EMPTY);
	    	stmt.setInt(7, pirateId);

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
	
	public void deactivateTopFive(int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_TOP_5_RESET);
	    	
	    	stmt.setString(1, StringUtils.EMPTY);
	    	stmt.setString(2, StringUtils.EMPTY);
	    	stmt.setString(3, StringUtils.EMPTY);
	    	stmt.setString(4, StringUtils.EMPTY);
	    	stmt.setInt(5, pirateId);

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
	
	public void addNewDoubloonAct() {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(ADD_NEW_ACT);
	    	
	    	stmt.setString(1, StringUtils.EMPTY);
	    	stmt.setString(2, StringUtils.EMPTY);
	    	stmt.setString(3, StringUtils.EMPTY);
	    	stmt.setString(4, StringUtils.EMPTY);
	    	stmt.setString(5, StringUtils.EMPTY);
	    	stmt.setString(6, StringUtils.EMPTY);
	    	stmt.setString(7, StringUtils.EMPTY);
	    	stmt.setString(8, StringUtils.EMPTY);
	    	stmt.setString(9, StringUtils.EMPTY);
	    	stmt.setString(10, StringUtils.EMPTY);

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

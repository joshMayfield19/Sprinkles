package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import com.ccc.chestersprinkles.model.Achievement;

public class AchievementDao {
	private static final String GET_BY_PIRATE_ID = "select ach_id, pirate_id, new_unemp, elec_slide, self_plank from achievement where pirate_id = ?";
	private static final String UPDATE_NEWLY_UNEMPLOYED = "update achievement set new_unemp = ? where pirate_id = ?";
	private static final String UPDATE_ELECTRA_SLIDE = "update achievement set elec_slide = ? where pirate_id = ?";
	private static final String UPDATE_SELF_PLANK = "update achievement set self_plank = ? where pirate_id = ?";
	private static final String ADD_NEW_ACHIEVEMENT = "insert into achievement (pirate_id, new_unemp, elec_slide, self_plank) "
			+ "values (0,?,?,0)";
	
	public Achievement getAchievementByPirateId(int pirateId) {
		Achievement achievement = new Achievement();
		
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(GET_BY_PIRATE_ID);
	    	stmt.setInt(1, pirateId);
	    	
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	        	achievement.setAchievementId(rs.getInt(1));
	        	achievement.setPirateId(rs.getInt(2));
	        	achievement.setNewlyUnemployed(rs.getString(3));
	        	achievement.setElectraSlide(rs.getString(4));
	        	achievement.setHasSelfPlank(rs.getInt(5) == 1 ? true : false);
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
	    
	    return achievement;
	}
	
	public void updateNewlyUnemployed(String newlyUnemployedString, int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_NEWLY_UNEMPLOYED);
	    	
	    	stmt.setString(1, newlyUnemployedString);
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
	
	public void updateElectraSlide(String electraSlideString, int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_ELECTRA_SLIDE);
	    	
	    	stmt.setString(1, electraSlideString);
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
	
	public void updateSelfPlank(boolean selfPlank, int pirateId) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_SELF_PLANK);
	    	
	    	stmt.setInt(1, selfPlank ? 1 : 0);
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
	
	public void addNewAchievement() {
		Connection con = null;
		PreparedStatement stmt = null;
		
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(ADD_NEW_ACHIEVEMENT);
	    	
	    	stmt.setString(1, StringUtils.EMPTY);
	    	stmt.setString(2, StringUtils.EMPTY);
	    	
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

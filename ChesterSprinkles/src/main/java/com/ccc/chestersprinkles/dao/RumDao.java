package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ccc.chestersprinkles.model.Challenge;
import com.ccc.chestersprinkles.model.Pirate;
import com.ccc.chestersprinkles.model.Rum;
import com.ccc.chestersprinkles.model.SlackUser;

public class RumDao {
	private static final String GET_MY_RUM_GETS = "select rum_id, rum_giver, rum_giver_id, rum_getter, rum_getter_id, rum_reason, rum_dte from rum where rum_getter_id = ? order by datetime(rum_dte) asc";
	private static final String GET_MY_RUM_GIVES = "select rum_id, rum_giver, rum_giver_id, rum_getter, rum_getter_id, rum_reason, rum_dte from rum where rum_giver_id = ? order by datetime(rum_dte) asc";
	private static final String ADD_NEW_RUM = "insert into rum (rum_giver, rum_giver_id, rum_getter, rum_getter_id, rum_reason, rum_dte) values (?,?,?,?,?,?)";
	
	public void addNewRum(Rum rum) {
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = SqliteDao.openDb();
			stmt = con.prepareStatement(ADD_NEW_RUM);

			stmt.setString(1, rum.getRumGiver());
			stmt.setInt(2, rum.getRumGiverId());
			stmt.setString(3, rum.getRumGetter());
			stmt.setInt(4, rum.getRumGetterId());
			stmt.setString(5, rum.getRumReason());
			stmt.setString(6, rum.getRumDate());

			// execute update SQL stetement
			stmt.executeUpdate();

		} catch (SQLException e) {
			// JDBCTutorialUtilities.printSQLException(e);
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
	
	public List<Rum> getRumsGiven(int pirateId) {
		List<Rum> rums = new ArrayList<>(); 
		
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
	    	con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(GET_MY_RUM_GIVES);
	    	stmt.setInt(1, pirateId);
	    	
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	        	Rum rum = new Rum();
	        	rum.setRumId(rs.getInt(1));
	        	rum.setRumGiver(rs.getString(2));
	        	rum.setRumGiverId(rs.getInt(3));
	        	rum.setRumGetter(rs.getString(4));
	        	rum.setRumGetterId(rs.getInt(5));
	        	rum.setRumReason(rs.getString(6));
	        	rum.setRumDate(rs.getString(7));
	            rums.add(rum);
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
	    
	    return rums;
	}
	
	public List<Rum> getRumsGotten(int pirateId) {
		List<Rum> rums = new ArrayList<>(); 
		
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
	    	con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(GET_MY_RUM_GETS);
	    	stmt.setInt(1, pirateId);
	    	
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	        	Rum rum = new Rum();
	        	rum.setRumId(rs.getInt(1));
	        	rum.setRumGiver(rs.getString(2));
	        	rum.setRumGiverId(rs.getInt(3));
	        	rum.setRumGetter(rs.getString(4));
	        	rum.setRumGetterId(rs.getInt(5));
	        	rum.setRumReason(rs.getString(6));
	        	rum.setRumDate(rs.getString(7));
	            rums.add(rum);
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
	    
	    return rums;
	}
}

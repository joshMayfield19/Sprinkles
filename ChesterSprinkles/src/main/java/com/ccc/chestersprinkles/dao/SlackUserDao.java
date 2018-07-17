package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ccc.chestersprinkles.model.SlackUser;

public class SlackUserDao {
	private static final String GET_SLACK_USERS = "select slack_user_id, first_name, last_name, slack_id from slack_user";
	private static final String GET_SLACK_USER_BY_ID = "select slack_user_id, first_name, last_name, slack_id from slack_user where slack_id = ?";
	private static final String ADD_NEW_USER = "insert into slack_user (first_name, last_name, slack_id) values (?,?,?)";
	
	public List<SlackUser> getSlackUsers() throws SQLException {
		List<SlackUser> slackUsers = new ArrayList<SlackUser>(); 
		
		Connection con = null;
		Statement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(GET_SLACK_USERS);
	        while (rs.next()) {
	            SlackUser slackUser = new SlackUser();
	            slackUser.setSlackUserId(rs.getInt("slack_user_id"));
	            slackUser.setFirstName(rs.getString("first_name"));
	            slackUser.setLastName(rs.getString("last_name"));
	            slackUser.setSlackId(rs.getString("slack_id"));
	            slackUsers.add(slackUser);
	        }
	        
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) { 
	        	stmt.close(); 
	    		SqliteDao.closeDb(con);
	        }
	    }
	    
	    return slackUsers;
	}
	
	public SlackUser getSlackUser(String slackId) throws SQLException {
		SlackUser slackUser = new SlackUser();
		
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(GET_SLACK_USER_BY_ID);
	    	stmt.setString(1, slackId);
	    	
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            slackUser.setSlackUserId(rs.getInt("slack_user_id"));
	        	slackUser.setFirstName(rs.getString("first_name"));
	            slackUser.setLastName(rs.getString("last_name"));
	            slackUser.setSlackId(rs.getString("slack_id"));
	        }
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) { 
	        	stmt.close(); 
	    		SqliteDao.closeDb(con);
	        }
	    }
	    
	    return slackUser;
	}
	
	public void addNewSlackUser(SlackUser slackUser) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(ADD_NEW_USER);
	    	
	    	stmt.setString(1, slackUser.getFirstName());
	    	stmt.setString(2,  slackUser.getLastName());
	    	stmt.setString(3, slackUser.getSlackId());

			// execute insert SQL stetement
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

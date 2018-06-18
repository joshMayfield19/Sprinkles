package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ccc.chestersprinkles.model.CommandIdea;

public class CommandIdeaDao {
	private static final String GET_ALL_COMMANDS = "select co_idea_id, user_id, command, description from command_idea";
	
	public List<CommandIdea> getAllIdeas() throws SQLException {
		List<CommandIdea> commandIdeas = new ArrayList<CommandIdea>(); 
		
		Connection con = null;
		Statement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(GET_ALL_COMMANDS);
	        while (rs.next()) {
	        	CommandIdea commandIdea = new CommandIdea();
	        	commandIdea.setCommandIdeaId(rs.getInt("ch_idea_id"));
	            commandIdea.setUserId(rs.getInt("user_id"));
	            commandIdea.setCommand(rs.getString("command"));
	            commandIdea.setAction(rs.getString("description"));
	            commandIdeas.add(commandIdea);
	        }
	        
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) { 
	        	stmt.close(); 
	    		SqliteDao.closeDb(con);
	        }
	    }
	    
	    return commandIdeas;
	}
}

package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ccc.chestersprinkles.model.PointEvent;

public class PointEventDao {
	private static final String GET_ALL_EVENTS = "select event_id, event, contact, event_date, location, points from upcoming_event order by datetime(event_date) asc;";
	//private static final String INSERT_NEW_EVENT = "";

	public List<PointEvent> getEvents() {
		List<PointEvent> events = new ArrayList<PointEvent>(); 
		
		Connection con = null;
		Statement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(GET_ALL_EVENTS);
	        while (rs.next()) {
	        	PointEvent event = new PointEvent();
	        	event.setEventId(rs.getInt("event_id"));
	        	event.setEvent(rs.getString("event"));
	        	event.setContact(rs.getString("contact"));
	        	event.setDate(rs.getString("event_date"));
	        	event.setLocation(rs.getString("location"));
	        	event.setPoints(rs.getInt("points"));
	            events.add(event);
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
	    
	    return events;
	}
}

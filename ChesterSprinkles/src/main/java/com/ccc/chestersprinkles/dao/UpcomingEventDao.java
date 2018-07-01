package com.ccc.chestersprinkles.dao;

public class UpcomingEventDao {
	private static final String GET_ALL_EVENTS = "select event_id, event, contact, event_date, location, points from upcoming_event order by datetime(event_date) asc;";
	private static final String INSERT_NEW_EVENT = "";

	
}

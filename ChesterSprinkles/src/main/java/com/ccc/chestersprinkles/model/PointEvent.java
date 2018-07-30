package com.ccc.chestersprinkles.model;

public class PointEvent {
	private int eventId;
	private String event;
	private String contact;
	private String date;
	private String location;
	private int points;
	
	public PointEvent() {
		
	}
	
	public String getEvent() {
		return event;
	}
	public String getContact() {
		return contact;
	}
	public String getDate() {
		return date;
	}
	public String getLocation() {
		return location;
	}
	public int getPoints() {
		return points;
	}
	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setPoints(int points) {
		this.points = points;
	}
}
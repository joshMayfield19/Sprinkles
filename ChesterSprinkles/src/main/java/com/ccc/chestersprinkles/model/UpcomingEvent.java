package com.ccc.chestersprinkles.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "upcomingEvent")
@XmlAccessorType (XmlAccessType.FIELD)
public class UpcomingEvent {
	private String event;
	private String contact;
	private String date;
	private String location;
	private String points;
	
	public UpcomingEvent() {
		
	}
	
	public UpcomingEvent(String event, String contact, String date, String location, String points) {
		this.event = event;
		this.contact = contact;
		this.date = date;
		this.location = location;
		this.points = points;
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
	public String getPoints() {
		return points;
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
	public void setPoints(String points) {
		this.points = points;
	}
}
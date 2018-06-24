package com.ccc.chestersprinkles.model;

public class PiratePointsHistory {
	private int piratePointsHistoryId;
	private int pirateId;
	private int points;
	private String event;
	private String dateOfEvent;
	
	public PiratePointsHistory () {
		
	}

	public int getPiratePointsHistoryId() {
		return piratePointsHistoryId;
	}

	public int getPirateId() {
		return pirateId;
	}

	public int getPoints() {
		return points;
	}

	public String getEvent() {
		return event;
	}

	public String getDateOfEvent() {
		return dateOfEvent;
	}

	public void setPiratePointsHistoryId(int piratePointsHistoryId) {
		this.piratePointsHistoryId = piratePointsHistoryId;
	}

	public void setPirateId(int pirateId) {
		this.pirateId = pirateId;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public void setDateOfEvent(String dateOfEvent) {
		this.dateOfEvent = dateOfEvent;
	}
}

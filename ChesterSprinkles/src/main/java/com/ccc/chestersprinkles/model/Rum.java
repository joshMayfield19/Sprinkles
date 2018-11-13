package com.ccc.chestersprinkles.model;

public class Rum {
	private int rumId;
	private String rumGiver;
	private int rumGiverId;
	private String rumGetter;
	private int rumGetterId;
	private String rumReason;
	private String rumDate;
	
	public Rum() {
		//
	}
	
	public int getRumId() {
		return rumId;
	}
	public String getRumGiver() {
		return rumGiver;
	}
	public String getRumGetter() {
		return rumGetter;
	}
	public String getRumReason() {
		return rumReason;
	}
	public String getRumDate() {
		return rumDate;
	}
	public int getRumGiverId() {
		return rumGiverId;
	}

	public int getRumGetterId() {
		return rumGetterId;
	}

	public void setRumGiverId(int rumGiverId) {
		this.rumGiverId = rumGiverId;
	}

	public void setRumGetterId(int rumGetterId) {
		this.rumGetterId = rumGetterId;
	}

	public void setRumId(int rumId) {
		this.rumId = rumId;
	}
	public void setRumGiver(String rumGiver) {
		this.rumGiver = rumGiver;
	}
	public void setRumGetter(String rumGetter) {
		this.rumGetter = rumGetter;
	}
	public void setRumReason(String rumReason) {
		this.rumReason = rumReason;
	}
	public void setRumDate(String rumDate) {
		this.rumDate = rumDate;
	}
}

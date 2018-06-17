package com.ccc.chestersprinkles.model;

public class SlackUser {
	private int slackUserId;
	private String slackId;
	private String firstName;
	private String lastName;

	public SlackUser() {
		
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getSlackId() {
		return slackId;
	}

	public int getSlackUserId() {
		return slackUserId;
	}

	public void setSlackUserId(int slackUserId) {
		this.slackUserId = slackUserId;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setSlackId(String slackId) {
		this.slackId = slackId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}

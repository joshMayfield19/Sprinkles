package com.ccc.chestersprinkles.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="slack_user")
public class SlackUser {
	
	@Id
	private String slackId;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="times_presented")
	private int timesPresented;
	
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

	public int getTimesPresented() {
		return timesPresented;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setSlackId(String slackId) {
		this.slackId = slackId;
	}

	public void setTimesPresented(int timesPresented) {
		this.timesPresented = timesPresented;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}

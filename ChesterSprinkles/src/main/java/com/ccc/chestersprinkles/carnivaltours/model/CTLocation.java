package com.ccc.chestersprinkles.carnivaltours.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ct_location")
public class CTLocation {
	
	@Id
	private String locationId;
	
	@Column(name="location")
	private String location;
	
	@Column(name="intro_text")
	private String introText;
	
	public CTLocation() {
		
	}

	public String getLocation() {
		return location;
	}

	public String getIntroText() {
		return introText;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setIntroText(String introText) {
		this.introText = introText;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}

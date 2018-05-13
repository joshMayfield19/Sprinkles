package com.ccc.chestersprinkles.carnivaltours.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ct_look_around_item")
public class CTLookAroundItem {
	
	@Id
	private String lookAroundItemId;
	
	@Column(name="location_id")
	private String locationId;
	
	@Column(name="item")
	private String item;
	
	@Column(name="description")
	private String description;
	
	public CTLookAroundItem() {
		
	}

	public String getLookAroundItemId() {
		return lookAroundItemId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getLocationId() {
		return locationId;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setLookAroundItemId(String lookAroundItemId) {
		this.lookAroundItemId = lookAroundItemId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
}

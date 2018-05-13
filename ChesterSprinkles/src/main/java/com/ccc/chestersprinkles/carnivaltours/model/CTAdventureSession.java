package com.ccc.chestersprinkles.carnivaltours.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ct_adventure_session")
public class CTAdventureSession {
	
	@Id
	private String adventureId;
	
	@Column(name="current_loc")
	private String currentLoc;
	
	@Column(name="items")
	private String items;
	
	@Column(name="player")
	private String player;
	
	public CTAdventureSession() {
		
	}

	public String getAdventureId() {
		return adventureId;
	}
	
	public String getPlayer() {
		return player;
	}
	
	public String getCurrentLoc() {
		return currentLoc;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public void setAdventureId(String adventureId) {
		this.adventureId = adventureId;
	}

	public void setCurrentLoc(String currentLoc) {
		this.currentLoc = currentLoc;
	}
	
	public void setPlayer(String player) {
		this.player = player;
	}
}

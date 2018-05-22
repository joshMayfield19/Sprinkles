package com.ccc.chestersprinkles.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pirateShip")
@XmlAccessorType (XmlAccessType.FIELD)
public class PirateShip implements Comparable<PirateShip> {
	private int shipId;
	private String shipName;
	private String shipCaptain;
	private int shipPoints;
	private int shipCrew;
	
	public String getShipName() {
		return shipName;
	}
	public String getShipCaptain() {
		return shipCaptain;
	}
	public int getShipPoints() {
		return shipPoints;
	}
	public int getShipId() {
		return shipId;
	}
	public void setShipId(int shipId) {
		this.shipId = shipId;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	public void setShipCaptain(String shipCaptain) {
		this.shipCaptain = shipCaptain;
	}
	public void setShipPoints(int shipPoints) {
		this.shipPoints = shipPoints;
	}
	public int getShipCrew() {
		return shipCrew;
	}
	public void setShipCrew(int shipCrew) {
		this.shipCrew = shipCrew;
	}

	@Override
	public int compareTo(PirateShip pirateShip) {
		if (this.shipPoints == pirateShip.shipPoints) {
			return 0;
		}
		else if (this.shipPoints < pirateShip.shipPoints) {
			return 1;
		}
		else {
			return -1;
		}
	}
}

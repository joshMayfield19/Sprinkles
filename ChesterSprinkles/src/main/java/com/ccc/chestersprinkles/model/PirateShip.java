package com.ccc.chestersprinkles.model;

public class PirateShip implements Comparable<PirateShip> {
	private int shipId;
	private String shipName;
	private int shipCaptain;
	private int shipPoints;
	private int overallShipPoints;
	private int shipCrew;
	private String shipFlag;
	
	public String getShipName() {
		return shipName;
	}
	public int getShipCaptain() {
		return shipCaptain;
	}
	public int getShipPoints() {
		return shipPoints;
	}
	public int getShipId() {
		return shipId;
	}
	public String getShipFlag() {
		return shipFlag;
	}
	public void setShipFlag(String shipFlag) {
		this.shipFlag = shipFlag;
	}
	public void setShipId(int shipId) {
		this.shipId = shipId;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	public void setShipCaptain(int shipCaptain) {
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

	public int getOverallShipPoints() {
		return overallShipPoints;
	}
	public void setOverallShipPoints(int overallShipPoints) {
		this.overallShipPoints = overallShipPoints;
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

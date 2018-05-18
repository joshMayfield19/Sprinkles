package com.ccc.chestersprinkles.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pirate")
@XmlAccessorType (XmlAccessType.FIELD)
public class Pirate {
	private String pirateName;
	private String realName;
	private int pirateShipId;
	private int piratePoints;
	
	public String getPirateName() {
		return pirateName;
	}
	public int getPiratePoints() {
		return piratePoints;
	}
	public int getPirateShipId() {
		return pirateShipId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public void setPirateShipId(int pirateShipId) {
		this.pirateShipId = pirateShipId;
	}
	public void setPirateName(String pirateName) {
		this.pirateName = pirateName;
	}
	public void setPiratePoints(int piratePoints) {
		this.piratePoints = piratePoints;
	}
}

package com.ccc.chestersprinkles.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pirate")
@XmlAccessorType (XmlAccessType.FIELD)
public class Pirate implements Comparable<Pirate> {
	private String pirateName;
	private String realName;
	private int pirateShipId;
	private int piratePoints;
	private int doubloons;
	
	public Pirate () {
		
	}
	
	public Pirate (String realName) {
		this.pirateName = "Pending";
		this.realName = realName;
		this.pirateShipId = 0;
		this.piratePoints = 0;
		this.doubloons = 0;
	}
	
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

	public int getDoubloons() {
		return doubloons;
	}

	public void setDoubloons(int doubloons) {
		this.doubloons = doubloons;
	}

	@Override
	public int compareTo(Pirate pirate) {
		if (this.piratePoints == pirate.piratePoints) {
			return 0;
		}
		else if (this.piratePoints < pirate.piratePoints) {
			return 1;
		}
		else {
			return -1;
		}
	}
}

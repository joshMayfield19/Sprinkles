package com.ccc.chestersprinkles.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "challengeAward")
@XmlAccessorType (XmlAccessType.FIELD)
public class ChallengeAward {
	private String challengeName;
	private String mostCreative;
	private String mostInformative;
	private String prizeWinners;
	private String id;
	
	public String getChallengeName() {
		return challengeName;
	}
	public String getMostCreative() {
		return mostCreative;
	}
	public String getMostInformative() {
		return mostInformative;
	}
	public String getPrizeWinners() {
		return prizeWinners;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setChallengeName(String challengeName) {
		this.challengeName = challengeName;
	}
	public void setMostCreative(String mostCreative) {
		this.mostCreative = mostCreative;
	}
	public void setMostInformative(String mostInformative) {
		this.mostInformative = mostInformative;
	}
	public void setPrizeWinners(String prizeWinners) {
		this.prizeWinners = prizeWinners;
	}
}

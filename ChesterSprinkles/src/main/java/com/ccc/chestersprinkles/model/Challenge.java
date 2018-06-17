package com.ccc.chestersprinkles.model;

public class Challenge {
	private int challengeId;
	private String challengeName;
	private String challengeLink;
	private String challengeDate;
	private boolean isCurrent;
	
	public Challenge() {
		
	}

	public String getChallengeName() {
		return challengeName;
	}

	public String getChallengeLink() {
		return challengeLink;
	}

	public String getChallengeDate() {
		return challengeDate;
	}

	public boolean isCurrent() {
		return isCurrent;
	}

	public int getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(int challengeId) {
		this.challengeId = challengeId;
	}

	public void setCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}

	public void setChallengeName(String challengeName) {
		this.challengeName = challengeName;
	}

	public void setChallengeLink(String challengeLink) {
		this.challengeLink = challengeLink;
	}

	public void setChallengeDate(String challengeDate) {
		this.challengeDate = challengeDate;
	}
}

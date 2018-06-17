package com.ccc.chestersprinkles.model;

public class Presentation {
	private int presentationId;
	private int presenterId;
	private int challengeId;
	
	public Presentation() {
		
	}

	public int getPresentationId() {
		return presentationId;
	}

	public int getPresenterId() {
		return presenterId;
	}

	public int getChallengeId() {
		return challengeId;
	}

	public void setPresentationId(int presentationId) {
		this.presentationId = presentationId;
	}

	public void setPresenterId(int presenterId) {
		this.presenterId = presenterId;
	}

	public void setChallengeId(int challengeId) {
		this.challengeId = challengeId;
	}
}

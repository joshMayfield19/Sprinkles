package com.ccc.chestersprinkles.model;

public class ChallengeIdea {
	private int challengeIdeaId;
	private int userId;
	private String idea;
	
	public ChallengeIdea() {
		
	}

	public int getChallengeIdeaId() {
		return challengeIdeaId;
	}

	public int getUserId() {
		return userId;
	}

	public String getIdea() {
		return idea;
	}

	public void setChallengeIdeaId(int challengeIdeaId) {
		this.challengeIdeaId = challengeIdeaId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}
}

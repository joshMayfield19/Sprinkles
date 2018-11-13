package com.ccc.chestersprinkles.model;

public class Achievement {
	private int achievementId;
	private int pirateId;
	private String newlyUnemployed;
	private String electraSlide;
	private boolean hasSelfPlank;
	
	public Achievement() {
		
	}

	public int getAchievementId() {
		return achievementId;
	}

	public int getPirateId() {
		return pirateId;
	}

	public String getNewlyUnemployed() {
		return newlyUnemployed;
	}

	public String getElectraSlide() {
		return electraSlide;
	}

	public boolean isHasSelfPlank() {
		return hasSelfPlank;
	}

	public void setAchievementId(int achievementId) {
		this.achievementId = achievementId;
	}

	public void setPirateId(int pirateId) {
		this.pirateId = pirateId;
	}

	public void setNewlyUnemployed(String newlyUnemployed) {
		this.newlyUnemployed = newlyUnemployed;
	}

	public void setElectraSlide(String electraSlide) {
		this.electraSlide = electraSlide;
	}

	public void setHasSelfPlank(boolean hasSelfPlank) {
		this.hasSelfPlank = hasSelfPlank;
	}
	
	
}

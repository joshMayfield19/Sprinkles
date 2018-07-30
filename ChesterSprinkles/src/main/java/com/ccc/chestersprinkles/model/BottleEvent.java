package com.ccc.chestersprinkles.model;

public class BottleEvent {
	private int bottleEventId;
	private String bottleStartDate;
	private String bottleEndDate;
	private int doubReward;
	private int pointReward;
	private int pirateId;
	private boolean rewardCollected;
	
	public BottleEvent() {
		
	}

	public int getBottleEventId() {
		return bottleEventId;
	}

	public String getBottleStartDate() {
		return bottleStartDate;
	}

	public String getBottleEndDate() {
		return bottleEndDate;
	}
	
	public int getPirateId() {
		return pirateId;
	}

	public int getDoubReward() {
		return doubReward;
	}

	public boolean isRewardCollected() {
		return rewardCollected;
	}

	public void setRewardCollected(boolean rewardCollected) {
		this.rewardCollected = rewardCollected;
	}

	public int getPointReward() {
		return pointReward;
	}

	public void setDoubReward(int doubReward) {
		this.doubReward = doubReward;
	}

	public void setPointReward(int pointReward) {
		this.pointReward = pointReward;
	}

	public void setBottleEventId(int bottleEventId) {
		this.bottleEventId = bottleEventId;
	}

	public void setBottleStartDate(String bottleStartDate) {
		this.bottleStartDate = bottleStartDate;
	}

	public void setBottleEndDate(String bottleEndDate) {
		this.bottleEndDate = bottleEndDate;
	}

	public void setPirateId(int pirateId) {
		this.pirateId = pirateId;
	}
}

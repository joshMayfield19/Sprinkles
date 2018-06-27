package com.ccc.chestersprinkles.model;

public class DoubloonActivity {
	private int activityId;
	private int pirateId;
	private String commandStartDate;
	private String commandEndDate;
	private String lastShoreleaveDate;
	private String lastExploreDate;
	private String lastBattleDate;
	private String lastSetSailDate;
	
	public DoubloonActivity() {
		
	}

	public int getActivityId() {
		return activityId;
	}

	public int getPirateId() {
		return pirateId;
	}

	public String getCommandStartDate() {
		return commandStartDate;
	}

	public String getCommandEndDate() {
		return commandEndDate;
	}

	public String getLastShoreleaveDate() {
		return lastShoreleaveDate;
	}

	public String getLastExploreDate() {
		return lastExploreDate;
	}

	public String getLastBattleDate() {
		return lastBattleDate;
	}

	public String getLastSetSailDate() {
		return lastSetSailDate;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public void setPirateId(int pirateId) {
		this.pirateId = pirateId;
	}

	public void setCommandStartDate(String commandStartDate) {
		this.commandStartDate = commandStartDate;
	}

	public void setCommandEndDate(String commandEndDate) {
		this.commandEndDate = commandEndDate;
	}

	public void setLastShoreleaveDate(String lastShoreleaveDate) {
		this.lastShoreleaveDate = lastShoreleaveDate;
	}

	public void setLastExploreDate(String lastExploreDate) {
		this.lastExploreDate = lastExploreDate;
	}

	public void setLastBattleDate(String lastBattleDate) {
		this.lastBattleDate = lastBattleDate;
	}

	public void setLastSetSailDate(String lastSetSailDate) {
		this.lastSetSailDate = lastSetSailDate;
	}
}

package com.ccc.chestersprinkles.model;

public class Pirate implements Comparable<Pirate> {
	private int pirateId;
	private int userId;
	private String pirateName;
	private String realName;
	private int pirateShipId;
	private int piratePoints;
	private int doubloons;
	private int overallPiratePoints;
	private boolean isTopFivePirate;
	private boolean isOnWinningShip;
	private boolean isCaptain;
	private int plankNum;
	private boolean canPolly;
	private boolean canBottle;
	private SlackUser slackUser;
	private DoubloonActivity doubloonActivity;
	private String channelId;
	
	public Pirate () {
		this.pirateName = "Pending";
		//this.realName = realName;
		this.pirateShipId = 0;
		this.piratePoints = 0;
		this.doubloons = 0;
		this.overallPiratePoints = 0;
		this.isTopFivePirate = false;
		this.isOnWinningShip = false;
		this.isCaptain = false;
		this.slackUser = new SlackUser();
		this.doubloonActivity = new DoubloonActivity();
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

	public int getOverallPiratePoints() {
		return overallPiratePoints;
	}

	public boolean isTopFivePirate() {
		return isTopFivePirate;
	}

	public boolean isOnWinningShip() {
		return isOnWinningShip;
	}

	public boolean isCaptain() {
		return isCaptain;
	}

	public int getPirateId() {
		return pirateId;
	}

	public int getUserId() {
		return userId;
	}

	public int getPlankNum() {
		return plankNum;
	}

	public SlackUser getSlackUser() {
		return slackUser;
	}

	public DoubloonActivity getDoubloonActivity() {
		return doubloonActivity;
	}

	public boolean isCanPolly() {
		return canPolly;
	}

	public String getChannelId() {
		return channelId;
	}

	public boolean isCanBottle() {
		return canBottle;
	}

	public void setCanBottle(boolean canBottle) {
		this.canBottle = canBottle;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public void setCanPolly(boolean canPolly) {
		this.canPolly = canPolly;
	}

	public void setDoubloonActivity(DoubloonActivity doubloonActivity) {
		this.doubloonActivity = doubloonActivity;
	}

	public void setSlackUser(SlackUser slackUser) {
		this.slackUser = slackUser;
	}

	public void setPlankNum(int plankNum) {
		this.plankNum = plankNum;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setPirateId(int pirateId) {
		this.pirateId = pirateId;
	}

	public void setOverallPiratePoints(int overallPiratePoints) {
		this.overallPiratePoints = overallPiratePoints;
	}

	public void setTopFivePirate(boolean isTopFivePirate) {
		this.isTopFivePirate = isTopFivePirate;
	}

	public void setOnWinningShip(boolean isOnWinningShip) {
		this.isOnWinningShip = isOnWinningShip;
	}

	public void setCaptain(boolean isCaptain) {
		this.isCaptain = isCaptain;
	}

	public int getDoubloons() {
		return doubloons;
	}

	public void setDoubloons(int doubloons) {
		this.doubloons = doubloons;
	}

	@Override
	public int compareTo(Pirate pirate) {
		if (this.overallPiratePoints == pirate.overallPiratePoints) {
			return 0;
		}
		else if (this.overallPiratePoints < pirate.overallPiratePoints) {
			return 1;
		}
		else {
			return -1;
		}
	}
}

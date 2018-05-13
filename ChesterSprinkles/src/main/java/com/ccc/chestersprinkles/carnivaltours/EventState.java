package com.ccc.chestersprinkles.carnivaltours;

public class EventState {
	private boolean stillHidingItems;
	private boolean hatFound;
	private boolean caneFound;
	private boolean waxFound;
	private boolean foundAllHiddenItems;
	private boolean reachedMonorail;
	private boolean adventureContinue;
	private boolean validLocation;
	
	public EventState() {
		this.stillHidingItems = true;
		this.validLocation = true;
	}
	
	public boolean isStillHidingItems() {
		return stillHidingItems;
	}

	public boolean isHatFound() {
		return hatFound;
	}

	public boolean isCaneFound() {
		return caneFound;
	}

	public boolean isWaxFound() {
		return waxFound;
	}

	public boolean isFoundAllHiddenItems() {
		return foundAllHiddenItems;
	}

	public boolean isReachedMonorail() {
		return reachedMonorail;
	}

	public boolean isAdventureContinue() {
		return adventureContinue;
	}

	public void setStillHidingItems(boolean stillHidingItems) {
		this.stillHidingItems = stillHidingItems;
	}

	public void setHatFound(boolean hatFound) {
		this.hatFound = hatFound;
	}

	public void setCaneFound(boolean caneFound) {
		this.caneFound = caneFound;
	}

	public void setWaxFound(boolean waxFound) {
		this.waxFound = waxFound;
	}

	public void setFoundAllHiddenItems(boolean foundAllHiddenItems) {
		this.foundAllHiddenItems = foundAllHiddenItems;
	}

	public void setReachedMonorail(boolean reachedMonorail) {
		this.reachedMonorail = reachedMonorail;
	}

	public void setAdventureContinue(boolean adventureContinue) {
		this.adventureContinue = adventureContinue;
	}

	public boolean isValidLocation() {
		return validLocation;
	}

	public void setValidLocation(boolean validLocation) {
		this.validLocation = validLocation;
	}
	
	
}

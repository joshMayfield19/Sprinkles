package com.ccc.chestersprinkles.carnivaltours;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ccc.chestersprinkles.carnivaltours.factory.CarnivalLocationFactory;
import com.ccc.chestersprinkles.carnivaltours.model.CTLocation;
import com.ccc.chestersprinkles.carnivaltours.model.CTLookAroundItem;

@Component
public class TextAdventureCriteria {
	private String currentCommand;
	private List<String> itemsAlreadyInteractedWith;
	private EventState eventState;
	private CTLocation location;
	
	@Autowired
	private CarnivalLocationFactory locationFactory;

	@Autowired
	private ItemHider itemHider;
	
	public TextAdventureCriteria() {
		this.currentCommand = "";
		this.itemsAlreadyInteractedWith = new ArrayList<String>();
		this.eventState = new EventState();
	}
	
	public void resetCriteria() {
		this.itemsAlreadyInteractedWith = new ArrayList<String>();
		this.eventState = new EventState();
		this.itemHider.reset();
	}
	
	public void distributeHiddenItems() {
		getItemHider().distributeHiddenItems(getEventState());
	}
	
	public boolean hasReachedMonrail() {
		return getEventState().isReachedMonorail();
	}
	
	public boolean haveFoundAllItems() {
		return getEventState().isFoundAllHiddenItems();
	}
	
	public void checkToSeeIfAllItemsAreFound() {
		if (getEventState().isHatFound() && getEventState().isCaneFound() && getEventState().isWaxFound()) {
			getEventState().setFoundAllHiddenItems(true);
		}
	}
	
	public void moveToNewLocation() {
		CTLocation newLocation = getLocationFactory().getCarnivalLocation(getCurrentCommand(), getEventState());
		
		if (newLocation != null) {
			setLocation(newLocation);
			getEventState().setValidLocation(true);
		}
		else {
			getEventState().setValidLocation(false);
		}
	}
	
	public boolean haveSeenThisItem(CTLookAroundItem item) {
		return getItemsAlreadyInteractedWith().contains(item.getItem());
	}
	
	public boolean isThisTheHatLocation() {
		return getCurrentCommand().contains(getItemHider().getHatLocation());
	}
	
	public boolean isThisTheCaneLocation() {
		return getCurrentCommand().contains(getItemHider().getCaneLocation());
	}
	
	public boolean isThisTheWaxLocation() {
		return getCurrentCommand().contains(getItemHider().getWaxLocation());
	}
	
	public boolean isEventStateContinue() {
		return getEventState().isAdventureContinue();
	}
	
	public void setEventStateContinue (boolean isContinue) {
		getEventState().setAdventureContinue(isContinue);
	}
	
	public String getCurrentCommand() {
		return currentCommand;
	}
	public List<String> getItemsAlreadyInteractedWith() {
		return itemsAlreadyInteractedWith;
	}
	public EventState getEventState() {
		return eventState;
	}
	public ItemHider getItemHider() {
		return itemHider;
	}
	public CTLocation getLocation() {
		return location;
	}

	public CarnivalLocationFactory getLocationFactory() {
		return locationFactory;
	}

	public void setLocationFactory(CarnivalLocationFactory locationFactory) {
		this.locationFactory = locationFactory;
	}

	public void setLocation(CTLocation location) {
		this.location = location;
	}

	public void setCurrentCommand(String currentCommand) {
		this.currentCommand = currentCommand;
	}
	public void setItemsAlreadyInteractedWith(List<String> itemsAlreadyInteractedWith) {
		this.itemsAlreadyInteractedWith = itemsAlreadyInteractedWith;
	}
	public void setEventState(EventState eventState) {
		this.eventState = eventState;
	}
	public void setItemHider(ItemHider itemHider) {
		this.itemHider = itemHider;
	}
}

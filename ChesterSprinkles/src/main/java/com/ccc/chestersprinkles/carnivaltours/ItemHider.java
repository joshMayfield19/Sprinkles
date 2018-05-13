package com.ccc.chestersprinkles.carnivaltours;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ccc.chestersprinkles.carnivaltours.model.CTLookAroundItem;
import com.ccc.chestersprinkles.carnivaltours.service.CTLookAroundItemService;

@Component
public class ItemHider {
	private String hatLocation;
	private String caneLocation;
	private String waxLocation;

	@Autowired
	private CTLookAroundItemService ctLookAroundItemService;
	
	public ItemHider() {
		//
	}
	
	public void reset() {
		setHatLocation(null);
		setCaneLocation(null);
		setWaxLocation(null);
	}
	
	public void distributeHiddenItems(EventState eventState) {
		while (eventState.isStillHidingItems()) {
			String locationToHideItem = getRandomItem();

			if (isThereAlreadySomethingHiddenHere(locationToHideItem)) {
				continue;
			} else if (hatLocation == null) {
				hatLocation = locationToHideItem;
			} else if (waxLocation == null) {
				waxLocation = locationToHideItem;
			} else if (caneLocation == null) {
				caneLocation = locationToHideItem;
			}

			if (isEverythingHidden()) {
				eventState.setStillHidingItems(false);
			}
		}
	}
	
	private boolean isEverythingHidden() {
		return hatLocation != null && waxLocation != null && caneLocation != null;
	}

	private boolean isThereAlreadySomethingHiddenHere(String locationToHideItem) {
		return locationToHideItem.equals(hatLocation) || locationToHideItem.equals(waxLocation)
				|| locationToHideItem.equals(caneLocation);
	}
	
	private String getRandomItem() {
		List<CTLookAroundItem> listOfInteractionsToPlaceHiddenItems = retrieveAllItems();
		int totalItemsMinusOne = listOfInteractionsToPlaceHiddenItems.size() - 1;
		CTLookAroundItem randomItem = listOfInteractionsToPlaceHiddenItems.get(getRandomNumber(totalItemsMinusOne));
		return randomItem.getItem().toLowerCase();
	}
	
	private int getRandomNumber(int maxValue) {
		Random rand = new Random();
		return rand.nextInt(maxValue) + 1;
	}
	
	private List<CTLookAroundItem> retrieveAllItems() {
		return ctLookAroundItemService.getAllCtItems();
	}
	
	public String getHatLocation() {
		return hatLocation;
	}
	public String getCaneLocation() {
		return caneLocation;
	}
	public String getWaxLocation() {
		return waxLocation;
	}
	public void setHatLocation(String hatLocation) {
		this.hatLocation = hatLocation;
	}
	public void setCaneLocation(String caneLocation) {
		this.caneLocation = caneLocation;
	}
	public void setWaxLocation(String waxLocation) {
		this.waxLocation = waxLocation;
	}
}	

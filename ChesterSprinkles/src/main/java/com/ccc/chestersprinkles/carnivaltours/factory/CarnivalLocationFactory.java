package com.ccc.chestersprinkles.carnivaltours.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ccc.chestersprinkles.carnivaltours.EventState;
import com.ccc.chestersprinkles.carnivaltours.TextAdventureConstants;
import com.ccc.chestersprinkles.carnivaltours.TextAdventureHelper;
import com.ccc.chestersprinkles.carnivaltours.model.CTLocation;
import com.ccc.chestersprinkles.carnivaltours.service.CTLocationService;

@Component
public class CarnivalLocationFactory {
	@Autowired
	private CTLocationService ctLocationService;
	
	public CTLocation getCarnivalLocation(String command, EventState eventState) {
		if (TextAdventureHelper.commandContainsItemsIn(command, TextAdventureConstants.CAFE_LOC_CHG_TXT)) {
			return changeToLocation(TextAdventureConstants.LOCATION_CAFE);
		} else if (TextAdventureHelper.commandContainsItemsIn(command, TextAdventureConstants.ARCADE_LOC_CHG_TXT)) {
			return changeToLocation(TextAdventureConstants.LOCATION_ARCADE);
		} else if (TextAdventureHelper.commandContainsItemsIn(command, TextAdventureConstants.HOM_LOC_CHG_TXT)) {
			return changeToLocation(TextAdventureConstants.LOCATION_MIRRORS);
		} else if (TextAdventureHelper.commandContainsItemsIn(command, TextAdventureConstants.LION_DEN_LOC_CHG_TXT)) {
			return changeToLocation(TextAdventureConstants.LOCATION_DEN);
		} else if (TextAdventureHelper.commandContainsItemsIn(command, TextAdventureConstants.SPOOKY_LOC_CHG_TXT)) {
			return changeToLocation(TextAdventureConstants.LOCATION_MANOR);
		} else if (TextAdventureHelper.commandContainsItemsIn(command, TextAdventureConstants.COASTER_LOC_CHG_TXT)) {
			return changeToLocation(TextAdventureConstants.LOCATION_COASTER);
		} else if (eventState.isFoundAllHiddenItems() && TextAdventureHelper.commandContainsItemsIn(command, TextAdventureConstants.MONORAIL_CHG_TXT)) {
			eventState.setReachedMonorail(true);
			return changeToLocation(TextAdventureConstants.LOCATION_MONORAIL);
		} else {
			return null;
		}
	}
	
	private CTLocation changeToLocation(String locationInteger) {
		return ctLocationService.getCtLocation(locationInteger);
	}
}

package com.ccc.chestersprinkles.carnivaltours.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ccc.chestersprinkles.carnivaltours.TextAdventureCriteria;
import com.ccc.chestersprinkles.carnivaltours.model.CTAdventureSession;
import com.ccc.chestersprinkles.carnivaltours.model.CTLookAroundItem;
import com.ccc.chestersprinkles.carnivaltours.service.CTLookAroundItemService;

@Component
public class CarnivalLookAroundFactory {
	@Autowired
	private CTLookAroundItemService ctLookAroundItemService;
	
	public String getLookAroundReply(CTAdventureSession session, TextAdventureCriteria criteria) {
		String lookAroundReply = "";
		if (session.getCurrentLoc().equals("Carnival Entrance")) {
			lookAroundReply = "You see the whole park. You also see the entrance behind you. Let's go see if we can find Monty at one of the attractions.";
		} else {
			List<CTLookAroundItem> whatYouSee = ctLookAroundItemService.getCtLookAroundItemByLocationId(criteria.getLocation().getLocationId());
			List<String> itemsLeftToSee = new ArrayList<String>();
			
			for (CTLookAroundItem item : whatYouSee) {
				if (!criteria.haveSeenThisItem(item)) {
					itemsLeftToSee.add(item.getDescription());
				}
			}
			
			lookAroundReply = retrieveWhatYouSee(itemsLeftToSee);
		}
		
		return lookAroundReply;
	}
	
	private String retrieveWhatYouSee(List<String> itemsLeftToSee) {
		String lookAroundReply;
		if (itemsLeftToSee.size() == 3) {
			lookAroundReply = "You see " + itemsLeftToSee.get(0) + ", " + itemsLeftToSee.get(1)
					+ ", and " + itemsLeftToSee.get(2) + ".";
		}
		else if (itemsLeftToSee.size() == 2) {
			lookAroundReply = "You see " + itemsLeftToSee.get(0) + " and " + itemsLeftToSee.get(1);
		}
		else if (itemsLeftToSee.size() == 1) {
			lookAroundReply = "You see " + itemsLeftToSee.get(0) + ".";
		}
		else {
			lookAroundReply = "You don't see anything of importance here at this location.";
		}
		return lookAroundReply;
	}
}

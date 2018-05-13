package com.ccc.chestersprinkles.carnivaltours.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ccc.chestersprinkles.carnivaltours.TextAdventureCriteria;
import com.ccc.chestersprinkles.carnivaltours.TextAdventureHelper;
import com.ccc.chestersprinkles.carnivaltours.model.CTAdventureSession;
import com.ccc.chestersprinkles.carnivaltours.model.CTInteraction;
import com.ccc.chestersprinkles.carnivaltours.model.CTLookAroundItem;
import com.ccc.chestersprinkles.carnivaltours.service.CTInteractionService;
import com.ccc.chestersprinkles.carnivaltours.service.CTLookAroundItemService;

@Component
public class CarnivalInteractionFactory {
	private TextAdventureCriteria criteria;
	private CTAdventureSession session;
	
	@Autowired
	private  CTInteractionService ctInteractionService;

	@Autowired
	private  CTLookAroundItemService ctLookAroundItemService;
	
	public  String getCarnivalInteraction(CTAdventureSession txSession, TextAdventureCriteria txCriteria) {
		criteria = txCriteria;
		session = txSession;
		
		String interactReply = "";
		if (session.getCurrentLoc().equals("Carnival Entrance")) {
			interactReply = "There is nothing to interact with at this location.";
		} else {
			List<String> whatYouSee = retrieveSearchableItems();
			List<CTInteraction> listOfInteractions = retrieveInteractionsBasedOnLocation();

			if (TextAdventureHelper.commandContainsItemsIn(criteria.getCurrentCommand(), whatYouSee)) {
				interactReply = retrieveInteraction(whatYouSee, listOfInteractions);
			} else {
				interactReply = "You don't see that item.";
			}
		}
		
		return interactReply;
	}
	
	private  String retrieveInteraction(List<String> whatYouSee, List<CTInteraction> listOfInteractions) {
		String interactReply;
		if (!TextAdventureHelper.commandContainsMoreThanOneItemIn(criteria.getCurrentCommand(), whatYouSee)) {
			if (criteria.isThisTheHatLocation()) {
				interactReply = retrieveInteractionForItemFoundHat(listOfInteractions);
			} else if (criteria.isThisTheWaxLocation()) {
				interactReply = retrieveInteractionForItemFoundWax(listOfInteractions);
			} else if (criteria.isThisTheCaneLocation()) {
				interactReply = retrieveInteractionForItemFoundCane(listOfInteractions);
			} else {
				interactReply = retrieveInteractionForItem(listOfInteractions);
			}
		}
		else {
			interactReply = "You can't interact with more than one item at a time.";
		}
		return interactReply;
	}
	
	private  String retrieveInteractionForItemFoundHat(List<CTInteraction> listOfInteractions) {
		addItemToInventory("Top Hat");
		return retrieveInteractionForFoundItem(listOfInteractions, "hat");
	}

	private  String retrieveInteractionForItemFoundWax(List<CTInteraction> listOfInteractions) {
		addItemToInventory("Jar of Mustache Wax");
		return retrieveInteractionForFoundItem(listOfInteractions, "wax");
	}

	private  String retrieveInteractionForItemFoundCane(List<CTInteraction> listOfInteractions) {
		addItemToInventory("Walking Cane");
		return retrieveInteractionForFoundItem(listOfInteractions, "cane");
	}

	private  String retrieveInteractionForItem(List<CTInteraction> listOfInteractions) {
		return retrieveInteractionForFoundItem(listOfInteractions, "notFound");

	}
	
	private  String retrieveInteractionForFoundItem(List<CTInteraction> listOfInteractions, String foundItem) {
		for (CTInteraction interaction : listOfInteractions) {
			CTLookAroundItem itemInteractedWith = retrieveLookAroundItemFromId(interaction.getItemId());
			
			if (criteria.getCurrentCommand().contains(interaction.getInteraction()) && criteria.getCurrentCommand().contains(itemInteractedWith.getItem())) {
				if (criteria.getItemsAlreadyInteractedWith().contains(itemInteractedWith.getItem())) {
					return "You have already interacted with that item.";
				}
				else {
					criteria.getItemsAlreadyInteractedWith().add(itemInteractedWith.getItem());
					String interactionReply = returnInteractionReply(foundItem, interaction);
					
					criteria.checkToSeeIfAllItemsAreFound();
					
					return interactionReply;
				}
			}
		}

		return "You can't do that interaction.";
	}
	
	private  String returnInteractionReply(String foundItem, CTInteraction interaction) {
		String interactionReply;
		if (foundItem.equals("hat")) {
			criteria.getEventState().setHatFound(true);
			interactionReply = interaction.getFoundHatItemAction();
		}
		else if (foundItem.equals("cane")) {
			criteria.getEventState().setCaneFound(true);
			interactionReply = interaction.getFoundCaneItemAction();
		}
		else if (foundItem.equals("wax")) {
			criteria.getEventState().setWaxFound(true);
			interactionReply = interaction.getFoundWaxItemAction();
		}
		else {
			interactionReply = interaction.getStandardAction();
		}
		return interactionReply;
	}

	private  List<String> retrieveSearchableItems() {
		List<CTLookAroundItem> whatYouSee = retrieveItemsYouSeeBasedOnLocation();
		List<String> whatYouSeeSearchableItems = new ArrayList<String>();

		for (CTLookAroundItem item : whatYouSee) {
			whatYouSeeSearchableItems.add(item.getItem());
		}

		return whatYouSeeSearchableItems;
	}
	
	private void addItemToInventory(String item) {
		String currentItems = session.getItems();
		
		if (currentItems.equals("No Items Currently")) {
			session.setItems(item);
		}
		else {
			session.setItems(currentItems + ", " + item);
		}
	}
	
	private  List<CTInteraction> retrieveInteractionsBasedOnLocation() {
		return ctInteractionService.getCtInteractionsByLocationId(criteria.getLocation().getLocationId());
	}
	
	private  List<CTLookAroundItem> retrieveItemsYouSeeBasedOnLocation() {
		return ctLookAroundItemService.getCtLookAroundItemByLocationId(criteria.getLocation().getLocationId());
	}

	private  CTLookAroundItem retrieveLookAroundItemFromId(String itemId) {
		return ctLookAroundItemService.getCtLookAroundItem(itemId);
	}
}

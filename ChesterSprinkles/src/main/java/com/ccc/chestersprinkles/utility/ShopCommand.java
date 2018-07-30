package com.ccc.chestersprinkles.utility;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ccc.chestersprinkles.model.DoubloonShopItem;
import com.ccc.chestersprinkles.model.Pirate;
import com.ccc.chestersprinkles.service.DoubloonItemHistoryService;
import com.ccc.chestersprinkles.service.DoubloonShopItemService;
import com.ccc.chestersprinkles.service.PirateService;

import me.ramswaroop.jbot.core.slack.models.Event;

@Component
public class ShopCommand extends Command {
	
	@Autowired
	private DoubloonShopItemService doubloonShopItemService;
	
	@Autowired
	private PirateService pirateService;
	
	@Autowired
	private DoubloonItemHistoryService doubloonItemHistoryService;
	
	public String getAllShopItems(Event event) {
		if (validateInput(event)) {
			List<DoubloonShopItem> shopItems = doubloonShopItemService.getAllItems();
			
			StringBuilder output = new StringBuilder();
			
			output.append("*These are the items that available for purchase:*\n");
			
			for (DoubloonShopItem item : shopItems) {
				output.append("*PurchaseCommand:* ")
					   .append(item.getItemCommand())
					   .append("\n*Price:* ")
					   .append(item.getItemPrice())
					   .append(" doubloons\n*Description:* ")
					   .append(item.getItemDescription())
					   .append("\n*Quantity left:* ")
					   .append(item.getItemQuantity())
					   .append("\n\n");
			}
			
			output.append("*NOTE: These commands are case sensitive.*\n");
			
			return output.toString();
		}
		
		return null;
	}
	
	public String buyNameChange(Event event) {
		if (validateInput(event)) {
			String[] inputSplit = event.getText().split("\\|");
			
			if (inputSplit.length == 1) {
				return "The format for this purchase should be *!buyName|My New Name*.";
			}
			
			String newName = inputSplit[1];
			
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());
			DoubloonShopItem item = doubloonShopItemService.getItemByCommand(inputSplit[0].trim());
			
			if (item == null) {
				return "I don't recognize the shop command " + inputSplit[0].trim();
			}
			
			if (pirate.getDoubloons() < item.getItemPrice()) {
				return "You need " + item.getItemPrice() + " doubloons for this purchase.";
			}
			
			int pirateDoubloon = pirate.getDoubloons();
			int newCount = pirateDoubloon - item.getItemPrice();
			
			pirateService.updateNameChange(pirate.getPirateId(), newName, newCount);
			doubloonShopItemService.updateQuantity(item.getItemId(), item.getItemQuantity()-1);
			doubloonItemHistoryService.addNewItemPurchase(pirate.getPirateId(), inputSplit[0].trim(), newName, item.getItemPrice());
			
			return "You have successfully changed your name to *" + newName + "*!";
		}
		
		return null;
	}
	
	public String buyPollyWantACracker(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());
			DoubloonShopItem item = doubloonShopItemService.getItemByCommand(event.getText());
			
			if (item == null) {
				return "I don't recognize the shop command " + event.getText();
			}
			
			if (pirate.getDoubloons() < item.getItemPrice()) {
				return "You need " + item.getItemPrice() + " doubloons for this purchase.";
			}
			
			int pirateDoubloon = pirate.getDoubloons();
			int newCount = pirateDoubloon - item.getItemPrice();
			
			pirateService.updatePollyCommand(pirate.getPirateId(), newCount);
			doubloonShopItemService.updateQuantity(item.getItemId(), item.getItemQuantity()-1);
			doubloonItemHistoryService.addNewItemPurchase(pirate.getPirateId(), event.getText(), null, item.getItemPrice());
			
			return "You have successfully unlocked the *!pollyWantACracker* command.";
		}
		
		return null;
	}
	
	public String buyBottle(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());
			DoubloonShopItem item = doubloonShopItemService.getItemByCommand(event.getText());
			
			if (item == null) {
				return "I don't recognize the shop command " + event.getText();
			}
			
			if (pirate.getDoubloons() < item.getItemPrice()) {
				return "You need " + item.getItemPrice() + " doubloons for this purchase.";
			}
			
			int pirateDoubloon = pirate.getDoubloons();
			int newCount = pirateDoubloon - item.getItemPrice();
			
			pirateService.updateBottleCommand(pirate.getPirateId(), newCount);
			doubloonShopItemService.updateQuantity(item.getItemId(), item.getItemQuantity()-1);
			doubloonItemHistoryService.addNewItemPurchase(pirate.getPirateId(), event.getText(), null, item.getItemPrice());
			
			return "You have successfully unlocked the *!messageInABottle* command.";
		}
		
		return null;
	}
}

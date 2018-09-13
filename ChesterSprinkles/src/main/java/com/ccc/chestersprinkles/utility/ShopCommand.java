package com.ccc.chestersprinkles.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ccc.chestersprinkles.model.DoubloonShopItem;
import com.ccc.chestersprinkles.model.Pirate;
import com.ccc.chestersprinkles.model.PirateShip;
import com.ccc.chestersprinkles.service.DoubloonItemHistoryService;
import com.ccc.chestersprinkles.service.DoubloonShopItemService;
import com.ccc.chestersprinkles.service.PirateService;
import com.ccc.chestersprinkles.service.PirateShipService;

import me.ramswaroop.jbot.core.slack.models.Event;

@Component
public class ShopCommand extends Command {

	@Autowired
	private DoubloonShopItemService doubloonShopItemService;

	@Autowired
	private PirateService pirateService;

	@Autowired
	private PirateShipService pirateShipService;

	@Autowired
	private DoubloonItemHistoryService doubloonItemHistoryService;

	public String getAllShopItems(Event event) {
		if (validateInput(event)) {
			List<DoubloonShopItem> shopItems = doubloonShopItemService.getAllItems();

			StringBuilder output = new StringBuilder();

			output.append("*These are the items that available for purchase:*\n");

			for (DoubloonShopItem item : shopItems) {
				output.append("*PurchaseCommand:* ").append(item.getItemCommand()).append("\n*Price:* ")
						.append(item.getItemPrice()).append(" doubloons\n*Description:* ")
						.append(item.getItemDescription()).append("\n*Quantity left:* ").append(item.getItemQuantity())
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
			doubloonShopItemService.updateQuantity(item.getItemId(), item.getItemQuantity() - 1);
			doubloonItemHistoryService.addNewItemPurchase(pirate.getPirateId(), inputSplit[0].trim(), newName,
					item.getItemPrice());

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
			doubloonShopItemService.updateQuantity(item.getItemId(), item.getItemQuantity() - 1);
			doubloonItemHistoryService.addNewItemPurchase(pirate.getPirateId(), event.getText(), null,
					item.getItemPrice());

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
			doubloonShopItemService.updateQuantity(item.getItemId(), item.getItemQuantity() - 1);
			doubloonItemHistoryService.addNewItemPurchase(pirate.getPirateId(), event.getText(), null,
					item.getItemPrice());

			return "You have successfully unlocked the *!messageInABottle* command.";
		}

		return null;
	}

	public String buyMutiny(Event event) {
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

			pirateService.updateMutinyCommand(pirate.getPirateId(), newCount);
			doubloonShopItemService.updateQuantity(item.getItemId(), item.getItemQuantity() - 1);
			doubloonItemHistoryService.addNewItemPurchase(pirate.getPirateId(), event.getText(), null,
					item.getItemPrice());

			return "You have successfully purchased a charge of the *!mutiny* command.\n*How to use: Type !mutiny pride, in order to walk the Pride of the Tide. The other ships keywords are: insanity, dagger, scurvy, and wolf.*";
		}

		return null;
	}

	public String buyPlankSniper(Event event) {
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

			pirateService.updatePlankSniperCommand(pirate.getPirateId(), newCount);
			doubloonShopItemService.updateQuantity(item.getItemId(), item.getItemQuantity() - 1);
			doubloonItemHistoryService.addNewItemPurchase(pirate.getPirateId(), event.getText(), null,
					item.getItemPrice());

			return "You have successfully purchased a charge of the *!plankSniper* command.\n*How to use: Type !plankSniper @user in the channel. (@user should be the person you want to walk).*";
		}

		return null;
	}

	public String gamble(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());
			String message = validateDailyLootDate(pirate);
			
			if (StringUtils.isEmpty(message)) {
				DoubloonShopItem item = doubloonShopItemService.getItemByCommand(event.getText());

				int currentPoints = pirate.getPiratePoints();
				int totalPoints = pirate.getOverallPiratePoints();

				PirateShip ship = pirateShipService.getShipById(pirate.getPirateShipId());
				int shipPoints = ship.getOverallShipPoints();

				if (item == null) {
					return "I don't recognize the shop command " + event.getText();
				}

				if (pirate.getDoubloons() < item.getItemPrice()) {
					return "You need " + item.getItemPrice() + " doubloons for this purchase.";
				}

				int pirateDoubloon = pirate.getDoubloons();
				int doubloonLoot = getDoubloonsLoot();
				int pointsLoot = getPointsLoot();
				int newCount = pirateDoubloon - item.getItemPrice() + doubloonLoot;
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

				LocalDate lootDate = LocalDate.now().plusDays(7);
				String lootDateString = lootDate.format(formatter);
				
				pirateService.updatePoints(currentPoints + pointsLoot, totalPoints + pointsLoot, pirate.getUserId());
				pirateShipService.updatePoints(shipPoints + pointsLoot, ship.getShipId());
				pirateService.updateLootDateCommand(pirate.getPirateId(), newCount, lootDateString);
				doubloonShopItemService.updateQuantity(item.getItemId(), item.getItemQuantity() - 1);
				doubloonItemHistoryService.addNewItemPurchase(pirate.getPirateId(), event.getText(),
						doubloonLoot + " doubloons, " + pointsLoot + " points", item.getItemPrice());

				return "You purchase some loot. Inside the loot bag, there was " + doubloonLoot + " doubloons and "
						+ pointsLoot + " points!";
			}
			else {
				return message;
			}
		}

		return null;
	}

	private static int getDoubloonsLoot() {
		boolean bigDoubloon = new Random().nextInt(4) == 0;

		if (bigDoubloon) {
			return new Random().nextInt(5) + 5;
		} else {
			return new Random().nextInt(5) + 1;
		}
	}

	private static int getPointsLoot() {
		boolean bigPoints = new Random().nextInt(5) == 0;

		if (bigPoints) {
			return new Random().nextInt(10) + 10;
		} else {
			return new Random().nextInt(10) + 1;
		}
	}

	private String validateDailyLootDate(Pirate pirate) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			Date currentDate = new Date();
			String currentDateString = formatter.format(currentDate);

			if (StringUtils.isNotEmpty(pirate.getLootDate())
					&& formatter.parse(currentDateString).equals(formatter.parse(pirate.getLootDate()))) {
				return null;
			}
			else if (StringUtils.isNotEmpty(pirate.getLootDate())
					&& formatter.parse(currentDateString).before(formatter.parse(pirate.getLootDate()))) {
				return "You can only purchase loot once a week. Loot opens up for you again on " + pirate.getLootDate()
						+ ".";
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String buyWfh(Event event) {
		if (validateInput(event)) {
			return "Command not implemented yet.";
		}

		return null;
	}

	public String buyLotSpot(Event event) {
		if (validateInput(event)) {
			return "Command not implemented yet.";
		}

		return null;
	}
}

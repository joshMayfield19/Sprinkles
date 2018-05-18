package com.ccc.chestersprinkles.utility;

import java.util.List;

import com.ccc.chestersprinkles.model.Pirate;
import com.ccc.chestersprinkles.model.PiratePointsData;
import com.ccc.chestersprinkles.model.PirateShip;
import com.ccc.chestersprinkles.model.SlackUser;
import com.ccc.chestersprinkles.service.SlackUserService;

import me.ramswaroop.jbot.core.slack.models.Event;

public class PiratePointsCommand extends Command {
	private static String MY_INFO_COMMAND = "!mypirateinfo";
	private static String MY_SHIP_COMMAND = "!myshipinfo";
	
	public static String getCommandResponse(Event event, SlackUserService slackUserService) {
		if (commandStartsWith(event, MY_INFO_COMMAND)) {
			return getMyPirateInfoCommandResponse(event, slackUserService);
		}
		else if (commandStartsWith(event, MY_SHIP_COMMAND)) {
			return getMyShipInfoCommandResponse(event, slackUserService);
		}
		
		return null;
	}

	private static String getMyShipInfoCommandResponse(Event event, SlackUserService slackUserService) {
		SlackUser currentUser = slackUserService.getSlackUser(event.getUserId());
		String realName = currentUser.getFirstName() + " " + currentUser.getLastName();
		PiratePointsData piratePoints = PiratePointsData.getPiratePointsData();
		List<Pirate> pirates = piratePoints.getPirates();
		List<PirateShip> pirateShips = piratePoints.getPirateShips();
		
		for (Pirate pirate : pirates) {
			if (pirate.getRealName().equals(realName)) {
				for (PirateShip pirateShip : pirateShips) {
					if (pirateShip.getShipId() == pirate.getPirateShipId()) {
						return "*Ship Name:* " + pirateShip.getShipName() +"\n"
								+ "*Ship Captain:* " + pirateShip.getShipCaptain() + "\n"
										+ "*Ship Crew:* " + pirateShip.getShipCrew() + "\n"
												+ "*Ship Points:* " + pirateShip.getShipPoints() + "\n";
					}
					else {
						return "I don't see you assigned to a ship! Captain Monty!!!! Fix this!";
					}
				}
			}
		}
		
		return "I don't see you on my pirate registry. Overboard you go!";
	}

	private static String getMyPirateInfoCommandResponse(Event event, SlackUserService slackUserService) {
		SlackUser currentUser = slackUserService.getSlackUser(event.getUserId());
		String realName = currentUser.getFirstName() + " " + currentUser.getLastName();
		PiratePointsData piratePoints = PiratePointsData.getPiratePointsData();
		List<Pirate> pirates = piratePoints.getPirates();
		List<PirateShip> pirateShips = piratePoints.getPirateShips();
		String myShip = "";
		
		for (Pirate pirate : pirates) {
			if (pirate.getRealName().equals(realName)) {
				for (PirateShip pirateShip : pirateShips) {
					if (pirateShip.getShipId() == pirate.getPirateShipId()) {
						myShip = pirateShip.getShipName();
					}
				}
				
				return "*Pirate Name:* " + pirate.getPirateName() +"\n"
						+ "*Pirate Points:* " + pirate.getPiratePoints() + "\n"
								+ "*Pirate Ship:* " + myShip + "\n";
			}
		}
		
		return "I don't see you on my pirate registry. Overboard you go!";
	}
}		

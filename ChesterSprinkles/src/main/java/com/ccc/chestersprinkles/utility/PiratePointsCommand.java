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
	private static String MY_CREW_INFO_COMMAND = "!mycrewinfo";
	private static String POINTS_HELP_COMMAND = "!pointshelp";
	private static String POINTS_REWARDS_COMMAND = "!pointsrewards";
	private static String PARROT_SPEAK_COMMAND = "!parrotspeak";
	private static String POLLY_COMMAND = "!pollywantacracker";
	private static String SORTING_EYEPATCH_COMMAND = "!sortingeyepatch";
	
	private static int PRIDE_OF_TIDE = 1;
	private static int SCURVY_SUN = 2;
	private static int CRY_OF_DAGGER = 3;
	private static int BLUE_INSANITY = 4;
	private static int CORRUPT_WOLF = 5;
	
	private static SlackUser currentUser;
	private static String realName;
	private static PiratePointsData piratePoints;
	private static List<Pirate> pirates;
	private static List<PirateShip> pirateShips;
	
	private static final String JOSH_ID = "U2AR5EH8U";
	
	public static String getCommandResponse(Event event, SlackUserService slackUserService) {
		if (commandStartsWith(event, MY_INFO_COMMAND)) {
			return getMyPirateInfoCommandResponse(event, slackUserService);
		}
		else if (commandStartsWith(event, MY_SHIP_COMMAND)) {
			return getMyShipInfoCommandResponse(event, slackUserService);
		}
		else if (commandStartsWith(event, SORTING_EYEPATCH_COMMAND)) {
			return getSortingEyepatchCommandResponse(event, slackUserService); 
		}
		else if (commandStartsWith(event, MY_CREW_INFO_COMMAND)) {
			return getMyCrewInfoCommandResponse(event, slackUserService); 
		}
		else if (commandStartsWith(event, POINTS_HELP_COMMAND)) {
			return getPointsHelpCommandResponse(event, slackUserService); 
		}
		else if (commandStartsWith(event, POINTS_REWARDS_COMMAND)) {
			return getPointsRewardCommandResponse(event, slackUserService); 
		}
		else if (commandStartsWith(event, PARROT_SPEAK_COMMAND)) {
			return getParrotSpeakCommandResponse(event, slackUserService); 
		}
		else if (commandStartsWith(event, POLLY_COMMAND)) {
			return getPollyWantACrackerCommandResponse(event, slackUserService); 
		}
		
		return null;
	}
	
	private static String getPollyWantACrackerCommandResponse(Event event, SlackUserService slackUserService) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String getParrotSpeakCommandResponse(Event event, SlackUserService slackUserService) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String getPointsRewardCommandResponse(Event event, SlackUserService slackUserService) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String getPointsHelpCommandResponse(Event event, SlackUserService slackUserService) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String getMyCrewInfoCommandResponse(Event event, SlackUserService slackUserService) {
		populateSessionPirateInfo(event, slackUserService);
		
		int userShipId = 0;
		
		for (Pirate pirate : pirates) {
			if (pirate.getRealName().equals(realName)) {
				userShipId = pirate.getPirateShipId();
			}
		}
		
		if (userShipId == PRIDE_OF_TIDE) {
			return "Your fellow crew members of *The Pride of the Tide* are located on this manifest. https://drive.google.com/open?id=16pyNf8xIs7dX6hKdZ2_nerCL2hAXtrZ2";
		}
		else if (userShipId == SCURVY_SUN) {
			return "Your fellow crew members of *The Scurvy Sun* are located on this manifest. https://drive.google.com/open?id=18YqqpbGzflTenyS0zZuVvpEqsJmNt_qi";
		}
		else if (userShipId == CRY_OF_DAGGER) {
			return "Your fellow crew members of *The Cry of the Dagger* are located on this manifest. https://drive.google.com/open?id=1_VCFR8drU9jtx9I5esIIZftvrC5WDyor";
		}
		else if (userShipId == BLUE_INSANITY) {
			return "Your fellow crew members of *The Blue Insanity* are located on this manifest. https://drive.google.com/open?id=1LoudGGrfA2K0Bp_WgZpLdoNuXdB8O6tp";
		}
		else if (userShipId == CORRUPT_WOLF) {
			return "Your fellow crew members of *The Corrupted Wolf* are located on this manifest. https://drive.google.com/open?id=1o4Z5nSdM8jbmh0YnPd6eojG9asWloDf2";
		}
		else {
			return "You are not assigned to a ship yet!";
		}
		
	}

	public static String getCommandConversationResponse(Event event, SlackUserService slackUserService) {
		PiratePointsData piratePoints = PiratePointsData.getPiratePointsData();
		List<Pirate> pirates = piratePoints.getPirates();
		
		for (Pirate pirate : pirates) {
			if (pirate.getRealName().equals(event.getText())) {
				return event.getText() + " is already assigned to a ship.";
			}
		}
		
		Pirate newPirate = new Pirate(event.getText());
		PirateShip assignedShip = findShip();
		int shipCrew = assignedShip.getShipCrew();
		
		List<PirateShip> pirateShips = piratePoints.getPirateShips();
		
		for (PirateShip pirateShip : pirateShips) {
			if (pirateShip.getShipId() == assignedShip.getShipId()) {
				pirateShip.setShipCrew(++shipCrew);
			}
		}
		
		newPirate.setPirateShipId(assignedShip.getShipId());
		pirates.add(newPirate);
		piratePoints.setPirates(pirates);
		piratePoints.setPirateShips(pirateShips);
		piratePoints.writePiratePointsData(piratePoints);
		
		return "Aye, *" + event.getText() + "* has been added to the crew of *" + assignedShip.getShipName() + 
				"*. I will assign an appropriate Pirate Name soon. Type !myPirateInfo to see your info and !myShipInfo for your ship info.";
	}

	private static String getSortingEyepatchCommandResponse(Event event, SlackUserService slackUserService) {
		if (!event.getUserId().equals(JOSH_ID)) {
			return "Nice try. Not everyone can control the magical eyepatch!";
		}
		
		return "Who are we sorting this time, matey?";
	}

	private static String getMyShipInfoCommandResponse(Event event, SlackUserService slackUserService) {
		populateSessionPirateInfo(event, slackUserService);
		
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
		populateSessionPirateInfo(event, slackUserService);
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
	
	private static PirateShip findShip() {
		PiratePointsData piratePoints = PiratePointsData.getPiratePointsData();
		
		List<PirateShip> pirateShips = piratePoints.getPirateShips();
		int randomShip = getRandomNumber(5)-1;
		PirateShip designatedShip = pirateShips.get(randomShip);
		
		for (PirateShip pirateShip : pirateShips) {
			if (pirateShip.getShipCrew() < designatedShip.getShipCrew()) {
				designatedShip = pirateShip;
			}
		}
		
		return designatedShip;
	}
	
	private static void populateSessionPirateInfo(Event event, SlackUserService slackUserService) {
		currentUser = slackUserService.getSlackUser(event.getUserId());
		realName = currentUser.getFirstName() + " " + currentUser.getLastName();
		piratePoints = PiratePointsData.getPiratePointsData();
		pirates = piratePoints.getPirates();
		pirateShips = piratePoints.getPirateShips();
	}
}		

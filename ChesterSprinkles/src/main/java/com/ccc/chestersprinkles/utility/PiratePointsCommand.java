package com.ccc.chestersprinkles.utility;

import java.util.Collections;
import java.util.List;

import org.springframework.util.StringUtils;

import com.ccc.chestersprinkles.model.Pirate;
import com.ccc.chestersprinkles.model.PiratePointsData;
import com.ccc.chestersprinkles.model.PirateShip;
import com.ccc.chestersprinkles.model.SlackUser;
import com.ccc.chestersprinkles.service.SlackUserService;

import me.ramswaroop.jbot.core.slack.models.Event;

public class PiratePointsCommand extends Command {
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
	private static final String KD_ID = "U56V0HYTX";
	private static final String CALEB_ID = "U52NQPT7X";
	
	private static boolean isAllowedUser(Event event) {
		return JOSH_ID.equals(event.getUserId()) || KD_ID.equals(event.getUserId()) || CALEB_ID.equals(event.getUserId());
	}
	
	public static String getPollyWantACrackerCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			
		}
		
		return null;
	}

	public static String getParrotSpeakCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			String text = event.getText();
		}
		
		return null;
	}
	
	//Captain Command
	public static String getSetSailCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			return "Only the captain of the ship can set sail.";
		}
		
		return null;	
	}
	
	//Captain Command
	public static String getBattleCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			return "Only the captain of the ship can start a battle.";
		}
		
		return null;	
	}
	
	public static String getExploreCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			return "You need to be either on the Winning Ship or in the Top 5 at the end of the previous Pirate Adventure.";
		}
		
		return null;	
	}
	
	public static String getShoreleaveCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			return "You need to be either on the Winning Ship or in the Top 5 at the end of the previous Pirate Adventure.";
		}
		
		return null;	
	}
	
	public static String getWhatAreDoubloonsHelpCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			return "You can find out more about doubloons here: https://drive.google.com/open?id=1vd4OvcnbLPEd0awWc-EnRgiWb8eZ_y2V";
		}
		
		return null;	
	}
	
	public static String getWhatArePiratePointsHelpCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			return "You can find out more about Pirate Points here: https://drive.google.com/open?id=1zNiQnfTHsaXadI617uJGcEKWYUc7i9iq";
		}
		
		return null;	
	}
	
	public static String getPirateLeaderboardCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			populateSessionPirateInfo(event, slackUserService);
			
			Collections.sort(pirates);
			
			return "*Here are the top 5 pirates!*\n" +
					pirates.get(0).getPirateName() + " (" + pirates.get(0).getRealName() + "): " + pirates.get(0).getPiratePoints() + " points\n" +
					pirates.get(1).getPirateName() + " (" + pirates.get(1).getRealName() + "): " + pirates.get(1).getPiratePoints() + " points\n" +
					pirates.get(2).getPirateName() + " (" + pirates.get(2).getRealName() + "): " + pirates.get(2).getPiratePoints() + " points\n" +
					pirates.get(3).getPirateName() + " (" + pirates.get(3).getRealName() + "): " + pirates.get(3).getPiratePoints() + " points\n" +
					pirates.get(4).getPirateName() + " (" + pirates.get(4).getRealName() + "): " + pirates.get(4).getPiratePoints() + " points\n";
		}
		
		return null;	
	}
	
	public static String getShipLeaderboardCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			populateSessionPirateInfo(event, slackUserService);
			
			Collections.sort(pirateShips);
			
			return "*Here is the points standings for the Ships!*\n" +
					pirateShips.get(0).getShipName() + ": " + pirateShips.get(0).getShipPoints() + " points\n" +
					pirateShips.get(1).getShipName() + ": " + pirateShips.get(1).getShipPoints() + " points\n" +
					pirateShips.get(2).getShipName() + ": " + pirateShips.get(2).getShipPoints() + " points\n" +
					pirateShips.get(3).getShipName() + ": " + pirateShips.get(3).getShipPoints() + " points\n" +
					pirateShips.get(4).getShipName() + ": " + pirateShips.get(4).getShipPoints() + " points\n";
		}
		
		return null;	
	}
	
	public static String getPiratePointsHelpCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			return "You find all of the new commands here: https://drive.google.com/open?id=170xe_lczX3flsbmMsEUxKm-o09c7vWWp";
		}
		
		return null;	
	}
	
	public static String getAddPointsCommandResponse(Event event) {
		if (validateInput(event) && isAllowedUser(event)) {
			String inputString = event.getText();
			String[] inputStringSplit = inputString.split(" ");
			String user = inputStringSplit[1] + " " + inputStringSplit[2];
			int pointsToAdd = Integer.valueOf(inputStringSplit[3]);
			
			PiratePointsData piratePointsData = PiratePointsData.getPiratePointsData();
			
			List<Pirate> pirates = piratePointsData.getPirates();
			List<PirateShip> pirateShips = piratePointsData.getPirateShips();
			String shipName = "";
			
			for (Pirate pirate : pirates) {
				if (pirate.getRealName().equals(user)) {
					int currentPoints = pirate.getPiratePoints();
					pirate.setPiratePoints(currentPoints + pointsToAdd);
					
					for (PirateShip pirateShip : pirateShips) {
						if (pirateShip.getShipId() == pirate.getPirateShipId()) {
							int currentShipPoints = pirateShip.getShipPoints();
							pirateShip.setShipPoints(currentShipPoints + pointsToAdd);
							shipName = pirateShip.getShipName();
						}
					}
				}
			}
			
			piratePointsData.setPirates(pirates);
			piratePointsData.setPirateShips(pirateShips);
			PiratePointsData.writePiratePointsData(piratePointsData);
			
			return "You have added " + pointsToAdd + " points to " + user + "'s and " + shipName + "'s total.";
		}
		
		return null;
	}
	
	public static String getMyCrewInfoCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event) && isAllowedUser(event)) {
			populateSessionPirateInfo(event, slackUserService);
			
			int userShipId = 0;
			
			for (Pirate pirate : pirates) {
				if (pirate.getRealName().equals(realName)) {
					userShipId = pirate.getPirateShipId();
				}
			}
			
			if (userShipId == PRIDE_OF_TIDE) {
				return "Your fellow crew members of *The Pride of the Tide* are located on this manifest. https://drive.google.com/open?id=1ud0NDMQnZMRXlLsk1_Kj2MEolfSuwtil";
			}
			else if (userShipId == SCURVY_SUN) {
				return "Your fellow crew members of *The Scurvy Sun* are located on this manifest. https://drive.google.com/open?id=15_oIDVjnumVR83Q7MdnFz_94N8qxaeYa";
			}
			else if (userShipId == CRY_OF_DAGGER) {
				return "Your fellow crew members of *The Cry of the Dagger* are located on this manifest. https://drive.google.com/open?id=1QESOlugIXJdI1fMUI82Q4sUyFrLTOBW_";
			}
			else if (userShipId == BLUE_INSANITY) {
				return "Your fellow crew members of *The Blue Insanity* are located on this manifest. https://drive.google.com/open?id=1FGqcgpEjHE_9j9YGf_wtN6Ns75iQBYN6";
			}
			else if (userShipId == CORRUPT_WOLF) {
				return "Your fellow crew members of *The Corrupted Wolf* are located on this manifest. https://drive.google.com/open?id=1uhOtP-JG1BaPAFX0Ju5nnChK2XQtCPsc";
			}
			else {
				return "You are not assigned to a ship yet!";
			}
		}
		
		return null;
	}

	public static String getSortingEyepatchCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event) && event.getUserId().equals(JOSH_ID)) {
			String inputString = event.getText();
			String[] inputStringSplit = inputString.split(" ");
			String user = inputStringSplit[1] + " " + inputStringSplit[2];
			
			PiratePointsData piratePoints = PiratePointsData.getPiratePointsData();
			List<Pirate> pirates = piratePoints.getPirates();
			
			for (Pirate pirate : pirates) {
				if (pirate.getRealName().equals(user)) {
					return event.getText() + " is already assigned to a ship.";
				}
			}
			
			Pirate newPirate = new Pirate(user);
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
			
			return "Aye, *" + user + "* has been added to the crew of *" + assignedShip.getShipName() + 
					"*. I will assign an appropriate Pirate Name soon. Type *!myPirateInfo* to see your info and *!myShipInfo* for your ship info.";
		}
		
		return null;
	}

	public static String getMyShipInfoCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event) && isAllowedUser(event)) {
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
					}
					
					return "I don't see you assigned to a ship! Captain Monty!!!! Fix this!";
				}
			}
			
			return "I don't see you on my pirate registry. Overboard you go!";
		}
		
		return null;
	}

	public static String getMyPirateInfoCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event) && isAllowedUser(event)) {
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
							+ "*Pirate Ship:* " + myShip + "\n"	
							+ "*Pirate Points:* " + pirate.getPiratePoints() + "\n"
							+ "*Doubloons:* " + pirate.getDoubloons() + "\n";
				}
			}
			
			return "I don't see you on my pirate registry. Overboard you go!";
		}
		
		return null;
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

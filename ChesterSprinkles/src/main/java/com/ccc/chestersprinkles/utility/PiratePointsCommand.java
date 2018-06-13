package com.ccc.chestersprinkles.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import com.ccc.chestersprinkles.model.ParrotLanguageStorage;
import com.ccc.chestersprinkles.model.Pirate;
import com.ccc.chestersprinkles.model.PirateHistory;
import com.ccc.chestersprinkles.model.PiratePointsData;
import com.ccc.chestersprinkles.model.PiratePointsDataHistory;
import com.ccc.chestersprinkles.model.PirateShip;
import com.ccc.chestersprinkles.model.SlackUser;
import com.ccc.chestersprinkles.model.UpcomingEvent;
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

	private static final String KD_SPRINKLES_CHANNEL = "G7S3DCZAB";
	private static final String CODING_CHALLENGE_CHANNEL = "C5VRF4892";
	
	private static boolean isAllowedUser(Event event) {
		return JOSH_ID.equals(event.getUserId()) || KD_ID.equals(event.getUserId()) || CALEB_ID.equals(event.getUserId());
	}
	
	private static boolean isJoshUser(Event event) {
		return JOSH_ID.equals(event.getUserId());
	}
	
	public static String getPollyWantACrackerCommandResponse(Event event) {
		if (validateInput(event) && isJoshUser(event)) {
			ParrotLanguageStorage parrotLanguage = ParrotLanguageStorage.getParrotLanguageStorage();
			List<String> phrases = parrotLanguage.getPhrases();

			int randomNum = getRandomNumber(phrases.size());
			String parrotSpeak = phrases.get(randomNum-1);
			StringBuilder parrotSpeakOutput = new StringBuilder();
			String[] parrotSpeakSplit = parrotSpeak.split(" ");
			parrotSpeakOutput.append("Squawk...");
			
			for (int i = 0; i < parrotSpeakSplit.length; i++) {
				int rando = getRandomNumber(2);
				
				if (rando == 1) {
					parrotSpeakOutput.append(parrotSpeakSplit[i].toUpperCase());
				}
				else {
					parrotSpeakOutput.append(parrotSpeakSplit[i]);
				}
				
				if (i != parrotSpeakSplit.length-1) {
					parrotSpeakOutput.append(" ");
				}
			}
			
			parrotSpeakOutput.append("...Squawk!!!");
			
			return parrotSpeakOutput.toString();
		}
		
		return null;
	}
	
	public static void getGatherParrotLanguageCommandResponse(Event event) {
		if (validateInput(event)) {
			int randomNum = getRandomNumber(4);
		
			if (randomNum == 1 && event.getChannelId().equals(CODING_CHALLENGE_CHANNEL)) {
				ParrotLanguageStorage parrotLanguage = ParrotLanguageStorage.getParrotLanguageStorage();
				List<String> phrases = parrotLanguage.getPhrases();
				phrases.add(event.getText());
				parrotLanguage.setPhrases(phrases);
				ParrotLanguageStorage.writeParrotLanguageStorage(parrotLanguage);
			}
		}
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
			return "You need to be either on the Winning Ship or in the Top Five Pirates at the end of the previous Pirate Adventure.";
		}
		
		return null;	
	}
	
	public static String getShoreleaveCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			return "You need to be either on the Winning Ship or in the Top Five Pirates at the end of the previous Pirate Adventure.";
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
	
	public static String getUpcomingEventsCommandResponse(Event event) {
		if (validateInput(event)) {
			piratePoints = PiratePointsData.getPiratePointsData();
			List<UpcomingEvent> upcomingEvents = piratePoints.getUpcomingEvents();
			
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("*Here is a list of the upcoming events that award Pirate Points:*\n");
			
			for (UpcomingEvent upcomingEvent : upcomingEvents) {
				stringBuilder.append("*(").append(upcomingEvent.getDate()).append(" | ").append(upcomingEvent.getLocation()).append(")*  _")
							.append(upcomingEvent.getEvent()).append("_ -- Rewards: *").append(upcomingEvent.getPoints()).append(" points* -- ")
							.append(" Contact: *").append(upcomingEvent.getContact()).append("* for more info.\n");
			}
			
			return stringBuilder.toString();
		}
		
		return null;
	}
	
	public static String getPirateLeaderboardCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			populateSessionPirateInfo(event, slackUserService);
			
			Collections.sort(pirates);
			
			return "*Here are the top 5 pirates!*\n" +
					pirates.get(0).getPirateName() + " (" + pirates.get(0).getRealName() + "): *" + pirates.get(0).getPiratePoints() + " points*\n" +
					pirates.get(1).getPirateName() + " (" + pirates.get(1).getRealName() + "): *" + pirates.get(1).getPiratePoints() + " points*\n" +
					pirates.get(2).getPirateName() + " (" + pirates.get(2).getRealName() + "): *" + pirates.get(2).getPiratePoints() + " points*\n" +
					pirates.get(3).getPirateName() + " (" + pirates.get(3).getRealName() + "): *" + pirates.get(3).getPiratePoints() + " points*\n" +
					pirates.get(4).getPirateName() + " (" + pirates.get(4).getRealName() + "): *" + pirates.get(4).getPiratePoints() + " points*\n";
		}
		
		return null;	
	}
	
	public static String getShipLeaderboardCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			populateSessionPirateInfo(event, slackUserService);
			
			Collections.sort(pirateShips);
			
			return "*Here is the points standings for the Ships!*\n" +
					pirateShips.get(0).getShipName() + ": *" + pirateShips.get(0).getShipPoints() + " points*\n" +
					pirateShips.get(1).getShipName() + ": *" + pirateShips.get(1).getShipPoints() + " points*\n" +
					pirateShips.get(2).getShipName() + ": *" + pirateShips.get(2).getShipPoints() + " points*\n" +
					pirateShips.get(3).getShipName() + ": *" + pirateShips.get(3).getShipPoints() + " points*\n" +
					pirateShips.get(4).getShipName() + ": *" + pirateShips.get(4).getShipPoints() + " points*\n";
		}
		
		return null;	
	}
	
	public static String getPiratePointsHelpCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			return "You find all of the new commands here: https://drive.google.com/open?id=170xe_lczX3flsbmMsEUxKm-o09c7vWWp";
		}
		
		return null;	
	}
	
	public static String getStartNewAdventureCommandResponse(Event event) {
		if (validateInput(event) && isJoshUser(event)) {
			PiratePointsData piratePointsData = PiratePointsData.getPiratePointsData();
			
			List<Pirate> pirates = piratePointsData.getPirates();
			List<PirateShip> pirateShips = piratePointsData.getPirateShips();
			
			for (Pirate pirate : pirates) {
				pirate.setPiratePoints(0);
			}
			
			for (PirateShip pirateShip : pirateShips) {
				pirateShip.setShipPoints(0);
			}
			
			piratePointsData.setPirates(pirates);
			piratePointsData.setPirateShips(pirateShips);
			PiratePointsData.writePiratePointsData(piratePointsData);
			
			return "You have reset all of the adventure points.";
		}
		
		return null;
	}
	
	public static String getCalculateLeadersCommandResponse(Event event) {
		if (validateInput(event) && isJoshUser(event)) {
			PiratePointsData piratePointsData = PiratePointsData.getPiratePointsData();
			List<Pirate> pirates = piratePointsData.getPirates();
			List<PirateShip> pirateShips = piratePointsData.getPirateShips();
			
			List<Pirate> blueInsanityPirates = new ArrayList<Pirate>();
			List<Pirate> prideOfTidePirates = new ArrayList<Pirate>();
			List<Pirate> corruptedWolfPirates = new ArrayList<Pirate>();
			List<Pirate> scurvySunPirates = new ArrayList<Pirate>();
			List<Pirate> cryOfDaggerPirates = new ArrayList<Pirate>();
			
			for (Pirate pirate : pirates) {
				pirate.setOnWinningShip(false);
				pirate.setTopFivePirate(false);
				pirate.setCaptain(false);
				
				if (pirate.getPirateShipId() == PRIDE_OF_TIDE) {
					prideOfTidePirates.add(pirate);
				}
				else if (pirate.getPirateShipId() == SCURVY_SUN) {
					scurvySunPirates.add(pirate);
				}
				else if (pirate.getPirateShipId() == CRY_OF_DAGGER) {
					cryOfDaggerPirates.add(pirate);
				}
				else if (pirate.getPirateShipId() == BLUE_INSANITY) {
					blueInsanityPirates.add(pirate);
				}
				else if (pirate.getPirateShipId() == CORRUPT_WOLF) {
					corruptedWolfPirates.add(pirate);
				}
			}
			
			Collections.sort(prideOfTidePirates);
			Collections.sort(scurvySunPirates);
			Collections.sort(cryOfDaggerPirates);
			Collections.sort(blueInsanityPirates);
			Collections.sort(corruptedWolfPirates);
			Collections.sort(pirates);
			Collections.sort(pirateShips);
			
			pirates.get(0).setTopFivePirate(true);
			pirates.get(1).setTopFivePirate(true);
			pirates.get(2).setTopFivePirate(true);
			pirates.get(3).setTopFivePirate(true);
			pirates.get(4).setTopFivePirate(true);
			
			for (Pirate pirate : pirates) {
				if (pirate.getPirateShipId() == pirateShips.get(0).getShipId()) {
					pirate.setOnWinningShip(true);
				}
				
				if (pirate.getRealName().equals(cryOfDaggerPirates.get(0).getRealName()) ||
						pirate.getRealName().equals(prideOfTidePirates.get(0).getRealName()) ||
						pirate.getRealName().equals(blueInsanityPirates.get(0).getRealName()) ||
						pirate.getRealName().equals(scurvySunPirates.get(0).getRealName()) ||
						pirate.getRealName().equals(corruptedWolfPirates.get(0).getRealName())) {
					pirate.setCaptain(true);
					
					for (PirateShip pirateShip : pirateShips) {
						if (pirate.getPirateShipId() == pirateShip.getShipId()) {
							pirateShip.setShipCaptain(pirate.getPirateName() + " (" + pirate.getRealName() + ")");
						}
					}
				}
			}
			
			piratePointsData.setPirates(pirates);
			piratePointsData.setPirateShips(pirateShips);
			PiratePointsData.writePiratePointsData(piratePointsData);
			
			return "*The winning ship is* " + pirateShips.get(0).getShipName() + "!\n"
					+ "*The Top Five Pirates are:*\n " + pirates.get(0).getPirateName() + " (" + pirates.get(0).getRealName() + ")\n"
							+ pirates.get(1).getPirateName() + " (" + pirates.get(1).getRealName() + ")\n" 
							+ pirates.get(2).getPirateName() + " (" + pirates.get(2).getRealName() + ")\n"
							+ pirates.get(3).getPirateName() + " (" + pirates.get(3).getRealName() + ")\n"
							+ pirates.get(4).getPirateName() + " (" + pirates.get(4).getRealName() + ")\n"
									+ "*The captains have also been assigned for each ship!*";
		}
		
		return null;
	}
	
	public static String getAddPointsCommandResponse(Event event) {
		if (validateInput(event) && isJoshUser(event)) {
			String inputString = event.getText();
			String[] inputStringSplit = inputString.split(" ");
			String user = inputStringSplit[1] + " " + inputStringSplit[2];
			int pointsToAdd = Integer.valueOf(inputStringSplit[3]);
			String dateOfEvent = inputStringSplit[4];
			String typeOfEvent = inputStringSplit[5];
			
			PiratePointsData piratePointsData = PiratePointsData.getPiratePointsData();
			PiratePointsDataHistory piratePointsDataHistory = PiratePointsDataHistory.getPiratePointsDataHistory();
			
			List<Pirate> pirates = piratePointsData.getPirates();
			List<PirateShip> pirateShips = piratePointsData.getPirateShips();
			String shipName = "";
			
			for (Pirate pirate : pirates) {
				if (pirate.getRealName().equals(user)) {
					int currentPoints = pirate.getPiratePoints();
					int currentOverall = pirate.getOverallPiratePoints();
					pirate.setPiratePoints(currentPoints + pointsToAdd);
					pirate.setOverallPiratePoints(currentOverall + pointsToAdd);
					
					for (PirateShip pirateShip : pirateShips) {
						if (pirateShip.getShipId() == pirate.getPirateShipId()) {
							int currentShipPoints = pirateShip.getShipPoints();
							int currentOverallShip = pirateShip.getOverallShipPoints();
							pirateShip.setShipPoints(currentShipPoints + pointsToAdd);
							pirateShip.setOverallShipPoints(currentOverallShip + pointsToAdd);
							shipName = pirateShip.getShipName();
						}
					}
				}
			}
			
			List<PirateHistory> pirateHistories = piratePointsDataHistory.getPirates();
			boolean pirateHistoryFound = false;
			
			for (PirateHistory pirateHistory : pirateHistories) {
				if (pirateHistory.getRealName().equals(user)) {
					pirateHistoryFound = true;
					List<String> events = pirateHistory.getPointEvents();
					events.add(pointsToAdd + " points -- Event: " + typeOfEvent + " -- Date: " + dateOfEvent);
				}
			}
			
			if (!pirateHistoryFound) {
				PirateHistory newHistory = new PirateHistory();
				newHistory.setRealName(user);
			
				List<String> newEvents = new ArrayList<String>();
				newEvents.add(pointsToAdd + " points -- Event: " + typeOfEvent + " -- Date: " + dateOfEvent);
				newHistory.setPointEvents(newEvents);
			
				pirateHistories.add(newHistory);
			}
			
			piratePointsDataHistory.setPirates(pirateHistories);
			piratePointsDataHistory.writePiratePointsDataHistory(piratePointsDataHistory);
			
			piratePointsData.setPirates(pirates);
			piratePointsData.setPirateShips(pirateShips);
			PiratePointsData.writePiratePointsData(piratePointsData);
			
			return "You have added " + pointsToAdd + " points to " + user + "'s and " + shipName + "'s total.";
		}
		
		return null;
	}
	
	public static String getSample(Event event) {
		if (validateInput(event)) {
			return "<@U2AR5EH8U>";
		}
		return null;
	}
	
	public static String getMyCrewInfoCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			populateSessionPirateInfo(event, slackUserService);
			
			int userShipId = 0;
			
			for (Pirate pirate : pirates) {
				if (pirate.getRealName().equals(realName)) {
					userShipId = pirate.getPirateShipId();
				}
			}
			
			if (userShipId == PRIDE_OF_TIDE) {
				return "Your fellow crew members of *The Pride of the Tide* are located on this manifest. https://drive.google.com/open?id=1w-bLGbdrjg1aEj-IJ1Ue_rz_tsoG1u9U";
			}
			else if (userShipId == SCURVY_SUN) {
				return "Your fellow crew members of *The Scurvy Sun* are located on this manifest. https://drive.google.com/open?id=1KsUnp3Ple22u3Us9P4NdPVJsJ7VOIuoj";
			}
			else if (userShipId == CRY_OF_DAGGER) {
				return "Your fellow crew members of *The Cry of the Dagger* are located on this manifest. https://drive.google.com/open?id=1YOHC-oeMR1hGIGbsen90c0CXkJrdXIXm";
			}
			else if (userShipId == BLUE_INSANITY) {
				return "Your fellow crew members of *The Blue Insanity* are located on this manifest. https://drive.google.com/open?id=1s5HIAIjhbCeSLxv_V9dCCYSU60mUeF1U";
			}
			else if (userShipId == CORRUPT_WOLF) {
				return "Your fellow crew members of *The Corrupted Wolf* are located on this manifest. https://drive.google.com/open?id=1wPPq-KsXH_ar1fCvJKMh52sfWMEezcIC";
			}
			else {
				return "You are not assigned to a ship yet!";
			}
		}
		
		return null;
	}

	public static String getSortingEyepatchCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event) && isJoshUser(event)) {
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
					"*. I will assign an appropriate Pirate Name soon. Start a new Direct Message with me and type *!myPirateInfo* to see your info and *!myShipInfo* for your ship info.";
		}
		
		return null;
	}

	public static String getMyShipInfoCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
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
	
	public static String getDirectMessageChannelCommandResponse(Event event) {
		if (validateInput(event)) {
			return "This command is a *Direct Message* command only. You can open up a new *Direct Message* with me (left nav bar, + sign) and I can help you out there. :smile:";
		}
		
		return null;
	}

	public static String getMyPirateInfoCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
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

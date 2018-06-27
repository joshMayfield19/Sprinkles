package com.ccc.chestersprinkles.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ccc.chestersprinkles.model.DoubloonActivity;
import com.ccc.chestersprinkles.model.ParrotLanguageStorage;
import com.ccc.chestersprinkles.model.Pirate;
import com.ccc.chestersprinkles.model.PiratePointsData;
import com.ccc.chestersprinkles.model.PiratePointsHistory;
import com.ccc.chestersprinkles.model.PirateShip;
import com.ccc.chestersprinkles.model.SlackUser;
import com.ccc.chestersprinkles.model.UpcomingEvent;
import com.ccc.chestersprinkles.service.DoubloonActivityService;
import com.ccc.chestersprinkles.service.PiratePointsHistoryService;
import com.ccc.chestersprinkles.service.PirateService;
import com.ccc.chestersprinkles.service.PirateShipService;
import com.ccc.chestersprinkles.service.SlackUserService;

import me.ramswaroop.jbot.core.slack.models.Event;

@Component
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

	@Autowired
	private SlackUserService slackUserService;

	@Autowired
	private PirateService pirateService;

	@Autowired
	private PirateShipService pirateShipService;

	@Autowired
	private PiratePointsHistoryService piratePointsHistoryService;

	@Autowired
	private DoubloonActivityService doubloonActivityService;

	private static boolean isAllowedUser(Event event) {
		return JOSH_ID.equals(event.getUserId()) || KD_ID.equals(event.getUserId())
				|| CALEB_ID.equals(event.getUserId());
	}

	private static boolean isJoshUser(Event event) {
		return JOSH_ID.equals(event.getUserId());
	}

	public static String getPollyWantACrackerCommandResponse(Event event) {
		if (validateInput(event) && isJoshUser(event)) {
			ParrotLanguageStorage parrotLanguage = ParrotLanguageStorage.getParrotLanguageStorage();
			List<String> phrases = parrotLanguage.getPhrases();

			int randomNum = getRandomNumber(phrases.size());
			String parrotSpeak = phrases.get(randomNum - 1);
			StringBuilder parrotSpeakOutput = new StringBuilder();
			String[] parrotSpeakSplit = parrotSpeak.split(" ");
			parrotSpeakOutput.append("Squawk...");

			for (int i = 0; i < parrotSpeakSplit.length; i++) {
				int rando = getRandomNumber(2);

				if (rando == 1) {
					parrotSpeakOutput.append(parrotSpeakSplit[i].toUpperCase());
				} else {
					parrotSpeakOutput.append(parrotSpeakSplit[i]);
				}

				if (i != parrotSpeakSplit.length - 1) {
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
	
	public String getActivateDoubloonsCommandResponse(Event event) {
		if (validateInput(event)) {
			String[] inputString = event.getText().split(" ");
			int shipId = Integer.parseInt(inputString[1]);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDate startDate = LocalDate.now();
			String startDateString = startDate.format(formatter);
			
			LocalDate endDate = LocalDate.now().plusDays(14);
			String endDateString = endDate.format(formatter);
			
			List<Pirate> pirates = pirateService.getPiratesByShipId(shipId);
			
			Collections.sort(pirates);
			
			pirates.get(0).setCaptain(true);
			
			for (Pirate pirate : pirates) {
				if (pirate.getPiratePoints() != 0) {
					pirate.setOnWinningShip(true);
					pirateService.updateDoubloonsActivation(pirate);
					doubloonActivityService.updateCommandStartEndDate(startDateString, endDateString, pirate.getPirateId());
				}
			}
			
			pirateShipService.updateCaptainByShipId(shipId, pirates.get(0).getPirateId());
			
			return "You have activated the doubloons commands for ship id " + shipId;
		}
		
		return null;
	}

	// Captain Command
	public List<String> getSetSailCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());

			List<String> responses = new ArrayList<String>();

			if (pirate.isCaptain()) {
				String message = validateDailyDoubloonCount(pirate, "setSail");

				if (StringUtils.isNotEmpty(message)) {
					responses.add(message);
					return responses;
				}
				
				int doubloonsFound = getDoubloons();
				int doubloonsTotal = pirate.getDoubloons() + doubloonsFound;
				pirate.setDoubloons(doubloonsTotal);
				responses.add("You set sail and find *" + doubloonsFound + "* doubloons!\n"
						+ "The last day you can run this command is *" + pirate.getDoubloonActivity().getCommandEndDate() + "*.");

				if (doubloonsFound == 5) {
					responses.add("*" + pirate.getSlackUser().getFirstName() + " " + pirate.getSlackUser().getLastName()
							+ "* just found a whopping *5 doubloons* while sailing!!!");
				}

				pirateService.updateDoubloons(doubloonsTotal, pirate.getPirateId());
				
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Date currentDate = new Date();
				String currentDateString = formatter.format(currentDate);

				doubloonActivityService.updateSetSail(currentDateString, pirate.getPirateId());
			} else {
				responses.add("You need to be the Captain of your ship.");
			}

			return responses;
		}

		return null;
	}

	// Captain Command
	public List<String> getBattleCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());

			List<String> responses = new ArrayList<String>();

			if (pirate.isCaptain()) {
				String message = validateDailyDoubloonCount(pirate, "battle");

				if (StringUtils.isNotEmpty(message)) {
					responses.add(message);
					return responses;
				}
				
				int doubloonsFound = getDoubloons();
				int doubloonsTotal = pirate.getDoubloons() + doubloonsFound;
				pirate.setDoubloons(doubloonsTotal);
				responses.add("You initiate a battle and find *" + doubloonsFound + "* doubloons!\n"
						+ "The last day you can run this command is *" + pirate.getDoubloonActivity().getCommandEndDate() + "*.");

				if (doubloonsFound == 5) {
					responses.add("*" + pirate.getSlackUser().getFirstName() + " " + pirate.getSlackUser().getLastName()
							+ "* just found a whopping *5 doubloons* while battling!!!");
				}

				pirateService.updateDoubloons(doubloonsTotal, pirate.getPirateId());
				
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Date currentDate = new Date();
				String currentDateString = formatter.format(currentDate);

				doubloonActivityService.updateBattle(currentDateString, pirate.getPirateId());
			} else {
				responses.add("You need to be the Captain of your ship.");
			}

			return responses;
		}

		return null;
	}

	public List<String> getExploreCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());

			List<String> responses = new ArrayList<String>();

			if (pirate.isOnWinningShip() || pirate.isTopFivePirate()) {
				String message = validateDailyDoubloonCount(pirate, "explore");

				if (StringUtils.isNotEmpty(message)) {
					responses.add(message);
					return responses;
				}
				
				int doubloonsFound = getDoubloons();
				int doubloonsTotal = pirate.getDoubloons() + doubloonsFound;
				pirate.setDoubloons(doubloonsTotal);
				responses.add("You explore a nearby island and find *" + doubloonsFound + "* doubloons!\n"
						+ "The last day you can run this command is *" + pirate.getDoubloonActivity().getCommandEndDate() + "*.");

				if (doubloonsFound == 5) {
					responses.add("*" + pirate.getSlackUser().getFirstName() + " " + pirate.getSlackUser().getLastName()
							+ "* just found a whopping *5 doubloons* while exploring an island!!!");
				}

				pirateService.updateDoubloons(doubloonsTotal, pirate.getPirateId());
				
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Date currentDate = new Date();
				String currentDateString = formatter.format(currentDate);

				doubloonActivityService.updateExplore(currentDateString, pirate.getPirateId());
			} else {
				responses.add(
						"You need to be either on the Winning Ship or in the Top Five Pirates at the end of the previous Pirate Adventure.");
			}

			return responses;
		}

		return null;
	}

	public List<String> getShoreleaveCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());

			List<String> responses = new ArrayList<String>();

			if (pirate.isOnWinningShip() || pirate.isTopFivePirate()) {
				String message = validateDailyDoubloonCount(pirate, "shoreleave");

				if (StringUtils.isNotEmpty(message)) {
					responses.add(message);
					return responses;
				}

				int doubloonsFound = getDoubloons();
				int doubloonsTotal = pirate.getDoubloons() + doubloonsFound;
				pirate.setDoubloons(doubloonsTotal);
				responses.add("While on shoreleave you find *" + doubloonsFound + "* doubloons!\n"
						+ "The last day you can run this command is *" + pirate.getDoubloonActivity().getCommandEndDate() + "*.");

				if (doubloonsFound == 5) {
					responses.add("*" + pirate.getSlackUser().getFirstName() + " " + pirate.getSlackUser().getLastName()
							+ "* just found a whopping *5 doubloons* while on shoreleave!!!");
				}

				pirateService.updateDoubloons(doubloonsTotal, pirate.getPirateId());

				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Date currentDate = new Date();
				String currentDateString = formatter.format(currentDate);

				doubloonActivityService.updateShoreleave(currentDateString, pirate.getPirateId());
			} else {
				responses.add(
						"You need to be either on the Winning Ship or in the Top Five Pirates at the end of the previous Pirate Adventure.");
			}

			return responses;
		}

		return null;
	}

	private String validateDailyDoubloonCount(Pirate pirate, String type) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			Date currentDate = new Date();
			String currentDateString = formatter.format(currentDate);

			DoubloonActivity activity = pirate.getDoubloonActivity();

			if (StringUtils.isEmpty(activity.getCommandStartDate()) || StringUtils.isEmpty(activity.getCommandEndDate())
					|| formatter.parse(currentDateString).before(formatter.parse(activity.getCommandStartDate()))
					|| formatter.parse(currentDateString).after(formatter.parse(activity.getCommandEndDate()))) {
				return "The doubloons commands are not active at this point.";
			} else if (type.equals("shoreleave") && StringUtils.isNotEmpty(activity.getLastShoreleaveDate())
					&& formatter.parse(currentDateString).equals(formatter.parse(activity.getLastShoreleaveDate()))) {
				return "You can only run the !shoreleave command once a day.";
			} else if (type.equals("explore") && StringUtils.isNotEmpty(activity.getLastExploreDate())
					&& formatter.parse(currentDateString).equals(formatter.parse(activity.getLastExploreDate()))) {
				return "You can only run the !explore command once a day.";
			} else if (type.equals("setSail") && StringUtils.isNotEmpty(activity.getLastSetSailDate())
					&& formatter.parse(currentDateString).equals(formatter.parse(activity.getLastSetSailDate()))) {
				return "You can only run the !setSail command once a day.";
			} else if (type.equals("battle") && StringUtils.isNotEmpty(activity.getLastBattleDate())
					&& formatter.parse(currentDateString).equals(formatter.parse(activity.getLastBattleDate()))) {
				return "You can only run the !battle command once a day.";
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static int getDoubloons() {
		return new Random().nextInt(10) == 0 ? 5 : (new Random().nextInt(3) == 0 ? 2 : 1);
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

	public String getPirateLeaderboardCommandResponse(Event event) {
		if (validateInput(event)) {
			List<Pirate> pirates = pirateService.getTopFivePirates();

			return "*Here are the top 5 pirates!*\n" + pirates.get(0).getPirateName() + " ("
					+ pirates.get(0).getSlackUser().getFirstName() + " " + pirates.get(0).getSlackUser().getLastName()
					+ "): *" + pirates.get(0).getPiratePoints() + "* points\n" + pirates.get(1).getPirateName() + " ("
					+ pirates.get(1).getSlackUser().getFirstName() + " " + pirates.get(1).getSlackUser().getLastName()
					+ "): *" + pirates.get(1).getPiratePoints() + "* points\n" + pirates.get(2).getPirateName() + " ("
					+ pirates.get(2).getSlackUser().getFirstName() + " " + pirates.get(2).getSlackUser().getLastName()
					+ "): *" + pirates.get(2).getPiratePoints() + "* points\n" + pirates.get(3).getPirateName() + " ("
					+ pirates.get(3).getSlackUser().getFirstName() + " " + pirates.get(3).getSlackUser().getLastName()
					+ "): *" + pirates.get(3).getPiratePoints() + "* points\n" + pirates.get(4).getPirateName() + " ("
					+ pirates.get(4).getSlackUser().getFirstName() + " " + pirates.get(4).getSlackUser().getLastName()
					+ "): *" + pirates.get(4).getPiratePoints() + "* points\n";
		}

		return null;
	}

	public String getShipLeaderboardCommandResponse(Event event) {
		if (validateInput(event)) {
			List<PirateShip> pirateShips = pirateShipService.getTopShips();

			return "*Here is the points standings for the Ships!*\n" + pirateShips.get(0).getShipName() + ": *"
					+ pirateShips.get(0).getShipPoints() + "* points\n" + pirateShips.get(1).getShipName() + ": *"
					+ pirateShips.get(1).getShipPoints() + "* points\n" + pirateShips.get(2).getShipName() + ": *"
					+ pirateShips.get(2).getShipPoints() + "* points\n" + pirateShips.get(3).getShipName() + ": *"
					+ pirateShips.get(3).getShipPoints() + "* points\n" + pirateShips.get(4).getShipName() + ": *"
					+ pirateShips.get(4).getShipPoints() + "* points\n";
		}

		return null;
	}

	public static String getPiratePointsHelpCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			return "You find all of the new commands here: https://drive.google.com/open?id=1uiRupRX_9zF_C8AUDpnUC3fsSWNFVs6A";
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
				} else if (pirate.getPirateShipId() == SCURVY_SUN) {
					scurvySunPirates.add(pirate);
				} else if (pirate.getPirateShipId() == CRY_OF_DAGGER) {
					cryOfDaggerPirates.add(pirate);
				} else if (pirate.getPirateShipId() == BLUE_INSANITY) {
					blueInsanityPirates.add(pirate);
				} else if (pirate.getPirateShipId() == CORRUPT_WOLF) {
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

				if (pirate.getRealName().equals(cryOfDaggerPirates.get(0).getRealName())
						|| pirate.getRealName().equals(prideOfTidePirates.get(0).getRealName())
						|| pirate.getRealName().equals(blueInsanityPirates.get(0).getRealName())
						|| pirate.getRealName().equals(scurvySunPirates.get(0).getRealName())
						|| pirate.getRealName().equals(corruptedWolfPirates.get(0).getRealName())) {
					pirate.setCaptain(true);

					for (PirateShip pirateShip : pirateShips) {
						if (pirate.getPirateShipId() == pirateShip.getShipId()) {
							// pirateShip.setShipCaptain(pirate.getPirateName()
							// + " (" + pirate.getRealName() + ")");
						}
					}
				}
			}

			piratePointsData.setPirates(pirates);
			piratePointsData.setPirateShips(pirateShips);
			PiratePointsData.writePiratePointsData(piratePointsData);

			return "*The winning ship is* " + pirateShips.get(0).getShipName() + "!\n"
					+ "*The Top Five Pirates are:*\n " + pirates.get(0).getPirateName() + " ("
					+ pirates.get(0).getRealName() + ")\n" + pirates.get(1).getPirateName() + " ("
					+ pirates.get(1).getRealName() + ")\n" + pirates.get(2).getPirateName() + " ("
					+ pirates.get(2).getRealName() + ")\n" + pirates.get(3).getPirateName() + " ("
					+ pirates.get(3).getRealName() + ")\n" + pirates.get(4).getPirateName() + " ("
					+ pirates.get(4).getRealName() + ")\n" + "*The captains have also been assigned for each ship!*";
		}

		return null;
	}

	public static String getUpcomingEventsCommandResponse(Event event) {
		if (validateInput(event)) {
			piratePoints = PiratePointsData.getPiratePointsData();
			List<UpcomingEvent> upcomingEvents = piratePoints.getUpcomingEvents();

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("*Here is a list of the upcoming events that award points:*\n");

			for (UpcomingEvent upcomingEvent : upcomingEvents) {
				stringBuilder.append("*(").append(upcomingEvent.getDate()).append(" | ")
						.append(upcomingEvent.getLocation()).append(")*  _").append(upcomingEvent.getEvent())
						.append("_ -- Rewards: *").append(upcomingEvent.getPoints()).append(" points* -- ")
						.append("Contact: *").append(upcomingEvent.getContact()).append("* for more info.\n");
			}

			return stringBuilder.toString();
		}

		return null;
	}

	public String getAddPointsCommandResponse(Event event) {
		if (validateInput(event) && isJoshUser(event)) {
			String[] inputString = event.getText().split(" ");
			String user = inputString[1] + " " + inputString[2];
			int pointsToAdd = Integer.valueOf(inputString[3]);
			String dateOfEvent = inputString[4];
			String typeOfEvent = inputString[5];

			Pirate pirate = pirateService.getPirateByName(inputString[1], inputString[2]);
			pirateService.updatePoints((pirate.getPiratePoints() + pointsToAdd), pirate.getUserId());

			PirateShip pirateShip = pirateShipService.getShipById(pirate.getPirateShipId());
			pirateShipService.updatePoints((pirateShip.getShipPoints() + pointsToAdd), pirate.getPirateShipId());

			PiratePointsHistory newHistoryEvent = new PiratePointsHistory();
			newHistoryEvent.setPirateId(pirate.getPirateId());
			newHistoryEvent.setDateOfEvent(dateOfEvent);
			newHistoryEvent.setEvent(typeOfEvent);
			newHistoryEvent.setPoints(pointsToAdd);

			piratePointsHistoryService.addNewEvent(newHistoryEvent);

			return "You have added " + pointsToAdd + " points to " + user + "'s and " + pirateShip.getShipName()
					+ "'s total.";
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
			} else if (userShipId == SCURVY_SUN) {
				return "Your fellow crew members of *The Scurvy Sun* are located on this manifest. https://drive.google.com/open?id=1KsUnp3Ple22u3Us9P4NdPVJsJ7VOIuoj";
			} else if (userShipId == CRY_OF_DAGGER) {
				return "Your fellow crew members of *The Cry of the Dagger* are located on this manifest. https://drive.google.com/open?id=1YOHC-oeMR1hGIGbsen90c0CXkJrdXIXm";
			} else if (userShipId == BLUE_INSANITY) {
				return "Your fellow crew members of *The Blue Insanity* are located on this manifest. https://drive.google.com/open?id=1s5HIAIjhbCeSLxv_V9dCCYSU60mUeF1U";
			} else if (userShipId == CORRUPT_WOLF) {
				return "Your fellow crew members of *The Corrupted Wolf* are located on this manifest. https://drive.google.com/open?id=1wPPq-KsXH_ar1fCvJKMh52sfWMEezcIC";
			} else {
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

			return "Aye, *" + user + "* has been added to the crew of *" + assignedShip.getShipName()
					+ "*. I will assign an appropriate Pirate Name soon. Start a new Direct Message with me and type *!myPirateInfo* "
					+ "to see your info and *!myShipInfo* for your ship info."
					+ " *(NOTE: There could be a chance that I don't reply to you in the Direct Message. If that's "
					+ "the case let Josh Mayfield know. He will fix it. He has to be good for something.)";
		}

		return null;
	}

	public String getMyShipInfoCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());
			PirateShip pirateShip = pirateShipService.getShipById(pirate.getPirateShipId());
			List<Pirate> pirates = pirateService.getPiratesByShipId(pirate.getPirateShipId());
			String captain = "No Captain Yet.";
			
			for (Pirate pirateCapt : pirates) {
				if (pirateCapt.isCaptain()) {
					captain = pirateCapt.getPirateName();
					break;
				}
			}
			
			if (pirate != null && pirateShip != null) {
				return "*Ship Name:* " + pirateShip.getShipName() + "\n" + "*Ship Captain:* "
						+ captain + "\n" + "*Ship Crew:* " + pirateShip.getShipCrew() + "\n"
						+ "*Ship Points:* " + pirateShip.getShipPoints() + "\n";
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

	public String getMyPirateInfoCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());
			PirateShip pirateShip = pirateShipService.getShipById(pirate.getPirateShipId());

			if (pirate != null && pirateShip != null) {
				return "*Pirate Name:* " + pirate.getPirateName() + "\n" + "*Pirate Ship:* " + pirateShip.getShipName()
						+ "\n" + "*Pirate Points:* " + pirate.getPiratePoints() + "\n" + "*Doubloons:* "
						+ pirate.getDoubloons() + "\n";
			}

			return "I don't see you on my pirate registry. Overboard you go!";
		}

		return null;
	}

	private static PirateShip findShip() {
		PiratePointsData piratePoints = PiratePointsData.getPiratePointsData();

		List<PirateShip> pirateShips = piratePoints.getPirateShips();
		int randomShip = getRandomNumber(5) - 1;
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

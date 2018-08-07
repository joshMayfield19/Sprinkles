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

import com.ccc.chestersprinkles.model.BottleEvent;
import com.ccc.chestersprinkles.model.DoubloonActivity;
import com.ccc.chestersprinkles.model.ParrotLanguageStorage;
import com.ccc.chestersprinkles.model.Pirate;
import com.ccc.chestersprinkles.model.PiratePointsHistory;
import com.ccc.chestersprinkles.model.PirateShip;
import com.ccc.chestersprinkles.model.PointEvent;
import com.ccc.chestersprinkles.model.SlackUser;
import com.ccc.chestersprinkles.service.BottleEventService;
import com.ccc.chestersprinkles.service.DoubloonActivityService;
import com.ccc.chestersprinkles.service.PiratePointsHistoryService;
import com.ccc.chestersprinkles.service.PirateService;
import com.ccc.chestersprinkles.service.PirateShipService;
import com.ccc.chestersprinkles.service.PointEventService;
import com.ccc.chestersprinkles.service.SlackUserService;

import me.ramswaroop.jbot.core.slack.models.Event;

@Component
public class PiratePointsCommand extends Command {
	private static final String JOSH_ID = "UBYAE4ANA";

	private static final String KD_SPRINKLES_CHANNEL = "GC2RPBCA0";
	private static final String CODING_CHALLENGE_CHANNEL = "CC06WLNA1";

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

	@Autowired
	private PointEventService eventService;

	@Autowired
	private BottleEventService bottleEventService;

	private static boolean isJoshUser(Event event) {
		return JOSH_ID.equals(event.getUserId());
	}

	public String getMessageInABottleCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());
			
			if (pirate.isCanBottle()) {
				BottleEvent bottleEvent = bottleEventService.getBottleEventByPirateId(pirate.getPirateId());
				int bottleDoub = bottleEvent.getDoubReward();
				int bottlePoint = bottleEvent.getPointReward();
				
				if (bottleEvent.getBottleEndDate() == null) {
					return "No message in a bottle has appeared to you yet.";
				}
				
				if (bottleEvent.isRewardCollected()) {
					return "You have already read your message. You can't receive a new message again until after " + bottleEvent.getBottleEndDate() + ".";
				}
				
				int currentPoints = pirate.getPiratePoints();
				int totalPoints = pirate.getOverallPiratePoints();
				int doubloons = pirate.getDoubloons();
				
				PirateShip ship = pirateShipService.getShipById(pirate.getPirateShipId());
				int shipPoints = ship.getOverallShipPoints();
				
				boolean doubloonReward = new Random().nextInt(50) == 0;
				
				if (doubloonReward) {
					int newDoub = new Random().nextInt(5) + 1;
					pirateService.updateDoubloons(doubloons + newDoub, pirate.getPirateId());
					bottleEventService.updateDoubloons(newDoub + bottleDoub, pirate.getPirateId());
					
					return "You opened the message and  " + newDoub + " doubloons fell out!";
				}
				else {
					int newPoints = new Random().nextInt(20) + 1;
					pirateService.updatePoints(currentPoints + newPoints, totalPoints + newPoints, pirate.getUserId());
					pirateShipService.updatePoints(shipPoints + newPoints, ship.getShipId());
					bottleEventService.updatePoints(newPoints + bottlePoint, pirate.getPirateId());
					
					return "You opened the message and " + newPoints + " points appeared before you!";
				}
			}
			else {
				return "You haven't purchased the *!messageInABottle* command.";
			}
		}

		return null;
	}

	public List<String> getStartNewMessageCommandResponse(Event event) {
		if (validateInput(event)) {
			boolean triggerEvent = new Random().nextInt(30) == 0;
			
			if (triggerEvent && event.getChannelId().equals(CODING_CHALLENGE_CHANNEL)) {
				List<String> responses = new ArrayList<String>();
				List<Pirate> pirates = pirateService.getBottlePirates();
				
				if (pirates.size()==0) {
					return null;
				}
				
				int randomPirate = getRandomNumber(pirates.size());
				
				if (randomPirate > 0) {
					randomPirate = randomPirate -1;
				}
				
				Pirate pirate = pirates.get(randomPirate);
				
				if (StringUtils.isEmpty(pirate.getChannelId())) {
					return null;
				}
				
				BottleEvent bottleEvent = bottleEventService.getBottleEventByPirateId(pirate.getPirateId());

				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				
				Date currentDate = new Date();
				String currentDateString = formatter.format(currentDate);
				
				LocalDate endDate = LocalDate.now().plusDays(5);
				String endDateString = endDate.format(formatter2);

				try {
					if (bottleEvent.getBottleEndDate() == null) {
						bottleEvent = new BottleEvent();
						bottleEvent.setPirateId(pirate.getPirateId());
						bottleEvent.setBottleStartDate(currentDateString);
						bottleEvent.setBottleEndDate(endDateString);
						bottleEvent.setDoubReward(0);
						bottleEvent.setPointReward(0);
						bottleEvent.setRewardCollected(false);
						
						bottleEventService.addNewBottleEvent(bottleEvent);
						
						responses.add("A message in a bottle has floated up to you... Type *!messageInABottle* to read the message.");
						responses.add(pirate.getChannelId());
						
						return responses;
					} else if (formatter.parse(currentDateString)
							.after(formatter.parse(bottleEvent.getBottleEndDate()))) {
						bottleEventService.updateDates(currentDateString, endDateString, 0, pirate.getPirateId());
						
						responses.add("A message in a bottle has floated up to you... Type *!messageInABottle* to read the message.");
						responses.add(pirate.getChannelId());
						
						return responses;
					} else {
						return null;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	public String getPollyWantACrackerCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());

			if (pirate.isCanPolly()) {
				ParrotLanguageStorage parrotLanguage = ParrotLanguageStorage.getParrotLanguageStorage();
				List<String> phrases = parrotLanguage.getPhrases();

				int randomNum = getRandomNumber(phrases.size() - 1);
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
			} else {
				return "You haven't purchased this command.";
			}
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

	public String getTopFiveActivationResponse(Event event) {
		if (validateInput(event) && isJoshUser(event)) {
			List<Pirate> pirates = pirateService.getTopFivePirates();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDate startDate = LocalDate.now();
			String startDateString = startDate.format(formatter);

			LocalDate endDate = LocalDate.now().plusDays(7);
			String endDateString = endDate.format(formatter);

			for (Pirate pirate : pirates) {
				pirate.setTopFivePirate(true);
				pirateService.updateTopFivePirate(pirate);
				doubloonActivityService.updateTopFiveStartEndDate(startDateString, endDateString, pirate.getPirateId());
			}

			pirateService.updateZeroPoints();

			return "You have activated the Top Five Pirates.";
		}

		return null;
	}

	public String getTopFiveDeactivationResponse(Event event) {
		if (validateInput(event) && isJoshUser(event)) {
			List<Pirate> pirates = pirateService.getTopFivePiratesToDeactivate();

			for (Pirate pirate : pirates) {
				pirate.setTopFivePirate(false);
				pirateService.updateTopFivePirate(pirate);
				doubloonActivityService.deactivateTopFive(pirate.getPirateId());
			}

			return "You have deactivated the Top Five Pirates.";
		}

		return null;
	}

	public String getAdventureCommandResponse(Event event) {
		if (validateInput(event)) {
			return "*Here is the list of the current destinations*:\n(1) Monsoon Lagoon --- 1750 points\n"
					+ "(2) The Gloomy Isles --- 5000 points\n" + "(3) The Volcanic Haven --- 9500 points.\n\n"
					+ "*Here is the list of upcoming Top Five pirates dates*:\n"
					+ "(1) 7/6/18\n(2) 8/22/18\n(3) 9/28/18";
		}

		return null;
	}

	public String getActivateDoubloonsCommandResponse(Event event) {
		if (validateInput(event) && isJoshUser(event)) {
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
				if (pirate.getOverallPiratePoints() != 0) {
					pirate.setOnWinningShip(true);
					pirateService.updateDoubloonsActivation(pirate);
					doubloonActivityService.updateCommandStartEndDate(startDateString, endDateString,
							pirate.getPirateId());
				}
			}

			pirateShipService.updateCaptainByShipId(shipId, pirates.get(0).getPirateId());

			return "You have activated the doubloons commands for ship id " + shipId;
		}

		return null;
	}

	public String getDeactivateDoubloonsCommandResponse(Event event) {
		if (validateInput(event) && isJoshUser(event)) {
			String[] inputString = event.getText().split(" ");
			int shipId = Integer.parseInt(inputString[1]);

			List<Pirate> pirates = pirateService.getPiratesByShipId(shipId);

			for (Pirate pirate : pirates) {
				pirate.setOnWinningShip(false);
				pirateService.updateDoubloonsActivation(pirate);
				doubloonActivityService.deactivateCommands(pirate.getPirateId());
			}

			return "You have deactivated the doubloons commands for ship id " + shipId;
		}

		return null;
	}

	// Captain Command
	public List<String> getSetSailCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());

			List<String> responses = new ArrayList<String>();

			if (StringUtils.isEmpty(pirate.getChannelId())) {
				pirateService.updateChannelId(pirate.getPirateId(), event.getChannelId());
			}

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
						+ "The last day you can run this command is *"
						+ pirate.getDoubloonActivity().getCommandEndDate() + "*.");

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

			if (StringUtils.isEmpty(pirate.getChannelId())) {
				pirateService.updateChannelId(pirate.getPirateId(), event.getChannelId());
			}

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
						+ "The last day you can run this command is *"
						+ pirate.getDoubloonActivity().getCommandEndDate() + "*.");

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

			if (StringUtils.isEmpty(pirate.getChannelId())) {
				pirateService.updateChannelId(pirate.getPirateId(), event.getChannelId());
			}

			if (pirate.isOnWinningShip()) {
				String message = validateDailyDoubloonCount(pirate, "explore");

				if (StringUtils.isNotEmpty(message)) {
					responses.add(message);
					return responses;
				}

				int doubloonsFound = getDoubloons();
				int doubloonsTotal = pirate.getDoubloons() + doubloonsFound;
				pirate.setDoubloons(doubloonsTotal);
				responses.add("You explore a nearby island and find *" + doubloonsFound + "* doubloons!\n"
						+ "The last day you can run this command is *"
						+ pirate.getDoubloonActivity().getCommandEndDate() + "*.");

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
				responses.add("Your ship needs to have reached an island.");
			}

			return responses;
		}

		return null;
	}

	public List<String> getShoreleaveCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());

			List<String> responses = new ArrayList<String>();

			if (StringUtils.isEmpty(pirate.getChannelId())) {
				pirateService.updateChannelId(pirate.getPirateId(), event.getChannelId());
			}

			if (pirate.isOnWinningShip()) {
				String message = validateDailyDoubloonCount(pirate, "shoreleave");

				if (StringUtils.isNotEmpty(message)) {
					responses.add(message);
					return responses;
				}

				int doubloonsFound = getDoubloons();
				int doubloonsTotal = pirate.getDoubloons() + doubloonsFound;
				pirate.setDoubloons(doubloonsTotal);
				responses.add("While on shoreleave you find *" + doubloonsFound + "* doubloons!\n"
						+ "The last day you can run this command is *"
						+ pirate.getDoubloonActivity().getCommandEndDate() + "*.");

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
				responses.add("Your ship needs to have reached an island.");
			}

			return responses;
		}

		return null;
	}

	public List<String> getPlunderCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());

			List<String> responses = new ArrayList<String>();

			if (StringUtils.isEmpty(pirate.getChannelId())) {
				pirateService.updateChannelId(pirate.getPirateId(), event.getChannelId());
			}

			if (pirate.isTopFivePirate()) {
				String message = validateDailyDoubloonCount(pirate, "plunder");

				if (StringUtils.isNotEmpty(message)) {
					responses.add(message);
					return responses;
				}

				int doubloonsFound = getDoubloons();
				int doubloonsTotal = pirate.getDoubloons() + doubloonsFound;
				pirate.setDoubloons(doubloonsTotal);
				responses.add("While plundering a nearby tavern, you find *" + doubloonsFound + "* doubloons!\n"
						+ "The last day you can run this command is *"
						+ pirate.getDoubloonActivity().getTopFiveCommandEndDate() + "*.");

				if (doubloonsFound == 5) {
					responses.add("*" + pirate.getSlackUser().getFirstName() + " " + pirate.getSlackUser().getLastName()
							+ "* just found a whopping *5 doubloons* while on plundering a nearby tavern!!!");
				}

				pirateService.updateDoubloons(doubloonsTotal, pirate.getPirateId());

				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Date currentDate = new Date();
				String currentDateString = formatter.format(currentDate);

				doubloonActivityService.updatePlunder(currentDateString, pirate.getPirateId());
			} else {
				responses.add("You need to be in the Top Five Pirates at the end of the previous Pirate Adventure.");
			}

			return responses;
		}

		return null;
	}

	public List<String> getGrogCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());

			List<String> responses = new ArrayList<String>();

			if (StringUtils.isEmpty(pirate.getChannelId())) {
				pirateService.updateChannelId(pirate.getPirateId(), event.getChannelId());
			}

			if (pirate.isTopFivePirate()) {
				String message = validateDailyDoubloonCount(pirate, "grog");

				if (StringUtils.isNotEmpty(message)) {
					responses.add(message);
					return responses;
				}

				int doubloonsFound = getDoubloons();
				int doubloonsTotal = pirate.getDoubloons() + doubloonsFound;
				pirate.setDoubloons(doubloonsTotal);
				responses.add("While on drinking a mug of grog you find *" + doubloonsFound
						+ "* doubloons at the bottom of the cup!\n" + "The last day you can run this command is *"
						+ pirate.getDoubloonActivity().getTopFiveCommandEndDate() + "*.");

				if (doubloonsFound == 5) {
					responses.add("*" + pirate.getSlackUser().getFirstName() + " " + pirate.getSlackUser().getLastName()
							+ "* just found a whopping *5 doubloons* at the bottom of a grog mug!!!");
				}

				pirateService.updateDoubloons(doubloonsTotal, pirate.getPirateId());

				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Date currentDate = new Date();
				String currentDateString = formatter.format(currentDate);

				doubloonActivityService.updateGrog(currentDateString, pirate.getPirateId());
			} else {
				responses.add("You need to be in the Top Five Pirates at the end of the previous Pirate Adventure.");
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

			if (type.equals("plunder") || type.equals("grog")) {
				if (StringUtils.isEmpty(activity.getTopFiveCommandStartDate())
						|| StringUtils.isEmpty(activity.getTopFiveCommandEndDate())
						|| formatter.parse(currentDateString)
								.before(formatter.parse(activity.getTopFiveCommandStartDate()))
						|| formatter.parse(currentDateString)
								.after(formatter.parse(activity.getTopFiveCommandEndDate()))) {
					return "The doubloons commands are not active at this point.";
				} else if (type.equals("plunder") && StringUtils.isNotEmpty(activity.getLastPlunderDate())
						&& formatter.parse(currentDateString).equals(formatter.parse(activity.getLastPlunderDate()))) {
					return "You can only run the !plunder command once a day.";
				} else if (type.equals("grog") && StringUtils.isNotEmpty(activity.getLastGrogDate())
						&& formatter.parse(currentDateString).equals(formatter.parse(activity.getLastGrogDate()))) {
					return "You can only run the !grog command once a day.";
				}
			} else if (StringUtils.isEmpty(activity.getCommandStartDate())
					|| StringUtils.isEmpty(activity.getCommandEndDate())
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
		return new Random().nextInt(100) == 0 ? 5 : (new Random().nextInt(3) == 0 ? 2 : 1);
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

			StringBuilder output = new StringBuilder();

			output.append("*Here are the top 5 pirates!*\n");

			for (Pirate pirate : pirates) {
				output.append(pirate.getPirateName() + " (" + pirate.getSlackUser().getFirstName() + " "
						+ pirate.getSlackUser().getLastName() + "): *" + pirate.getPiratePoints() + " points* (Total: "
						+ pirate.getOverallPiratePoints() + " points) \n");
			}

			return output.toString();

		}

		return null;
	}

	public String getShipLeaderboardCommandResponse(Event event) {
		if (validateInput(event)) {
			List<PirateShip> pirateShips = pirateShipService.getTopShips();

			StringBuilder output = new StringBuilder();

			output.append("*Here is the points standings for the Ships!*\n");

			for (PirateShip pirateShip : pirateShips) {
				output.append(pirateShip.getShipFlag() + " " + pirateShip.getShipName() + ": *"
						+ pirateShip.getShipPoints() + " points*\n");
			}

			return output.toString();
		}

		return null;
	}

	public static String getPiratePointsHelpCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			return "You find all of the new commands here: https://drive.google.com/open?id=1uiRupRX_9zF_C8AUDpnUC3fsSWNFVs6A";
		}

		return null;
	}

	public String getEventsCommandResponse(Event event) {
		if (validateInput(event)) {
			List<PointEvent> pointsEvents = eventService.getEvents();

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("*Here is a list of the upcoming events that award points:*\n");

			for (PointEvent pointsEvent : pointsEvents) {
				SimpleDateFormat fromSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String displayDate = StringUtils.EMPTY;

				try {
					Date dbDate = fromSdf.parse(pointsEvent.getDate());
					SimpleDateFormat toSdf = new SimpleDateFormat("MM-dd-yyyy h:mm a");
					displayDate = toSdf.format(dbDate);
				} catch (ParseException e) {
					return null;
				}

				stringBuilder.append("*(").append(displayDate).append(" | ").append(pointsEvent.getLocation())
						.append(")*  _").append(pointsEvent.getEvent()).append("_ -- Rewards: *")
						.append(pointsEvent.getPoints()).append(" points* -- ").append("Contact: *")
						.append(pointsEvent.getContact()).append("* for more info.\n");
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
			pirateService.updatePoints((pirate.getPiratePoints() + pointsToAdd),
					(pirate.getOverallPiratePoints() + pointsToAdd), pirate.getUserId());

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

	public String getMyCrewInfoCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());
			List<Pirate> pirates = pirateService.getPiratesByShipId(pirate.getPirateShipId());
			
			Collections.sort(pirates);

			StringBuilder output = new StringBuilder();

			output.append("*Here is the manifest of your ship:*\n");

			for (Pirate pir : pirates) {
				output.append("*Pirate Name:* " + pir.getPirateName() + " --- *Real Name:* "
						+ pir.getSlackUser().getFirstName() + " " + pir.getSlackUser().getLastName() + " --- *Points:* "
						+ pir.getOverallPiratePoints() + "\n");
			}

			return output.toString();
		}

		return null;
	}

	public String getSortingEyepatchCommandResponse(Event event) {
		if (validateInput(event) && isJoshUser(event)) {
			String inputString = event.getText();
			String[] inputStringSplit = inputString.split("\\|");
			PirateShip assignedShip = pirateShipService.getShipToAddCrew();
			int shipCrew = assignedShip.getShipCrew();
			assignedShip.setShipCrew(shipCrew += 1);

			// insert into slack_user
			SlackUser slackUser = new SlackUser();
			slackUser.setFirstName(inputStringSplit[1]);
			slackUser.setLastName(inputStringSplit[2]);
			slackUser.setSlackId(inputStringSplit[3]);

			slackUserService.addNewSlackUser(slackUser);

			// insert into pirate
			pirateService.addNewPirate(assignedShip.getShipId(), inputStringSplit[4]);

			// insert into doubloon_act
			doubloonActivityService.addNewDoubloonAct();

			// update pirate ship
			pirateShipService.updateShipCrew(assignedShip);

			return "Aye, *" + slackUser.getFirstName() + " " + slackUser.getLastName()
					+ "* has been added to the crew of *" + assignedShip.getShipName() + "*. I dub ye *"
					+ inputStringSplit[4] + "*. Start a new Direct Message with me and type *!myPirateInfo* "
					+ "to see your info and *!myShipInfo* for your ship info."
					+ " *(NOTE: There could be a chance that I don't reply to you in the Direct Message. If that's "
					+ "the case let Josh Mayfield know. He will fix it. He has to be good for something.)*";
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

			if (StringUtils.isEmpty(pirate.getChannelId())) {
				pirateService.updateChannelId(pirate.getPirateId(), event.getChannelId());
			}

			if (pirate != null && pirateShip != null) {
				return "*Ship Name:* " + pirateShip.getShipName() + "\n" + "*Ship Captain:* " + captain + "\n"
						+ "*Ship Crew:* " + pirateShip.getShipCrew() + "\n" + "*Ship Points:* "
						+ pirateShip.getShipPoints() + "\n";
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

			if (StringUtils.isEmpty(pirate.getChannelId())) {
				pirateService.updateChannelId(pirate.getPirateId(), event.getChannelId());
			}

			if (pirate != null && pirateShip != null) {
				return "*Pirate Name:* " + pirate.getPirateName() + "\n*Pirate Ship:* " + pirateShip.getShipName()
						+ "\n*Current Pirate Points:* " + pirate.getPiratePoints() + "\n*Total Pirate Points:* "
						+ pirate.getOverallPiratePoints() + "\n*Doubloons:* " + pirate.getDoubloons() + "\n"
						+ "*Times you have walked the plank:* " + pirate.getPlankNum() + "\n";
			}

			return "I don't see you on my pirate registry. Overboard you go!";
		}

		return null;
	}

	public String getMyPointHistoryCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());
			List<PiratePointsHistory> piratePointsHistory = piratePointsHistoryService
					.getHistoryByPirateId(pirate.getPirateId());

			if (StringUtils.isEmpty(pirate.getChannelId())) {
				pirateService.updateChannelId(pirate.getPirateId(), event.getChannelId());
			}

			if (pirate != null && piratePointsHistory != null) {
				StringBuilder output = new StringBuilder();
				output.append("*Here are all the events that you took part in:*\n");

				for (PiratePointsHistory history : piratePointsHistory) {
					output.append("*Date:* " + history.getDateOfEvent() + " --- *Event:* " + history.getEvent()
							+ " --- *Points earned:* " + history.getPoints() + "\n");
				}

				return output.toString();
			} else {
				return "You don't have any points yet!!!";
			}
		}

		return null;
	}
}

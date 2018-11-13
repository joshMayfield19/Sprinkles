package com.ccc.chestersprinkles.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ccc.chestersprinkles.model.Achievement;
import com.ccc.chestersprinkles.model.Pirate;
import com.ccc.chestersprinkles.model.PirateShip;
import com.ccc.chestersprinkles.model.Rum;
import com.ccc.chestersprinkles.model.SlackUser;
import com.ccc.chestersprinkles.service.AchievementService;
import com.ccc.chestersprinkles.service.PirateService;
import com.ccc.chestersprinkles.service.PirateShipService;
import com.ccc.chestersprinkles.service.RumService;
import com.ccc.chestersprinkles.service.SlackUserService;

import me.ramswaroop.jbot.core.slack.models.Event;

@Component
public class PirateCommand extends Command {

	@Autowired
	private PirateService pirateService;

	@Autowired
	private SlackUserService slackUserService;

	@Autowired
	private AchievementService achievementService;

	@Autowired
	private RumService rumService;
	
	@Autowired
	private PirateShipService pirateShipService;

	// Trey Sparks = UBTE5F1J4
	// Reanna Reach = UBW5VUDK2
	// Scott Monnig = UC811U8H0
	// Ingrid Miller = UC0GUB9BR
	// Monty Hamilton = UC0D00SFP
	private List<String> newlyUnemployedSlackIds = Arrays.asList("UBTE5F1J4", "UBW5VUDK2", "UC811U8H0", "UC0GUB9BR",
			"UC0D00SFP");

	// Bethany Straughan = UBW657ERF
	// Hannah Chappelle = UBX52CBFX
	// Natascha Thomas = UBX6FTZ6H
	private List<String> electraSlideSlackIds = Arrays.asList("UBW657ERF", "UBX52CBFX", "UBX6FTZ6H");

	public String getWalkThePlankCommandResponse(Event event) {
		if (validateInput(event)) {
			List<SlackUser> allUsers = slackUserService.getSlackUsers();

			SlackUser randomSlackUser = allUsers.get(getRandomNumber(allUsers.size() - 1));

			Pirate pirate = pirateService.getPirateBySlackId(randomSlackUser.getSlackId());
			int currentPlankNum = pirate.getPlankNum();
			pirateService.updateWalkThePlank((currentPlankNum + 1), pirate.getPirateId());

			String achievementString = updateAcheivements(event, randomSlackUser);
			String output = getPlankWalkerStatement(
					randomSlackUser.getFirstName() + " " + randomSlackUser.getLastName());

			if (achievementString != null) {
				output = output + "\n" + achievementString;
			}

			return output;
		}

		return null;
	}

	private String updateAcheivements(Event event, SlackUser plankWalker) {
		Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());

		// check Newly Unemployed
		if (newlyUnemployedSlackIds.contains(plankWalker.getSlackId())) {
			return checkForNewlyUnemployed(plankWalker, pirate);
		} else if (electraSlideSlackIds.contains(plankWalker.getSlackId())) {
			return checkForElectraSlide(plankWalker, pirate);
		} else if (pirate.getSlackUser().getSlackId().equals(plankWalker.getSlackId())) {
			return checkForSelfPlank(pirate);
		}

		return null;
	}

	private String checkForSelfPlank(Pirate pirate) {
		Achievement achievement = achievementService.getAchievementByPirateId(pirate.getPirateId());

		if (achievement.isHasSelfPlank()) {
			return null;
		} else {
			achievementService.updateSelfPlank(true, pirate.getPirateId());
			return "*" + pirate.getSlackUser().getFirstName() + " " + pirate.getSlackUser().getLastName()
					+ "* has earned the *Self Planked* achievement.";
		}
	}

	private String checkForNewlyUnemployed(SlackUser plankWalker, Pirate pirate) {
		Achievement achievement = achievementService.getAchievementByPirateId(pirate.getPirateId());
		String currentNewlyUnemployed = achievement.getNewlyUnemployed();

		return checkAchievementString(plankWalker, pirate, currentNewlyUnemployed, 5);
	}

	private String checkForElectraSlide(SlackUser plankWalker, Pirate pirate) {
		Achievement achievement = achievementService.getAchievementByPirateId(pirate.getPirateId());
		String currentElectraSlide = achievement.getElectraSlide();

		return checkAchievementString(plankWalker, pirate, currentElectraSlide, 3);
	}

	private String checkAchievementString(SlackUser plankWalker, Pirate pirate, String achievementString,
			int maxValue) {
		if (StringUtils.isEmpty(achievementString)) {
			if (maxValue == 5) {
				achievementService.updateNewlyUnemployed(plankWalker.getLastName(), pirate.getPirateId());
			} else {
				achievementService.updateElectraSlide(plankWalker.getLastName(), pirate.getPirateId());
			}
		} else {
			String[] currentArray = achievementString.split(";");
			boolean alreadyWalked = false;
			int newCounter = currentArray.length;

			if (newCounter != maxValue) {
				for (String name : currentArray) {
					if (name.equals(plankWalker.getLastName())) {
						alreadyWalked = true;
						break;
					}
				}

				String newValue = StringUtils.EMPTY;
				String output = null;

				if (!alreadyWalked) {
					newValue = achievementString + ";" + plankWalker.getLastName();
					newCounter = newCounter + 1;
				}

				if (newCounter == maxValue) {
					newValue = "Complete";

					if (maxValue == 5) {
						output = "*" + pirate.getSlackUser().getFirstName() + " " + pirate.getSlackUser().getLastName()
								+ "* has earned the *Newly Unemployed* achievement.";
					} else {
						output = "*" + pirate.getSlackUser().getFirstName() + " " + pirate.getSlackUser().getLastName()
								+ "* has earned the *Electra Slide* achievement.";
					}
				}

				if (StringUtils.isNotEmpty(newValue)) {
					if (maxValue == 5) {
						achievementService.updateNewlyUnemployed(newValue, pirate.getPirateId());
					} else {
						achievementService.updateElectraSlide(newValue, pirate.getPirateId());
					}
				}

				return output;
			}
		}

		return null;
	}

	public String getMutinyCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());

			if (pirate.getMutiny() > 0) {
				String input = event.getText();
				String[] inputSplit = input.split(" ");

				String shipString = inputSplit[1];
				String shipName = StringUtils.EMPTY;
				List<Pirate> pirates = null;

				if (shipString.toLowerCase().equals("pride")) {
					pirates = pirateService.getPiratesByShipId(7);
					shipName = "The Pride of the Tide";
				} else if (shipString.toLowerCase().equals("scurvy")) {
					pirates = pirateService.getPiratesByShipId(2);
					shipName = "The Scurvy Sun";
				} else if (shipString.toLowerCase().equals("insanity")) {
					pirates = pirateService.getPiratesByShipId(4);
					shipName = "The Blue Insanity";
				} else if (shipString.toLowerCase().equals("dagger")) {
					pirates = pirateService.getPiratesByShipId(3);
					shipName = "The Cry of the Dagger";
				} else if (shipString.toLowerCase().equals("wolf")) {
					pirates = pirateService.getPiratesByShipId(5);
					shipName = "The Corrupted Wolf";
				} else {
					return "I don't recognize that ship.";
				}

				for (Pirate pir : pirates) {
					int currentPlankNum = pir.getPlankNum();
					pirateService.updateWalkThePlank((currentPlankNum + 1), pir.getPirateId());
				}

				pirateService.updateUseMutinyCommand(pirate.getPirateId());

				return "You have caused a mutiny on *" + shipName
						+ "*. Everyone on that ship has now walked the plank!";
			} else {
				return "You don't have any charges of !mutiny. You can purchase them in the shop.";
			}
		}

		return null;
	}

	public String getPlankSniperCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());

			if (pirate.getPlankSniper() > 0) {
				String input = event.getText();
				String[] inputSplit = input.split(" ");

				String walkerStringSlackId = inputSplit[1].substring(2, 11);
				Pirate pirateWalker = pirateService.getPirateBySlackId(walkerStringSlackId);

				if (pirateWalker == null) {
					return "I don't recognize that pirate.";
				}

				int currentPlankNum = pirateWalker.getPlankNum();
				pirateService.updateWalkThePlank((currentPlankNum + 1), pirateWalker.getPirateId());

				SlackUser walker = pirateWalker.getSlackUser();
				String walkerName = walker.getFirstName() + " " + walker.getLastName();

				pirateService.updateUsePlankSniperCommand(pirate.getPirateId());

				return getPlankWalkerStatement(walkerName);
			} else {
				return "You don't have any charges of !plankSniper. You can purchase them in the shop.";
			}
		}

		return null;
	}

	public String getRumCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());

			if (pirate.getRum() > 0) {
				Rum newRum = new Rum();
				newRum.setRumGiverId(pirate.getPirateId());
				newRum.setRumGiver(pirate.getSlackUser().getFirstName() + " " + pirate.getSlackUser().getLastName());
				
				String[] inputSplit = event.getText().split(" ");
				
				if (inputSplit[1].length() <= 10) {
					return "I don't recognize that pirate.";
				}
				
				Pirate rumGetter = pirateService.getPirateBySlackId(inputSplit[1].substring(2, 11));
				
				if (rumGetter == null) {
					return "I don't recognize that pirate.";
				}
				
				PirateShip rumGetterShip = pirateShipService.getShipById(rumGetter.getPirateShipId());
			
				newRum.setRumGetterId(rumGetter.getPirateId());
				newRum.setRumGetter(rumGetter.getSlackUser().getFirstName() + " " + rumGetter.getSlackUser().getLastName());
				
				StringBuilder rumReason = new StringBuilder();
				for (int i = 2; i < inputSplit.length; i++) {
					rumReason.append(inputSplit[i]).append(" "); 
				}
				
				newRum.setRumReason(rumReason.toString().trim());
				
				if (newRum.getRumGetterId() == newRum.getRumGiverId()) {
					return "You must be in cahoots with ole 'Ghost White' Carabis!";
				}
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

				LocalDate rumDate = LocalDate.now();
				String rumDateString = rumDate.format(formatter);
				
				newRum.setRumDate(rumDateString);
				
				rumService.addNewEvent(newRum);
				
				pirateService.updateUseRumCommand(pirate.getPirateId());
				pirateService.updateGetRumCommand(rumGetter.getPirateId());
				
				pirateShipService.updatePoints(rumGetterShip.getOverallShipPoints() + 5, rumGetter.getPirateShipId());
				
				return "*" + pirate.getSlackUser().getFirstName() + "* has given *" + rumGetter.getSlackUser().getFirstName() + "* some rum! Keep up the great work!";
			} else {
				return "You don't have any more rum to give out at this moment.";
			}
		}

		return null;
	}
	
	private String getPlankWalkerStatement(String plankWalker) {
		int rando = getRandomNumber(5);

		if (rando == 0) {
			return "I hope *" + plankWalker + "* knows how to swim!";
		} else if (rando == 1) {
			return "Oops! Seems *" + plankWalker + "* 'fell' off the ship.";
		} else if (rando == 2) {
			return "It's time for *" + plankWalker + "* to take a long walk down a short diving board.";
		} else if (rando == 3) {
			return "*" + plankWalker + "*, let me show you where the pool is...";
		} else if (rando == 4) {
			return "*" + plankWalker + "*, think of this as a very wet vacation from your ship. :stuck_out_tongue:";
		} else {
			return "Ahoy! It's time for *" + plankWalker + "* to walk the plank! Get moving!";
		}
	}

	public String getTopPlankWalkersCommandResponse(Event event) {
		if (validateInput(event)) {
			List<Pirate> pirates = pirateService.getTopPlankWalkers();

			StringBuilder output = new StringBuilder();

			output.append("*Here are the pirates who have walked the plank the most*\n");
			for (Pirate pirate : pirates) {
				output.append("Walked " + pirate.getPlankNum() + " times --- " + pirate.getPirateName() + " ("
						+ pirate.getSlackUser().getFirstName() + " " + pirate.getSlackUser().getLastName() + ")\n");
			}

			return output.toString();
		}

		return null;
	}

	public static String getWhatDoYouDoDrunkerCommandResponse(Event event) {
		if (validateInput(event)) {
			int rando = getRandomNumber(9);

			if (rando == 1) {
				return "Shave his belly with a rusty razor.\n";
			} else if (rando == 2) {
				return "Put him in a longboat till he's sober.\n";
			} else if (rando == 3) {
				return "Put him in the scuppers with a hose-pipe on him.\n";
			} else if (rando == 4) {
				return "Beat him with a cat 'til his back is bleedin'.\n";
			} else if (rando == 5) {
				return "Put him in the bilge and make him drink it.\n";
			} else if (rando == 6) {
				return "Truss him up with a runnin' bowline.\n";
			} else if (rando == 7) {
				return "Give 'im a dose of salt and water.\n";
			} else if (rando == 8) {
				return "Stick on 'is back a mustard plaster.\n";
			} else if (rando == 0) {
				return "Send him up the crows nest till he falls down.\n";
			} else {
				return "Soak 'im in whale oil till he sprouts a flipper.\n";
			}
		}

		return null;
	}

	public static String getShantyCommandResponse(Event event) {
		if (validateInput(event)) {
			int rando = getRandomNumber(3);

			if (rando == 1) {
				return "https://www.youtube.com/watch?v=0jGMgWUJcKc\n";
			} else if (rando == 2) {
				return "https://www.youtube.com/watch?v=d1DGNh9fOmw\n";
			} else if (rando == 0) {
				return "https://www.youtube.com/watch?v=20n3N1uhztc\n";
			} else {
				return "https://www.youtube.com/watch?v=pSnZ-J3kMmI\n";
			}
		}

		return null;
	}

	public static String getFuttocksCommandResponse(Event event) {
		return validateInput(event) ? "At their furthest reach, my dear boy.\n" : null;
	}

	public static String getYarrCommandResponse(Event event) {
		return validateInput(event) ? "Yarr! Ye be right!\n" : null;
	}

	public static String getKeelhauledCommandResponse(Event event) {
		return validateInput(event) ? "There be no keelhauling on our ship.\n" : null;
	}

	public static String getMaroonedCommandResponse(Event event) {
		return validateInput(event) ? "Don't ye fret. I would never maroon ye anywheres.\n" : null;
	}

	public static String getBlimeyCommandResponse(Event event) {
		return validateInput(event) ? "Blimey! What ye be so excited fer?\n" : null;
	}

	public static String getAyeCommandResponse(Event event) {
		return validateInput(event) ? "Aye! Ye be right!\n" : null;
	}

	public static String getAhoyCommandResponse(Event event) {
		return validateInput(event) ? "And a hearty Ahoy to you!\n" : null;
	}

	public static String getMateyCommandResponse(Event event) {
		return validateInput(event) ? "Are ye me matey?\n" : null;
	}

	public static String getArrCommandResponse(Event event) {
		return validateInput(event) ? "Arr! Ye be right!\n" : null;
	}

	public static String getAvastCommandResponse(Event event) {
		return validateInput(event) ? "Avast! Look here matey.\n" : null;
	}
}

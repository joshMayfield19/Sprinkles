package com.ccc.chestersprinkles.utility;

import java.util.List;

import com.ccc.chestersprinkles.model.SlackUser;
import com.ccc.chestersprinkles.service.SlackUserService;

import me.ramswaroop.jbot.core.slack.models.Event;

public class PirateCommand extends Command {
	private static String ARR_COMMAND = "!arr";
	private static String AVAST_COMMAND = "!avast";
	private static String MATEY_COMMAND = "!matey";
	private static String AHOY_COMMAND = "!ahoy";
	private static String AYE_COMMAND = "!aye";
	private static String BLIMEY_COMMAND = "!blimey";
	private static String MAROONED_COMMAND = "!marooned";
	private static String KEELHAULED_COMMAND = "!keelhauled";
	private static String YARR_COMMAND = "!yarr";
	private static String SHANTY_COMMAND = "!shanty";
	private static String FUTTOCKS_COMMAND = "!howareyourfuttocksoldman";
	private static String WALKTHEPLANK_COMMAND = "!walktheplank";
	
	public static String getCommandResponse(Event event, SlackUserService slackUserService) {
		if (commandStartsWith(event, ARR_COMMAND)) {
			return getArrCommandResponse();
		}
		else if (commandStartsWith(event, AVAST_COMMAND)) {
			return getAvastCommandResponse();
		}
		else if (commandStartsWith(event, MATEY_COMMAND)) {
			return getMateyCommandResponse();
		}
		else if (commandStartsWith(event, AHOY_COMMAND)) {
			return getAhoyCommandResponse();
		}
		else if (commandStartsWith(event, AYE_COMMAND)) {
			return getAyeCommandResponse();
		}
		else if (commandStartsWith(event, BLIMEY_COMMAND)) {
			return getBlimeyCommandResponse();
		}
		else if (commandStartsWith(event, MAROONED_COMMAND)) {
			return getMaroonedCommandResponse();
		}
		else if (commandStartsWith(event, KEELHAULED_COMMAND)) {
			return getKeelhauledCommandResponse();
		}
		else if (commandStartsWith(event, YARR_COMMAND)) {
			return getYarrCommandResponse();
		}
		else if (commandStartsWith(event, SHANTY_COMMAND)) {
			return getShantyCommandResponse();
		}
		else if (commandStartsWith(event, FUTTOCKS_COMMAND)) {
			return getFuttocksCommandResponse();
		}
		else if (commandStartsWith(event, WALKTHEPLANK_COMMAND)) {
			return getWalkThePlankCommandResponse(slackUserService);
		}
		
		return null;
	}
	
	private static String getWalkThePlankCommandResponse(SlackUserService slackUserService) {
		List<SlackUser> allUsers = slackUserService.getSlackUsers();
		
		SlackUser randomSlackUser = allUsers.get(getRandomNumber(allUsers.size()));
		
		return "Ahoy! It's time for " + randomSlackUser.getFirstName()  + " " + randomSlackUser.getLastName() + " to walk the plank! Get moving!";
	}
	
	private static String getShantyCommandResponse() {
		int rando = getRandomNumber(4);

		if (rando == 1) {
			return "https://www.youtube.com/watch?v=0jGMgWUJcKc\n";
		} else if (rando == 2) {
			return "https://www.youtube.com/watch?v=d1DGNh9fOmw\n";
		} else if (rando == 3) {
			return "https://www.youtube.com/watch?v=20n3N1uhztc\n";
		} else {
			return "https://www.youtube.com/watch?v=pSnZ-J3kMmI\n";
		}
	}
	
	private static String getFuttocksCommandResponse() {
		return "At their furthest reach, my dear boy.\n";
	}

	private static String getYarrCommandResponse() {
		return "Yarr! Ye be right!\n";
	}

	private static String getKeelhauledCommandResponse() {
		return "There be no keelhauling on our ship.\n";
	}

	private static String getMaroonedCommandResponse() {
		return "Don't ye fret. I would never maroon ye anywheres.\n";
	}

	private static String getBlimeyCommandResponse() {
		return "Blimey! What ye be so excited fer?\n";
	}

	private static String getAyeCommandResponse() {
		return "Aye! Ye be right!\n";
	}

	private static String getAhoyCommandResponse() {
		return "And a hearty Ahoy to you!\n";
	}

	private static String getMateyCommandResponse() {
		return "Are ye me matey?\n";
	}

	private static String getArrCommandResponse() {
		return "Arr! Ye be right!\n";
	}
	
	private static String getAvastCommandResponse() {
		return "Avast! Look here matey.\n";
	}
}

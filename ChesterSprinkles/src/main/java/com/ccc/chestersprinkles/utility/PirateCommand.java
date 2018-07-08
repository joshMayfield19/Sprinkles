package com.ccc.chestersprinkles.utility;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ccc.chestersprinkles.model.Pirate;
import com.ccc.chestersprinkles.model.SlackUser;
import com.ccc.chestersprinkles.service.PirateService;
import com.ccc.chestersprinkles.service.SlackUserService;

import me.ramswaroop.jbot.core.slack.models.Event;

@Component
public class PirateCommand extends Command {
	
	@Autowired
	private PirateService pirateService;
	
	@Autowired
	private SlackUserService slackUserService;
	
	public String getWalkThePlankCommandResponse(Event event) {
		if (validateInput(event)) {
			List<SlackUser> allUsers = slackUserService.getSlackUsers();
			
			SlackUser randomSlackUser = allUsers.get(getRandomNumber(allUsers.size()-1));
			
			Pirate pirate = pirateService.getPirateBySlackId(randomSlackUser.getSlackId());
			int currentPlankNum = pirate.getPlankNum();
			pirateService.updateWalkThePlank((currentPlankNum + 1), pirate.getPirateId());
			
			return "Ahoy! It's time for " + randomSlackUser.getFirstName()  + " " + randomSlackUser.getLastName() + " to walk the plank! Get moving!";
		}
		
		return null;
	}
	
	public static String getWhatDoYouDoDrunkerCommandResponse(Event event) {
		if (validateInput(event)) {
			int rando = getRandomNumber(10);
			
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
			} else if (rando == 9) {
				return "Send him up the crows nest till he falls down.\n";
			} else {
				return "Soak 'im in whale oil till he sprouts a flipper.\n";
			} 
		}
		
		return null;
	}
	
	public static String getShantyCommandResponse(Event event) {
		if (validateInput(event)) {
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

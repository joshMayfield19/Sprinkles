package com.ccc.chestersprinkles.utility;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ccc.chestersprinkles.model.ChesterSprinklesData;

import me.ramswaroop.jbot.core.slack.models.Event;

@Component
public class MiscCommand extends Command {
	public static String getHelpCommandResponse(Event event) {
		if (validateInput(event)) {
			return "Here is the list of me orders: https://drive.google.com/open?id=1JTev4MYesSjiRIHMbxPkXEG_DOrjGmUb";
		}

		return null;
	}

	public static String getStaplerCommandResponse(Event event) {
		if (validateInput(event)) {

			int rando = getRandomNumber(15);

			if (rando == 0) {
				return "I believe you have my stapler.\n";
			} else if (rando == 1) {
				return "Yeah, hi.\n";
			} else if (rando == 2) {
				return "Corporate accounts payable, Nina speaking. Just a moment...\n";
			} else if (rando == 3) {
				return "For my money, I don’t know if it gets any better than when he sings When a Man Loves a Woman.\n";
			} else if (rando == 4) {
				return "You know I never really liked paying bills, I don’t think I’m going to do that either.\n";
			} else if (rando == 5) {
				return "Well, I wouldn’t say I’ve been MISSING it, Bob.\n";
			} else if (rando == 6) {
				return "You know, squirrely looking guy, mumbles a lot.\n";
			} else if (rando == 7) {
				return "Well just a second there, professor. We uh, we fixed the glitch. \n";
			} else if (rando == 8) {
				return "It’s not that I’m lazy, it’s that I just don’t care.\n";
			} else if (rando == 9) {
				return "PC load letter!\n";
			} else if (rando == 10) {
				return "Do you ever watch Kung Fu?\n";
			} else if (rando == 11) {
				return "Sounds like someone has a case of the Mondays. :slightly_frowning_face:\n";
			} else if (rando == 12) {
				return "No, that's the jar. I'm talking about the tray, the pennies for everybody.\n";
			} else if (rando == 13) {
				return "People can get a cheeseburger anywhere, okay?\n";
			} else if (rando == 14) {
				return "Actually man, I make more money selling magazine subscriptions, than I ever did at Intertrode!\n";
			}       
			else {
				return "Going to need you to come in on Saturday.\n";
			}
		}
		
		return null;
	}

	public String getWeapojCommandResponse(Event event) {
		if (validateInput(event)) {
			int rando = getRandomNumber(7);

			if (rando == 0) {
				return "Josh is such a doofus. Am I right?\n";
			} else if (rando == 1) {
				return "At least I know how to spell weapon.\n";
			} else if (rando == 2) {
				return "Josh programmed me? There must bee so many typos in my codde.\n";
			} else if (rando == 3) {
				return "Josh fail English? That's unpossible!\n";
			} else if (rando == 4) {
				return "Josh ate too much plastic candy.\n";
			} else if (rando == 5) {
				return "This is Josh's sandbox, he's not allowed to go in the deep end.\n";
			} else if (rando == 6) {
				return "Josh is learnding!\n";
			} else {
				return "Let's get our pitchforks and show him what for!!!!!!!.\n";
			}
		}

		return null;
	}

	public static String getProposedCommandsCommandResponse(Event event) {
		if (validateInput(event)) {
			ChesterSprinklesData chesterSprinkles = ChesterSprinklesData.getSprinklesData();
			StringBuilder commandsOutput = new StringBuilder();
			commandsOutput.append("The current proposed commands are:\n");
			List<String> commands = chesterSprinkles.getCommands();
			for (int i = 0; i < commands.size(); i++) {
				commandsOutput.append(commands.get(i));

				if (i != commands.size() - 1) {
					commandsOutput.append("\n");
				}
			}

			return commandsOutput.toString();
		}

		return null;
	}

	public static String getBlaymonCommandResponse(Event event) {
		if (validateInput(event)) {
			int rando = getRandomNumber(3);

			if (rando == 1) {
				return "Haymon did it. Not me!\n";
			} else if (rando == 2) {
				return "I saw Haymon walking away with an incriminating smile.\n";
			} else if (rando == 0) {
				return "We all know who is to blame.\n";
			} else {
				return "I know right? Come on Haymon, get it right.\n";
			}
		}

		return null;
	}

	public static String getHackThePlanetCommandResponse(Event event) {
		if (validateInput(event)) {
			int rando = getRandomNumber(9);

			if (rando == 1) {
				return "Snoop onto them, as they snoop onto us.\n";
			} else if (rando == 2) {
				return "A worm and a virus? The plot thickens.\n";
			} else if (rando == 3) {
				return "Mess with the best. Die like the rest.\n";
			} else if (rando == 4) {
				return "Whoa. This isn't wood shop class?\n";
			} else if (rando == 5) {
				return "We are Samurai. The keyboard cowboy.\n";
			} else if (rando == 6) {
				return "Of all the things I've lost, I miss my mind the most.\n";
			} else if (rando == 7) {
				return "Hackers of the world, unite!\n";
			} else if (rando == 8) {
				return "Rabbit, Flu-shot, someone talk to me!\n";
			} else if (rando == 0) {
				return "Super hero-like, even...\n";
			} else {
				return "Hack the Planet!\n";
			}
		}

		return null;
	}
}

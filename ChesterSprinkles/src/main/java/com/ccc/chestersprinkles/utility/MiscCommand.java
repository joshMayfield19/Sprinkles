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
	
	public String getWeapojCommandResponse(Event event) {
		if (validateInput(event)) {
			int rando = getRandomNumber(4);
			
			if (rando == 1) {
				return "Josh is such a doofus. Am I right?\n";
			} else if (rando == 2) {
				return "At least I know how to spell weapon.\n";
			} else if (rando == 3) {
				return "Josh programmed me? There must bee so many typos in my codde.\n";
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
			int rando = getRandomNumber(4);
	
			if (rando == 1) {
				return "Haymon did it. Not me!\n";
			} else if (rando == 2) {
				return "I saw Haymon walking away with an incriminating smile.\n";
			} else if (rando == 3) {
				return "We all know who is to blame.\n";
			} else {
				return "I know right? Come on Haymon, get it right.\n";
			} 
		}
		
		return null;
	}
	
	public static String getHackThePlanetCommandResponse(Event event) {
		if (validateInput(event)) {
			int rando = getRandomNumber(10);
	
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
			} else if (rando == 9) {
				return "Super hero-like, even...\n";
			} else {
				return "Hack the Planet!\n";
			} 
		}
		
		return null;
	}
}

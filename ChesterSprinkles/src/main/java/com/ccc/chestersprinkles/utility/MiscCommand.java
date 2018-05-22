package com.ccc.chestersprinkles.utility;

import java.util.List;

import com.ccc.chestersprinkles.model.ChesterSprinklesData;

import me.ramswaroop.jbot.core.slack.models.Event;

public class MiscCommand extends Command {
	public static String getHelpCommandResponse(Event event) {
		if (validateInput(event)) {
			return "Here is the list of me orders: https://drive.google.com/open?id=1JTev4MYesSjiRIHMbxPkXEG_DOrjGmUb";
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
}

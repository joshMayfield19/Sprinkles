package com.ccc.chestersprinkles.utility;

import java.util.List;

import com.ccc.chestersprinkles.model.ChesterSprinklesData;

import me.ramswaroop.jbot.core.slack.models.Event;

public class RaffleCommand extends Command {
	private static String RAFFLE_COMMAND = "!raffle";
	
	public static String getCommandResponse(Event event) {
		if (commandStartsWith(event, RAFFLE_COMMAND)) {
			return getRaffleCommandResponse();
		}
		
		return null;
	}

	private static String getRaffleCommandResponse() {
		ChesterSprinklesData chesterSprinkles = ChesterSprinklesData.getSprinklesData();
		StringBuilder raffleWinnerOutput = new StringBuilder();
		raffleWinnerOutput.append("Avast! The winners of the last raffle are: ");
		List<String> raffleWinners = chesterSprinkles.getRaffleWinners();
		for (int i = 0; i < raffleWinners.size(); i++) {
			raffleWinnerOutput.append(raffleWinners.get(i));

			if (i == raffleWinners.size() - 1) {
				raffleWinnerOutput.append(".");
			} else {
				raffleWinnerOutput.append(", ");
			}
		}

		return "The next raffle will be held on " + chesterSprinkles.getRaffleDateTime() + "! Be ready with yer tickets!\n" + raffleWinnerOutput.toString();
	}
}

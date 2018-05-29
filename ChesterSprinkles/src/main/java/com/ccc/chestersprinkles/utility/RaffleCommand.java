package com.ccc.chestersprinkles.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ccc.chestersprinkles.model.ChesterSprinklesData;

import me.ramswaroop.jbot.core.slack.models.Event;

public class RaffleCommand extends Command {
	public static String getRaffleCommandResponse(Event event) {
		if (validateInput(event)) {
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
		
		return null;
	}
	
	public static String getStartRaffleCommandResponse(Event event) {
		if (validateInput(event)) {
			ChesterSprinklesData chesterSprinkles = ChesterSprinklesData.getSprinklesData();
			int startingNumber = Integer.valueOf(chesterSprinkles.getRaffleStartingTicket());
			int endingNumber = Integer.valueOf(chesterSprinkles.getRaffleEndingTicket());
			int randomNum = getRandomNumber(endingNumber - startingNumber);
			int firstWinner = startingNumber + randomNum;
			
			return "The raffle has begun! The first ticket drawn is ticket number: " + firstWinner + ". Come to Josh Mayfield's"
					+ " desk to claim your prize. Bring your ticket(s) with you as well.";
		}
		
		return null;
	}
	
	public static String getNextTicketCommandResponse(Event event) {
		if (validateInput(event)) {
			String[] inputArray = event.getText().split(" ");
			ChesterSprinklesData chesterSprinkles = ChesterSprinklesData.getSprinklesData();
			int startingNumber = Integer.valueOf(chesterSprinkles.getRaffleStartingTicket());
			int endingNumber = Integer.valueOf(chesterSprinkles.getRaffleEndingTicket());
			int randomNum = getRandomNumber(endingNumber - startingNumber);
			int potentialWinner = startingNumber + randomNum;  
			
			List<String> excludedTicketsStrings = Arrays.asList(chesterSprinkles.getRaffleExcludedTickets().split(";"));
			List<Integer> excludedTickets = new ArrayList<Integer>();
			
			for (String ticket : excludedTicketsStrings) {
				excludedTickets.add(Integer.valueOf(ticket));
			}
			
			while(excludedTickets.contains(potentialWinner)) {
				potentialWinner = (startingNumber + getRandomNumber(endingNumber - startingNumber));  
			}
			
			if (inputArray[1].equals("2")) {
				return "The first prize has been claimed! The second ticket drawn is ticket number: " + potentialWinner + "."
						+ " Come to Josh Mayfield's desk to claim your prize. Bring your ticket(s) with you as well.";
			}
			else if (inputArray[1].equals("3")){
				return "The second prize has been claimed! The last ticket drawn is ticket number: " + potentialWinner + "."
						+ " Come to Josh Mayfield's desk to claim your prize. Bring your ticket(s) with you as well.";
			}
			
		}
		
		return null;
	}
	
	public static String getStartCountdownCommandResponse(Event event) {
		if (validateInput(event)) {
			return "The countdown clock has started. I will redraw a new ticket in 5 minutes if the previous one has not been claimed by then.";
		}
		
		return null;
	}
	
	public static String getEndRaffleCommandResponse(Event event) {
		if (validateInput(event)) {
			return "All of the prizes have been claimed. Thanks for playing and see you at the next one!";
		}
		
		return null;
	}
	
	public static String getRedrawTicketCommandResponse(Event event) {
		if (validateInput(event)) {
			ChesterSprinklesData chesterSprinkles = ChesterSprinklesData.getSprinklesData();
			int startingNumber = Integer.valueOf(chesterSprinkles.getRaffleStartingTicket());
			int endingNumber = Integer.valueOf(chesterSprinkles.getRaffleEndingTicket());
			int randomNum = getRandomNumber(endingNumber - startingNumber);
			int potentialWinner = startingNumber + randomNum;  
			
			List<String> excludedTicketsStrings = Arrays.asList(chesterSprinkles.getRaffleExcludedTickets().split(";"));
			List<Integer> excludedTickets = new ArrayList<Integer>();
			
			for (String ticket : excludedTicketsStrings) {
				excludedTickets.add(Integer.valueOf(ticket));
			}
			
			while(excludedTickets.contains(potentialWinner)) {
				potentialWinner = (startingNumber + getRandomNumber(endingNumber - startingNumber));  
			}
			
			return "The redrawn ticket number is: " + potentialWinner + "."
			+ " Come to Josh Mayfield's desk to claim your prize. Bring your ticket(s) with you as well.";	
		}
		
		return null;
	}
	
	
}

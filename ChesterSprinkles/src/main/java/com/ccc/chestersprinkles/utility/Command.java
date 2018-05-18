package com.ccc.chestersprinkles.utility;

import java.util.Random;

import org.springframework.util.StringUtils;

import me.ramswaroop.jbot.core.slack.models.Event;

public class Command {
	private static boolean validateInput(Event event) {
		return (event.getUserId() != null && !StringUtils.isEmpty(event.getText())); 
	}
	
	protected static boolean commandStartsWith(Event event, String command) {
		return (validateInput(event) && event.getText().toLowerCase().startsWith(command));
	}
	
	protected static int getRandomNumber(int max) {
		Random rand = new Random();
		return rand.nextInt(max) + 1;
 	}
}

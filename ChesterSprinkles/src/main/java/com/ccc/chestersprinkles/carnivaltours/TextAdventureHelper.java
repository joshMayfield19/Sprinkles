package com.ccc.chestersprinkles.carnivaltours;

import java.util.List;
import java.util.Random;

public class TextAdventureHelper {
	public static boolean commandContainsItemsIn(String command, List<String> collection) {
		for (String str : collection) {
			if (command.toLowerCase().contains(str)) {
				return true;
			}
		}

		return false;
	}
	
	public static boolean commandContainsMoreThanOneItemIn(String command, List<String> collection) {
		int counter = 0;
		
		for (String str : collection) {
			if (command.toLowerCase().contains(str)) {
				counter++;
			}
		}
		
		return counter > 1;
	}
	
	public static int getRandomNumber(int maxValue) {
		Random rand = new Random();
		return rand.nextInt(maxValue) + 1;
	}
	
	public static boolean isMoveCommand(String command) {
		return commandContainsItemsIn(command, TextAdventureConstants.CHANGE_LOCATION_TEXT);
	}

	public static boolean isLookCommand(String command) {
		return commandContainsItemsIn(command, TextAdventureConstants.LOOK_TXT);
	}

	public static boolean isInteractCommand(String command) {
		return commandContainsItemsIn(command, TextAdventureConstants.USE_TXT);
	}

	public static boolean isQuestionCommand(String command) {
		return commandContainsItemsIn(command, TextAdventureConstants.QUESTION_TXT);
	}
	
	public static boolean isHelpCommand(String command) {
		return commandContainsItemsIn(command, TextAdventureConstants.HELP_TXT);
	}
}

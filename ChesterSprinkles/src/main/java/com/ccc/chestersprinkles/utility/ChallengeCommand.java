package com.ccc.chestersprinkles.utility;

import java.util.List;

import org.springframework.util.StringUtils;

import com.ccc.chestersprinkles.model.ChesterSprinklesData;

import me.ramswaroop.jbot.core.slack.models.Event;

public class ChallengeCommand extends Command {
	private static String CURRENT_CHAL_COMMAND = "!currentchallenge";
	private static String ALL_CHAL_COMMAND = "!allchallenges";
	private static String CHAL_IDEAS_COMMAND = "!challengeideas";
	
	public static String getCommandResponse(Event event) {
		if (commandStartsWith(event, CURRENT_CHAL_COMMAND)) {
			return getCurrentChallengeCommandResponse();
		}
		else if (commandStartsWith(event, ALL_CHAL_COMMAND)) {
			return getAllChallengesCommandResponse();
		}
		else if (commandStartsWith(event, CHAL_IDEAS_COMMAND)) {
			return getChallengeIdeasCommandResponse();
		}
		
		return null;
	}

	private static String getChallengeIdeasCommandResponse() {
		ChesterSprinklesData chesterSprinkles = ChesterSprinklesData.getSprinklesData();
		StringBuilder ideasOutput = new StringBuilder();
		ideasOutput.append("The current challenge ideas are:\n");
		List<String> ideas = chesterSprinkles.getIdeas();
		for (int i = 0; i < ideas.size(); i++) {
			ideasOutput.append(ideas.get(i));

			if (i != ideas.size() - 1) {
				ideasOutput.append("\n");
			}
		}

		return ideasOutput.toString();
	}

	private static String getAllChallengesCommandResponse() {
		return "Ye can find the information on all of the previous "
				+ "challenges here: https://drive.google.com/drive/u/0/folders/0B_ALbuAWHljrLWxVYTRaXzlQeFU";
	}

	private static String getCurrentChallengeCommandResponse() {
		ChesterSprinklesData chesterSprinkles = ChesterSprinklesData.getSprinklesData();
		
		if (StringUtils.isEmpty(chesterSprinkles.getCurrentChallenge())) {
			return "There is no challenge currently going on.";
		}
		else {
			return "We are currenly working on a problem at the " + chesterSprinkles.getCurrentChallenge()
							+ "\nWe will be presenting our solutions on " + chesterSprinkles.getCurrentChallengeDate()
							+ "\nYe can find the information on this challenge here: "
							+ chesterSprinkles.getCurrentChallengeLink();
		}
	}
}

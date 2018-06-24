package com.ccc.chestersprinkles.utility;

import java.util.List;

import org.springframework.util.StringUtils;

import com.ccc.chestersprinkles.model.Challenge;
import com.ccc.chestersprinkles.model.ChesterSprinklesData;
import com.ccc.chestersprinkles.service.ChallengeService;

import me.ramswaroop.jbot.core.slack.models.Event;

public class ChallengeCommand extends Command {
	public static String getChallengeIdeasCommandResponse(Event event) {
		if (validateInput(event)) {
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
		
		return null;
	}

	public static String getAllChallengesCommandResponse(Event event) {
		if (validateInput(event)) {
			return "Ye can find the information on all of the previous "
					+ "challenges here: https://drive.google.com/drive/u/0/folders/0B_ALbuAWHljrLWxVYTRaXzlQeFU";
		}
		
		return null;
	}

	public static String getCurrentChallengeCommandResponse(Event event, ChallengeService challengeService) {
		if (validateInput(event)) {
			//ChesterSprinklesData chesterSprinkles = ChesterSprinklesData.getSprinklesData();
			Challenge challenge = challengeService.getCurrentChallenge();
			
			if (challenge == null) {
				return "There is no challenge currently going on.";
			}
			else {
				return "We are currenly working on a problem titled *" + challenge.getChallengeName()
								+ "*\nWe will be presenting our solutions on *" + challenge.getChallengeDate()
								+ "*\nYe can find the information on this challenge here: "
								+ challenge.getChallengeLink();
			}
		}
		
		return null;
	}
}

package com.ccc.chestersprinkles.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.ccc.chestersprinkles.model.AllTimePresenter;
import com.ccc.chestersprinkles.model.ChesterSprinklesData;
import com.ccc.chestersprinkles.model.Presentation;
import com.ccc.chestersprinkles.model.SlackUser;
import com.ccc.chestersprinkles.service.PresentationService;
import com.ccc.chestersprinkles.service.SlackUserService;

import me.ramswaroop.jbot.core.slack.models.Event;

public class PresenterCommand extends Command {
	@Autowired
	private static SlackUserService slackUserService;
	
	@Autowired
	private PresentationService presentationService;
	
	public static String getPresentationTotalCommandResponse(Event event) {
		if (validateInput(event)) {
			ChesterSprinklesData chesterSprinkles = ChesterSprinklesData.getSprinklesData();
			StringBuilder presentersOutput = new StringBuilder();
			List<AllTimePresenter> presenters = chesterSprinkles.getAllTimePresenters();
			Map<Integer, String> presenterMap = new HashMap<Integer, String>();
			Collections.sort(presenters);
			int total = 0;
	
			for (AllTimePresenter presenter : presenters) {
				total += presenter.getTimesPresented();
				int presenterKey = presenter.getTimesPresented();
				if (presenterMap.containsKey(presenterKey)) {
					String presenterString = presenterMap.get(presenterKey);
					StringBuilder pBuilder = new StringBuilder();
					pBuilder.append(presenterString).append(", ").append(presenter.getName());
					presenterMap.put(presenterKey, pBuilder.toString());
				} else {
					presenterMap.put(presenterKey, presenter.getName());
				}
			}
	
			List<Integer> sortedKeys = new ArrayList<Integer>(presenterMap.keySet());
			Collections.reverse(sortedKeys);
	
			presentersOutput.append("There have been a total of ").append(total)
					.append(" presentations since the Carnival has arrived.\n");
	
			for (int key : sortedKeys) {
				presentersOutput.append(key).append(" presentation(s) : ").append(presenterMap.get(key)).append("\n");
			}
	
			return presentersOutput.toString();
		}
		
		return null;
	}

	public static String getPresentersCommandResponse(Event event) {
		if (validateInput(event)) {
			//List<Presentation> currentPresenters = presentationService.getCurrentPresentation();
			ChesterSprinklesData chesterSprinkles = ChesterSprinklesData.getSprinklesData();
			
			if (StringUtils.isEmpty(chesterSprinkles.getCurrentChallenge())) {
				return "There is no challenge currently going on.";
			}
			
			List<String> presenters = chesterSprinkles.getPresenters();
			int counter = 1;
			//boolean standbyPresenterTextAdded = false;
			
			StringBuilder presentersOutput = new StringBuilder();
			presentersOutput.append("The current presenters for " + chesterSprinkles.getCurrentChallenge() + " on "
					+ chesterSprinkles.getCurrentChallengeDate() + " are:\n");
			for (int i = 0; i < presenters.size(); i++) {
				/*if (counter > 6 && !standbyPresenterTextAdded) {
					presentersOutput.append("*Standby:*\n");
					standbyPresenterTextAdded = true;
				}*/
				
				presentersOutput.append(counter++).append(". ").append(presenters.get(i)).append("\n");
				
			}
	
			return presentersOutput.toString();
		}
		
		return null;
	}

	public static String getRemovePresenterCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			SlackUser currentUser = slackUserService.getSlackUser(event.getUserId());
			ChesterSprinklesData chesterSprinkles = ChesterSprinklesData.getSprinklesData();
			List<String> presenters = chesterSprinkles.getPresenters();
			if (presenters.size() > 0) {
				for (int i = 0; i < presenters.size(); i++) {
					if ((presenters.get(i)).equals(currentUser.getFirstName() + " " + currentUser.getLastName())) {
						presenters.remove(i);
						chesterSprinkles.setPresenters(presenters);
						ChesterSprinklesData.writeSprinklesData(chesterSprinkles);
						return "Alrighty, " + currentUser.getFirstName() + ", ye have been removed from the presenter list.";
					}
				}
	
				return "Sorry, " + currentUser.getFirstName() + ", ye currently aren't signed up to present at the moment.";
			} else {
				return "Nice try! There is no one signed up to present yet. I got my eye on ye " + currentUser.getFirstName() + ".";
			}
		}
		
		return null;
	}

	public static String getAddPresenterCommandResponse(Event event, SlackUserService slackUserService) {
		if (validateInput(event)) {
			SlackUser currentUser = slackUserService.getSlackUser(event.getUserId());
			ChesterSprinklesData chesterSprinkles = ChesterSprinklesData.getSprinklesData();
			List<String> presenters = chesterSprinkles.getPresenters();
			
			if (StringUtils.isEmpty(chesterSprinkles.getCurrentChallenge())) {
				return "There is no challenge currently going on.\n";
			}
			
			String userName = currentUser.getFirstName() + " " + currentUser.getLastName();
	
			if (presenters.contains(userName)) {
				return "Ye are already signed up to present, " + currentUser.getFirstName() + ". But I like the enthusiasm!";
			}
			
			//String confirmationReply = "";
			
			/*if (presenters.size() >= 6) {
				confirmationReply = "Awesome! " + currentUser.getFirstName() 
				+ ". Due to time constraints, we have to put a soft cap on the amount of presenters we have. "
				+ "I will place ye on standby for now in case anyone cannot present.";
			}
			else {*/
			String confirmationReply = "Thanks! " + currentUser.getFirstName()
				+ ". Ye have been added to present on " + chesterSprinkles.getCurrentChallengeDate() + "!";
			//}
			
			presenters.add(currentUser.getFirstName() + " " + currentUser.getLastName());
			chesterSprinkles.setPresenters(presenters);
			ChesterSprinklesData.writeSprinklesData(chesterSprinkles);
			
			return confirmationReply;
		}
		
		return null;
	}
}

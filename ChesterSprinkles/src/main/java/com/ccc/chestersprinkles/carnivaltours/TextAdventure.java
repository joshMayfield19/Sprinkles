package com.ccc.chestersprinkles.carnivaltours;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ccc.chestersprinkles.carnivaltours.factory.CarnivalInteractionFactory;
import com.ccc.chestersprinkles.carnivaltours.factory.CarnivalLookAroundFactory;
import com.ccc.chestersprinkles.carnivaltours.model.CTAdventureSession;
import com.ccc.chestersprinkles.carnivaltours.service.CTAdventureSessionService;
import com.ccc.chestersprinkles.model.SlackUser;
import com.ccc.chestersprinkles.service.SlackUserService;

import me.ramswaroop.jbot.core.slack.models.Event;

@Component
public class TextAdventure {
	private CTAdventureSession session;
	
	private boolean readyForNewGame = true;
	
	@Autowired
	private CarnivalInteractionFactory interactionFactory;
	
	@Autowired
	private CarnivalLookAroundFactory lookAroundFactory;
	
	@Autowired
	private TextAdventureCriteria criteria;
	
	@Autowired
	private CTAdventureSessionService ctAdventureSessionService;

	@Autowired
	private SlackUserService slackUserService;

	public TextAdventure() {
		//
	}

	public String startTextAdventure(Event event) {
		resetCriteria();
		createAdventureSession(event);
		return buildAdventureIntroduction();
	}
	
	private void createAdventureSession(Event event) {
		readyForNewGame = false;
		session = new CTAdventureSession();
		String uniqueId = UUID.randomUUID().toString();
		session.setAdventureId(uniqueId);
		session.setCurrentLoc("Carnival Entrance");
		session.setItems("No Items Currently");
		session.setPlayer(getCurrentPlayer(event));
		ctAdventureSessionService.createAdventureSession(session);
		
		criteria.distributeHiddenItems();
	}

	private String getCurrentPlayer(Event event) {
		SlackUser slackUser = slackUserService.getSlackUser(event.getUserId());

		if (slackUser == null) {
			return "Guest" + String.valueOf(TextAdventureHelper.getRandomNumber(10000));
		} else {
			return slackUser.getFirstName() + " " + slackUser.getLastName();
		}
	}
	
	private String buildAdventureIntroduction() {
		StringBuilder adventureStartIntro = new StringBuilder();
		adventureStartIntro.append("Welcome to Carnival Tours! I need your help in finding Montgomery.\n");
		adventureStartIntro.append("------------------------------------------------------------------\n");
		adventureStartIntro.append("What would you like to do?\n");
		return adventureStartIntro.toString();
	}

	public String continueTextAdventure() {
		if (criteria.hasReachedMonrail()) {
			return "Thanks for playing the Text Adventure. I hope you had fun!";
		} else if (criteria.haveFoundAllItems()) {
			return "You found all the items for Monty. A new place has opened up! Go and visit the Monorail.";
		} else {
			return "What would you like to do?\n";
		}
	}

	public String checkCommand(String command) {
		criteria.setCurrentCommand(command);

		String commandReply = "";
		if (TextAdventureHelper.isMoveCommand(command)) {
			commandReply = moveToLocation();
		} else if (TextAdventureHelper.isLookCommand(command)) {
			commandReply = lookAround();
		} else if (TextAdventureHelper.isInteractCommand(command)) {
			commandReply = interact();
		} else if (TextAdventureHelper.isQuestionCommand(command)) {
			commandReply = askQuestion();
		} else if (TextAdventureHelper.isHelpCommand(command)) {
			commandReply = help();
		} else {
			commandReply = "Not a valid command.";
		}

		return commandReply;
	}

	private String moveToLocation() {
		criteria.moveToNewLocation();
		
		if (criteria.getEventState().isValidLocation()) {
			updateSessionLocation();
		}
		else {
			return "I don't know where that location is.";
		}
		
		return criteria.getLocation().getIntroText();
	}
	
	private void updateSessionLocation() {
		session.setCurrentLoc(criteria.getLocation().getLocation());
		ctAdventureSessionService.updateAdventureSession(session);
	}

	private String lookAround() {
		return lookAroundFactory.getLookAroundReply(session, criteria);
	}

	private String interact() {
		return interactionFactory.getCarnivalInteraction(session, criteria);
	}

	private String askQuestion() {
		if (TextAdventureHelper.commandContainsItemsIn(criteria.getCurrentCommand(), TextAdventureConstants.CURR_LOC_TXT)) {
			return session.getCurrentLoc();
		}
		else {
			return session.getItems();
		}
	}
	
	private String help() {
		return "You can do several things. You can go to one of the locations that we have visited in the carnival. \n"
				+ "These locations are Cravings Crazy Cafe, Mainframe Games, House of Mirrors, Lions Den, Spooky Manor, Coaster Trifecta.\n"
				+ "You can try and look around to see if you see anything of importance.\n"
				+ "If you see anything in *bold* you can try to interact with it.";
	}
	
	public boolean isGameOver() {
		return getCriteria().hasReachedMonrail();
	}
	
	public boolean getCriteriaIsContinue() {
		return getCriteria().isEventStateContinue();
	}
	
	public void resetCriteria() {
		getCriteria().resetCriteria();
	}
	
	public boolean isReadyForNewGame() {
		return readyForNewGame;
	}

	public void setReadyForNewGame(boolean readyForNewGame) {
		this.readyForNewGame = readyForNewGame;
	}

	public void setCriteriaIsContinue(boolean isContinue) {
		getCriteria().setEventStateContinue(isContinue);
	}

	public TextAdventureCriteria getCriteria() {
		return criteria;
	}
	
	public void setCriteria (TextAdventureCriteria criteria) {
		this.criteria = criteria;
	}
}

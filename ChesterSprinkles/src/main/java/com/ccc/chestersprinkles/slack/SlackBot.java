package com.ccc.chestersprinkles.slack;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketSession;

import com.ccc.chestersprinkles.model.ChesterSprinklesData;
import com.ccc.chestersprinkles.model.SlackUser;
import com.ccc.chestersprinkles.service.SlackUserService;
import com.ccc.chestersprinkles.utility.ChallengeCommand;
import com.ccc.chestersprinkles.utility.MiscCommand;
import com.ccc.chestersprinkles.utility.PirateCommand;
import com.ccc.chestersprinkles.utility.PiratePointsCommand;
import com.ccc.chestersprinkles.utility.PresenterCommand;
import com.ccc.chestersprinkles.utility.RaffleCommand;

import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.Controller;
import me.ramswaroop.jbot.core.slack.EventType;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;

/**
 * A Slack Bot sample. Ye can create multiple bots by just extending
 * {@link Bot} class like this one.
 *
 * @author ramswaroop
 * @version 1.0.0, 05/06/2016
 */
@Component
@EnableAutoConfiguration
public class SlackBot extends Bot {

	private static final Logger logger = LoggerFactory.getLogger(SlackBot.class);
	private ChesterSprinklesData chesterSprinkles = new ChesterSprinklesData();
	private List<String> ideas = new ArrayList<String>();
	private List<String> commands = new ArrayList<String>();
	private String currentCommand = "";
	private String currentUser = "";
	private boolean addBotCommandReturn = false;
	private boolean addChallengeIdeaReturn = false;
	
	//@Autowired
	//private TextAdventure textAdventure;
	
	@Autowired
	private SlackUserService slackUserService;

	//private static final String JOSH_ID = "U2AR5EH8U";

	/**
	 * Slack token from application.properties file. Ye can get yer slack
	 * token next <a href="https://my.slack.com/services/new/bot">creating a new
	 * bot</a>.
	 */
	@Value("${slackBotToken}")
	private String slackToken;

	@Override
	public String getSlackToken() {
		return slackToken;
	}

	@Override
	public Bot getSlackBot() {
		return this;
	}

	/**
	 * Invoked when an item is pinned in the channel.
	 *
	 * @param session
	 * @param event
	 */
	@Controller(events = EventType.PIN_ADDED)
	public void onPinAdded(WebSocketSession session, Event event) {
		reply(session, event, new Message("Thanks for the pin! Ye can find all pinned items under channel details."));
	}

	/**
	 * Invoked when bot receives an event of type file shared. NOTE: Ye can't
	 * reply to this event as slack doesn't send a channel id for this event
	 * type. Ye can learn more about
	 * <a href="https://api.slack.com/events/file_shared">file_shared</a> event
	 * from Slack's Api documentation.
	 *
	 * @param session
	 * @param event
	 */
	@Controller(events = EventType.FILE_SHARED)
	public void onFileShared(WebSocketSession session, Event event) {
		logger.info("File shared: {}", event);
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!addPresenter|!addMe|!removePresenter|!removeMe|!presenters|!presentationTotal)$")
	public void presenterCommand(WebSocketSession session, Event event) {
		String commandResponse = PresenterCommand.getCommandResponse(event, slackUserService);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}

	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!blaymon|!proposedCommands|!help)$")
	public void miscCommand(WebSocketSession session, Event event) {
		String commandResponse = MiscCommand.getCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!arr|!avast|!matey|!ahoy|!aye|!matey|!ahoy|!aye|!blimey|!marooned|!keelhauled|!howAreYourFuttocksOldMan|!yarr|!shanty|!walkThePlank)$")
	public void pirateCommand(WebSocketSession session, Event event) {
		String commandResponse = PirateCommand.getCommandResponse(event, slackUserService);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!myPirateInfo|!myShipInfo|!myCrewInfo|!pointsHelp|!pointsRewards|!parrotSpeak|!pollyWantACracker)$")
	public void piratePointsCommand(WebSocketSession session, Event event) {
		String commandResponse = PiratePointsCommand.getCommandResponse(event, slackUserService);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!raffle)$")
	public void raffleCommand(WebSocketSession session, Event event) {
		String commandResponse = RaffleCommand.getCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!currentChallenge|!allChallenges|!challengeIdeas)$")
	public void challengeCommand(WebSocketSession session, Event event) {
		String commandResponse = ChallengeCommand.getCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	/*
	 * 
	 *    Conversation Commands
	 * 
	 * 
	 */


	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!sortingEyepatch)$", next = "confirmPirateSort")
	public void sortPirate(WebSocketSession session, Event event) {
		String commandResponse = PiratePointsCommand.getCommandResponse(event, slackUserService);
		
		if (commandResponse != null && commandResponse.contains("matey")) {
			startConversation(event, "confirmPirateSort");
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!confirmPirateSort)$")
	public void confirmPirateSort(WebSocketSession session, Event event) {
		String commandResponse = PiratePointsCommand.getCommandConversationResponse(event, slackUserService);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
			stopConversation(event);
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!addChallengeIdea$", next = "confirmIdeaAdd")
	public void addChallengeIdea(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			if (!addChallengeIdeaReturn) {
				currentUser = event.getUserId();
				startConversation(event, "confirmIdeaAdd");
				reply(session, event, new Message("Awesome! What kind of idea do ye have for a challenge?"));
			} else {
				startConversation(event, "confirmIdeaAdd");
			}
		}
	}

	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!confirmIdeaAdd$")
	public void confirmIdeaAdd(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			if (event.getUserId().equals(currentUser)) {
				chesterSprinkles = ChesterSprinklesData.getSprinklesData();
				SlackUser ideaSubmitter = slackUserService.getSlackUser(currentUser);
				String ideaSubmitterName = ideaSubmitter.getFirstName() + " " + ideaSubmitter.getLastName();
				ideas = chesterSprinkles.getIdeas();
				ideas.add("*Submitted by:* " + ideaSubmitterName + " --- *Idea:* " + event.getText());
				chesterSprinkles.setIdeas(ideas);
				ChesterSprinklesData.writeSprinklesData(chesterSprinkles);
				reply(session, event, new Message("That sounds like a great idea, " + ideaSubmitter.getFirstName()
						+ "! I'll add it to the list."));
				stopConversation(event);
			} else {
				addChallengeIdeaReturn = true;
				addChallengeIdea(session, event);
			}
		}
	}

	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!addBotCommand$", next = "confirmBotCommand")
	public void addBotCommand(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			if (!addBotCommandReturn) {
				startConversation(event, "confirmBotCommand");
				currentUser = event.getUserId();
				reply(session, event, new Message(
						"What command would ye propose me learn and what would I actually be doing when this command is ran?"));
			} else {
				startConversation(event, "confirmBotCommand");
			}
		}
	}

	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!confirmBotCommand$")
	public void confirmBotCommand(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			if (event.getUserId().equals(currentUser)) {
				chesterSprinkles = ChesterSprinklesData.getSprinklesData();
				currentCommand = event.getText();
				SlackUser commandSubmitter = slackUserService.getSlackUser(currentUser);
				String commandSubmitterName = commandSubmitter.getFirstName() + " " + commandSubmitter.getLastName();
				commands = chesterSprinkles.getCommands();
				commands.add("*Submitted by:* " + commandSubmitterName + " --- *Command:* " + currentCommand);
				chesterSprinkles.setCommands(commands);
				ChesterSprinklesData.writeSprinklesData(chesterSprinkles);
				reply(session, event, new Message(
						"Great! I can totally look into that. Thanks " + commandSubmitter.getFirstName() + "!"));
				stopConversation(event);
			} else {
				addBotCommandReturn = true;
				addBotCommand(session, event);
			}
		}
	}
	
	/*
	 * 
	 *    Text Adventure Game
	 * 
	 * 
	 */

	/*@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^!carnivalTours$", next = "nextAction")
	public void carnivalTours(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText()) && JOSH_ID.equals(event.getUserId())) {
			if (!textAdventure.getCriteriaIsContinue() || textAdventure.isReadyForNewGame()) {
				startConversation(event, "nextAction");
				reply(session, event, new Message(textAdventure.startTextAdventure(event)));
			}
			else {
				startConversation(event, "nextAction");
				String reply = textAdventure.continueTextAdventure();
				if (textAdventure.isGameOver()) {
					textAdventure.setReadyForNewGame(true);
					reply(session, event, new Message(reply));
					stopConversation(event);
				}
				else {
					reply(session, event, new Message(reply));
				}
			}
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^!nextAction")
	public void nextAction(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			if ("quit".equalsIgnoreCase(event.getText())) {
				textAdventure.setReadyForNewGame(true);
				stopConversation(event);
			}
			else {
				reply(session, event, new Message(textAdventure.checkCommand(event.getText())));
				textAdventure.setCriteriaIsContinue(true);
				carnivalTours(session, event);
			}
		}
	}*/
}
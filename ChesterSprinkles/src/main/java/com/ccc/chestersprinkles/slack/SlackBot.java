package com.ccc.chestersprinkles.slack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketSession;

import com.ccc.chestersprinkles.carnivaltours.TextAdventure;
import com.ccc.chestersprinkles.model.AllTimePresenter;
import com.ccc.chestersprinkles.model.ChallengeAward;
import com.ccc.chestersprinkles.model.SlackUser;
import com.ccc.chestersprinkles.service.SlackUserService;

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
	private List<String> raffleWinners = new ArrayList<String>();
	private List<String> presenters = new ArrayList<String>();
	private List<String> ideas = new ArrayList<String>();
	private List<String> commands = new ArrayList<String>();
	private String currentCommand = "";
	private String currentUser = "";
	private boolean addBotCommandReturn = false;
	private boolean addChallengeIdeaReturn = false;
	private boolean previousAwardsReturn = false;
	
	@Autowired
	private TextAdventure textAdventure;
	
	@Autowired
	private SlackUserService slackUserService;

	private static final String JOSH_ID = "U2AR5EH8U";

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

	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!help$")
	public void getHelpCommands(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			reply(session, event, new Message("Here is the list of me orders: "
					+ "https://drive.google.com/open?id=1JTev4MYesSjiRIHMbxPkXEG_DOrjGmUb"));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!raffle$")
	public void checkRaffle(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			chesterSprinkles = ChesterSprinklesData.getSprinklesData();
			StringBuilder raffleWinnerOutput = new StringBuilder();
			raffleWinnerOutput.append("Avast! The winners of the last raffle are: ");
			raffleWinners = chesterSprinkles.getRaffleWinners();
			for (int i = 0; i < raffleWinners.size(); i++) {
				raffleWinnerOutput.append(raffleWinners.get(i));

				if (i == raffleWinners.size() - 1) {
					raffleWinnerOutput.append(".");
				} else {
					raffleWinnerOutput.append(", ");
				}
			}

			reply(session, event, new Message("The next raffle will be held on " + chesterSprinkles.getRaffleDateTime()
					+ "! Be ready with yer tickets!\n" + raffleWinnerOutput.toString()));
		}
	}

	/*
	 * 
	 * CHALLENGE COMMANDS
	 * 
	 * 
	 */

	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!currentChallenge$")
	public void checkCurrentChallenge(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			chesterSprinkles = ChesterSprinklesData.getSprinklesData();
			
			if (StringUtils.isEmpty(chesterSprinkles.getCurrentChallenge())) {
				reply(session, event, new Message("There is no challenge currently going on."));
			}
			else {
				reply(session, event,
						new Message("We are currenly working on a problem at the " + chesterSprinkles.getCurrentChallenge()
								+ "\nWe will be presenting our solutions on " + chesterSprinkles.getCurrentChallengeDate()
								+ "\nYe can find the information on this challenge here: "
								+ chesterSprinkles.getCurrentChallengeLink()));
			}
		}
	}

	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!allChallenges$")
	public void getAllChallenges(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			reply(session, event, new Message("Ye can find the information on all of the previous "
					+ "challenges here: https://drive.google.com/drive/u/0/folders/0B_ALbuAWHljrLWxVYTRaXzlQeFU"));
		}
	}

	/*
	 * 
	 * PRESENTER COMMANDS
	 * 
	 * 
	 */

	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!addPresenter|!addMe)$")
	public void addPresenter(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			SlackUser currentUser = slackUserService.getSlackUser(event.getUserId());
			chesterSprinkles = ChesterSprinklesData.getSprinklesData();
			presenters = chesterSprinkles.getPresenters();
			
			if (StringUtils.isEmpty(chesterSprinkles.getCurrentChallenge())) {
				reply(session, event, new Message("There is no challenge currently going on."));
				return;
			}
			
			String userName = currentUser.getFirstName() + " " + currentUser.getLastName();

			if (presenters.contains(userName)) {
				reply(session, event, new Message("Ye are already signed up to present, " + currentUser.getFirstName()
						+ ". But I like the enthusiasm!"));
				return;
			}
			
			String confirmationReply = "";
			
			if (presenters.size() >= 6) {
				confirmationReply = "Awesome! " + currentUser.getFirstName() 
				+ ". Due to time constraints, we have to put a soft cap on the amount of presenters we have. "
				+ "I will place ye on standby for now in case anyone cannot present.";
			}
			else {
				confirmationReply = "Thanks! " + currentUser.getFirstName()
				+ ". Ye have been added to present on " + chesterSprinkles.getCurrentChallengeDate() + "!";
			}
			
			presenters.add(currentUser.getFirstName() + " " + currentUser.getLastName());
			chesterSprinkles.setPresenters(presenters);
			ChesterSprinklesData.writeSprinklesData(chesterSprinkles);
			
			reply(session, event, new Message(confirmationReply));
		}
	}

	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!removePresenter|!removeMe)$", next = "confirmRemPresenter")
	public void removePresenter(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			SlackUser currentUser = slackUserService.getSlackUser(event.getUserId());
			chesterSprinkles = ChesterSprinklesData.getSprinklesData();
			presenters = chesterSprinkles.getPresenters();
			if (presenters.size() > 0) {
				for (int i = 0; i < presenters.size(); i++) {
					if ((presenters.get(i)).equals(currentUser.getFirstName() + " " + currentUser.getLastName())) {
						presenters.remove(i);
						reply(session, event, new Message("Alrighty, " + currentUser.getFirstName()
								+ ", ye have been removed from the presenter list."));
						chesterSprinkles.setPresenters(presenters);
						ChesterSprinklesData.writeSprinklesData(chesterSprinkles);
						return;
					}
				}

				reply(session, event, new Message("Sorry, " + currentUser.getFirstName()
						+ ", ye currently aren't signed up to present at the moment."));
			} else {
				reply(session, event,
						new Message("Nice try! There is no one signed up to present yet. I got my eye on ye "
								+ currentUser.getFirstName() + "."));
			}
		}
	}

	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!presenters$")
	public void viewAllPresenters(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			chesterSprinkles = ChesterSprinklesData.getSprinklesData();
			
			if (StringUtils.isEmpty(chesterSprinkles.getCurrentChallenge())) {
				reply(session, event, new Message("There is no challenge currently going on."));
				return;
			}
			
			presenters = chesterSprinkles.getPresenters();
			int counter = 1;
			boolean standbyPresenterTextAdded = false;
			
			StringBuilder presentersOutput = new StringBuilder();
			presentersOutput.append("The current presenters for " + chesterSprinkles.getCurrentChallenge() + " on "
					+ chesterSprinkles.getCurrentChallengeDate() + " are:\n");
			for (int i = 0; i < presenters.size(); i++) {
				if (counter > 6 && !standbyPresenterTextAdded) {
					presentersOutput.append("*Standby:*\n");
					standbyPresenterTextAdded = true;
				}
				
				presentersOutput.append(counter++).append(". ").append(presenters.get(i)).append("\n");
				
			}

			reply(session, event, new Message(presentersOutput.toString()));
		}
	}

	/*
	 * 
	 * PROBLEMS COMMANDS
	 * 
	 * 
	 */

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

	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!challengeIdeas$")
	public void challengeIdeas(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			chesterSprinkles = ChesterSprinklesData.getSprinklesData();
			StringBuilder ideasOutput = new StringBuilder();
			ideasOutput.append("The current challenge ideas are:\n");
			ideas = chesterSprinkles.getIdeas();
			for (int i = 0; i < ideas.size(); i++) {
				ideasOutput.append(ideas.get(i));

				if (i != ideas.size() - 1) {
					ideasOutput.append("\n");
				}
			}

			reply(session, event, new Message(ideasOutput.toString()));
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

	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!proposedCommands$")
	public void botCommands(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			chesterSprinkles = ChesterSprinklesData.getSprinklesData();
			StringBuilder commandsOutput = new StringBuilder();
			commandsOutput.append("The current proposed commands are:\n");
			commands = chesterSprinkles.getCommands();
			for (int i = 0; i < commands.size(); i++) {
				commandsOutput.append(commands.get(i));

				if (i != commands.size() - 1) {
					commandsOutput.append("\n");
				}
			}

			reply(session, event, new Message(commandsOutput.toString()));
		}
	}

	/*@Controller(events = EventType.MESSAGE, pattern = "(?i)^!party$")
	public void party(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			Random rand = new Random();
			int rando = rand.nextInt(5) + 1;

			if (rando == 1) {
				reply(session, event, new Message(":balloon: Oh yeah! I already have the balloons! :balloon:\n"));
			} else if (rando == 2) {
				reply(session, event, new Message("Let's Boogie! :aw_yeah:\n"));
			} else if (rando == 3) {
				reply(session, event, new Message(":dancing_chicken: Already ahead of ye! :dancing_chicken:\n"));
			} else if (rando == 4) {
				reply(session, event,
						new Message(":goomba: Try and stop me. Go ahead, try. Ye can't. Ye can't stop me.\n"));
			} else {
				reply(session, event,
						new Message("Well I suppose. If ye have finished all of yer chores. :nerd_face:\n"));
			}
		}
	}*/

	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!arr$")
	public void arr(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			reply(session, event, new Message("Arr! Ye be right!"));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!avast$")
	public void avast(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			reply(session, event, new Message("Avast! Look here matey."));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!matey$")
	public void matey(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			reply(session, event, new Message("Are ye me matey?"));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!ahoy$")
	public void ahoy(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			reply(session, event, new Message("And a hearty Ahoy to you!"));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!aye$")
	public void aye(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			reply(session, event, new Message("Aye! Ye be right!"));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!blimey$")
	public void blimey(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			reply(session, event, new Message("Blimey! What ye be so excited fer?"));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!marooned$")
	public void marooned(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			reply(session, event, new Message("Don't ye fret. I would never maroon ye anywheres."));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!keelhauled$")
	public void keelhauled(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			reply(session, event, new Message("There be no keelhauling on our ship."));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!presentationTotal$")
	public void presentationTotal(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			chesterSprinkles = ChesterSprinklesData.getSprinklesData();
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

			reply(session, event, new Message(presentersOutput.toString()));
		}
	}

	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!previousAwards$", next = "whichChallenge")
	public void previousAwards(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			if (!previousAwardsReturn) {
				startConversation(event, "whichChallenge");
				currentUser = event.getUserId();
				chesterSprinkles = ChesterSprinklesData.getSprinklesData();
				StringBuilder replyString = new StringBuilder();
				List<ChallengeAward> awards = chesterSprinkles.getChallengeAwards();

				replyString.append("Which challenge do ye want to see the awards for (Pick the Number)?\n");

				for (ChallengeAward award : awards) {
					replyString.append("(").append(award.getId()).append(") ").append(award.getChallengeName())
							.append("\n");
				}

				reply(session, event, new Message(replyString.toString()));
			} else {
				startConversation(event, "whichChallenge");
			}
		}
	}

	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!whichChallenge$")
	public void whichChallenge(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			if (event.getUserId().equals(currentUser)) {
				List<ChallengeAward> awards = chesterSprinkles.getChallengeAwards();
				String selection = event.getText().trim();
				StringBuilder repString = new StringBuilder();
				boolean found = false;

				for (ChallengeAward award : awards) {
					if (award.getId().equals(selection)) {
						repString.append("*Challenge:* ").append(award.getChallengeName()).append("\n")
								.append("*Most Creative:* ").append(award.getMostCreative()).append("\n")
								.append("*Most Informative:* ").append(award.getMostInformative()).append("\n")
								.append("*Raffle Winners:* ").append(award.getPrizeWinners()).append("\n");
						found = true;
					}
				}

				if (!found) {
					repString.append("Sorry, that's not a valid selection.");
				}

				reply(session, event, new Message(repString.toString()));
				stopConversation(event);
			} else {
				previousAwardsReturn = true;
				previousAwards(session, event);
			}
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^!carnivalTours$", next = "nextAction")
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
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^!walkThePlank")
	public void walkThePlank(WebSocketSession session, Event event) {
		if (event.getUserId() != null && !StringUtils.isEmpty(event.getText())) {
			List<SlackUser> allUsers = slackUserService.getSlackUsers();
			
			Random rand = new Random();
			int randomUser = rand.nextInt(allUsers.size()) + 1;
			
			SlackUser randomSlackUser = allUsers.get(randomUser);
			
			reply(session, event, new Message("Ahoy! It's time for " + randomSlackUser.getFirstName()  + 
					" " + randomSlackUser.getLastName() + " to walk the plank! Get moving!"));
		}
	}
}
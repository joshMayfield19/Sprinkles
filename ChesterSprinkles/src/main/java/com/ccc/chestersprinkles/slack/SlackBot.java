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
import com.ccc.chestersprinkles.service.ChallengeService;
import com.ccc.chestersprinkles.service.SlackUserService;
import com.ccc.chestersprinkles.utility.ChallengeCommand;
import com.ccc.chestersprinkles.utility.MiscCommand;
import com.ccc.chestersprinkles.utility.PirateCommand;
import com.ccc.chestersprinkles.utility.PiratePointsCommand;
import com.ccc.chestersprinkles.utility.PresenterCommand;
import com.ccc.chestersprinkles.utility.RaffleCommand;
import com.ccc.chestersprinkles.utility.ShopCommand;

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
	private static final String KD_SPRINKLES_CHANNEL = "G7S3DCZAB";
	private static final String CODING_CHALLENGE_CHANNEL = "C5VRF4892";
	
	//@Autowired
	//private TextAdventure textAdventure;
	
	@Autowired
	private SlackUserService slackUserService;

	@Autowired
	private ChallengeService challengeService;
	
	@Autowired
	private PiratePointsCommand piratePointsCommand;
	
	@Autowired
	private PirateCommand pirateCommand;
	
	@Autowired
	private ShopCommand shopCommand;
	
	@Autowired
	private MiscCommand miscCommand;
	
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
	
	/**************************************
	 * 
	 * 		PRESENTER COMMANDS
	 * 
	 * 
	 **************************************
	 */
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!addPresenter|!addMe)$")
	public void addPresenter(WebSocketSession session, Event event) {
		String commandResponse = PresenterCommand.getAddPresenterCommandResponse(event, slackUserService);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!removePresenter|!removeMe)$")
	public void removePresenter(WebSocketSession session, Event event) {
		String commandResponse = PresenterCommand.getRemovePresenterCommandResponse(event, slackUserService);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!presenters)$")
	public void presenters(WebSocketSession session, Event event) {
		String commandResponse = PresenterCommand.getPresentersCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!presentationTotal)$")
	public void presentationTotal(WebSocketSession session, Event event) {
		String commandResponse = PresenterCommand.getPresentationTotalCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	/**************************************
	 * 
	 * 		MISCELLANEOUS COMMANDS
	 * 
	 * 
	 **************************************
	 */
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!blaymon)$")
	public void blaymon(WebSocketSession session, Event event) {
		String commandResponse = MiscCommand.getBlaymonCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!proposedCommands)$")
	public void proposedCommands(WebSocketSession session, Event event) {
		String commandResponse = MiscCommand.getProposedCommandsCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!help)$")
	public void help(WebSocketSession session, Event event) {
		String commandResponse = MiscCommand.getHelpCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!hackThePlanet)$")
	public void hackThePlanet(WebSocketSession session, Event event) {
		String commandResponse = MiscCommand.getHackThePlanetCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!weapoj)$")
	public void weapoj(WebSocketSession session, Event event) {
		String commandResponse = miscCommand.getWeapojCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	/**************************************
	 * 
	 * 		PIRATE COMMANDS
	 * 
	 * 
	 **************************************
	 */
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!whatDoWeDoWithADrunkenSailor|!drunkenSailor)$")
	public void whatDoWeDoWithADrunkenSailor(WebSocketSession session, Event event) {
		String commandResponse = PirateCommand.getWhatDoYouDoDrunkerCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!arr)$")
	public void arr(WebSocketSession session, Event event) {
		String commandResponse = PirateCommand.getArrCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!avast)$")
	public void avast(WebSocketSession session, Event event) {
		String commandResponse = PirateCommand.getAvastCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!matey)$")
	public void matey(WebSocketSession session, Event event) {
		String commandResponse = PirateCommand.getMateyCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!ahoy)$")
	public void ahoy(WebSocketSession session, Event event) {
		String commandResponse = PirateCommand.getAhoyCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!aye)$")
	public void aye(WebSocketSession session, Event event) {
		String commandResponse = PirateCommand.getAyeCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!blimey)$")
	public void blimey(WebSocketSession session, Event event) {
		String commandResponse = PirateCommand.getBlimeyCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!marooned)$")
	public void marooned(WebSocketSession session, Event event) {
		String commandResponse = PirateCommand.getMaroonedCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!keelhauled)$")
	public void keelhauled(WebSocketSession session, Event event) {
		String commandResponse = PirateCommand.getKeelhauledCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!howAreYourFuttocksOldMan)$")
	public void howAreYourFuttocksOldMan(WebSocketSession session, Event event) {
		String commandResponse = PirateCommand.getFuttocksCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!yarr)$")
	public void yarr(WebSocketSession session, Event event) {
		String commandResponse = PirateCommand.getYarrCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!shanty)$")
	public void shanty(WebSocketSession session, Event event) {
		String commandResponse = PirateCommand.getShantyCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!walkThePlank)$")
	public void walkThePlank(WebSocketSession session, Event event) {
		String commandResponse = pirateCommand.getWalkThePlankCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!plankLife)$")
	public void plankLife(WebSocketSession session, Event event) {
		String commandResponse = pirateCommand.getTopPlankWalkersCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	/**************************************
	 * 
	 * 		PIRATE POINTS COMMANDS
	 * 
	 * 
	 **************************************
	 */
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!upcomingEvents)$")
	public void upcomingEvents(WebSocketSession session, Event event) {
		String commandResponse = PiratePointsCommand.getUpcomingEventsCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!sortingEyepatch(.*))$")
	public void sortPirate(WebSocketSession session, Event event) {
		String commandResponse = piratePointsCommand.getSortingEyepatchCommandResponse(event);
		
		if (commandResponse != null) {
			event.setChannelId(CODING_CHALLENGE_CHANNEL);
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!addPoints(.*))$")
	public void addPoints(WebSocketSession session, Event event) {
		String commandResponse = piratePointsCommand.getAddPointsCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!myPirateInfo)$")
	public void myPirateInfo(WebSocketSession session, Event event) {
		String commandResponse = piratePointsCommand.getMyPirateInfoCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!myPirateInfo)$")
	public void myPirateInfoChannel(WebSocketSession session, Event event) {
		String commandResponse = PiratePointsCommand.getDirectMessageChannelCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!myShipInfo)$")
	public void myShipInfo(WebSocketSession session, Event event) {
		String commandResponse = piratePointsCommand.getMyShipInfoCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!myShipInfo)$")
	public void myShipInfoChannel(WebSocketSession session, Event event) {
		String commandResponse = PiratePointsCommand.getDirectMessageChannelCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!myCrewInfo)$")
	public void myCrewInfo(WebSocketSession session, Event event) {
		String commandResponse = piratePointsCommand.getMyCrewInfoCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!sample(.*))$")
	public void sample(WebSocketSession session, Event event) {
		String commandResponse = PiratePointsCommand.getSample(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!myCrewInfo)$")
	public void myCrewInfoChannel(WebSocketSession session, Event event) {
		String commandResponse = PiratePointsCommand.getDirectMessageChannelCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!piratePointsHelp)$")
	public void piratePointsHelp(WebSocketSession session, Event event) {
		String commandResponse = PiratePointsCommand.getPiratePointsHelpCommandResponse(event, slackUserService);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^([^!].*)$")
	public void gatherParrotLanguage(WebSocketSession session, Event event) {
		PiratePointsCommand.getGatherParrotLanguageCommandResponse(event);
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!pollyWantACracker)$")
	public void pollyWantACracker(WebSocketSession session, Event event) {
		String commandResponse = piratePointsCommand.getPollyWantACrackerCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!setSail)$")
	public void setSail(WebSocketSession session, Event event) {
		List<String> commandResponses = piratePointsCommand.getSetSailCommandResponse(event);
		
		if (commandResponses != null) {
			reply(session, event, new Message(commandResponses.get(0)));
			
			if (commandResponses.size() == 2) {
				event.setChannelId(CODING_CHALLENGE_CHANNEL);
				reply(session, event, new Message(commandResponses.get(1)));
			}
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!battle)$")
	public void battle(WebSocketSession session, Event event) {
		List<String> commandResponses = piratePointsCommand.getBattleCommandResponse(event);
		
		if (commandResponses != null) {
			reply(session, event, new Message(commandResponses.get(0)));
			
			if (commandResponses.size() == 2) {
				event.setChannelId(CODING_CHALLENGE_CHANNEL);
				reply(session, event, new Message(commandResponses.get(1)));
			}
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!explore)$")
	public void explore(WebSocketSession session, Event event) {
		List<String> commandResponses = piratePointsCommand.getExploreCommandResponse(event);
		
		if (commandResponses != null) {
			reply(session, event, new Message(commandResponses.get(0)));
			
			if (commandResponses.size() == 2) {
				event.setChannelId(CODING_CHALLENGE_CHANNEL);
				reply(session, event, new Message(commandResponses.get(1)));
			}
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!shoreleave)$")
	public void shoreleave(WebSocketSession session, Event event) {
		List<String> commandResponses = piratePointsCommand.getShoreleaveCommandResponse(event);
		
		if (commandResponses != null) {
			reply(session, event, new Message(commandResponses.get(0)));
			
			if (commandResponses.size() == 2) {
				event.setChannelId(CODING_CHALLENGE_CHANNEL);
				reply(session, event, new Message(commandResponses.get(1)));
			}
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!plunder)$")
	public void plunder(WebSocketSession session, Event event) {
		List<String> commandResponses = piratePointsCommand.getPlunderCommandResponse(event);
		
		if (commandResponses != null) {
			reply(session, event, new Message(commandResponses.get(0)));
			
			if (commandResponses.size() == 2) {
				event.setChannelId(CODING_CHALLENGE_CHANNEL);
				reply(session, event, new Message(commandResponses.get(1)));
			}
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!grog)$")
	public void grog(WebSocketSession session, Event event) {
		List<String> commandResponses = piratePointsCommand.getGrogCommandResponse(event);
		
		if (commandResponses != null) {
			reply(session, event, new Message(commandResponses.get(0)));
			
			if (commandResponses.size() == 2) {
				event.setChannelId(CODING_CHALLENGE_CHANNEL);
				reply(session, event, new Message(commandResponses.get(1)));
			}
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!activate(.*))$")
	public void activateDoubloons(WebSocketSession session, Event event) {
		String commandResponse = piratePointsCommand.getActivateDoubloonsCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!topFiveAct)$")
	public void activateTopFive(WebSocketSession session, Event event) {
		String commandResponse = piratePointsCommand.getTopFiveActivationResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!whatAreDoubloons)$")
	public void whatAreDoubloons(WebSocketSession session, Event event) {
		String commandResponse = PiratePointsCommand.getWhatAreDoubloonsHelpCommandResponse(event, slackUserService);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!adventure)$")
	public void adventure(WebSocketSession session, Event event) {
		String commandResponse = piratePointsCommand.getAdventureCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!whatArePiratePoints)$")
	public void whatArePiratePoints(WebSocketSession session, Event event) {
		String commandResponse = PiratePointsCommand.getWhatArePiratePointsHelpCommandResponse(event, slackUserService);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!pirateLeaderboard)$")
	public void pirateLeaderboard(WebSocketSession session, Event event) {
		String commandResponse = piratePointsCommand.getPirateLeaderboardCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!shipLeaderboard)$")
	public void shipLeaderboard(WebSocketSession session, Event event) {
		String commandResponse = piratePointsCommand.getShipLeaderboardCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	/**************************************
	 * 
	 * 		RAFFLE COMMANDS
	 * 
	 * 
	 **************************************
	 */
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!raffle)$")
	public void raffleCommand(WebSocketSession session, Event event) {
		String commandResponse = RaffleCommand.getRaffleCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!startRaffle)$")
	public void startRaffle(WebSocketSession session, Event event) {
		String commandResponse = RaffleCommand.getStartRaffleCommandResponse(event);
		
		if (commandResponse != null) {
			event.setChannelId(CODING_CHALLENGE_CHANNEL);
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!redrawTicket)$")
	public void redrawTicket(WebSocketSession session, Event event) {
		String commandResponse = RaffleCommand.getRedrawTicketCommandResponse(event);
		
		if (commandResponse != null) {
			event.setChannelId(CODING_CHALLENGE_CHANNEL);
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!nextTicket(.*))$")
	public void nextTicket(WebSocketSession session, Event event) {
		String commandResponse = RaffleCommand.getNextTicketCommandResponse(event);
		
		if (commandResponse != null) {
			event.setChannelId(CODING_CHALLENGE_CHANNEL);
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!endRaffle)$")
	public void endRaffle(WebSocketSession session, Event event) {
		String commandResponse = RaffleCommand.getEndRaffleCommandResponse(event);
		
		if (commandResponse != null) {
			event.setChannelId(CODING_CHALLENGE_CHANNEL);
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!startCountdown)$")
	public void startCountdown(WebSocketSession session, Event event) {
		String commandResponse = RaffleCommand.getStartCountdownCommandResponse(event);
		
		if (commandResponse != null) {
			event.setChannelId(CODING_CHALLENGE_CHANNEL);
			reply(session, event, new Message(commandResponse));
		}
	}
	
	/**************************************
	 * 
	 * 		SHOP COMMANDS
	 * 
	 * 
	 **************************************
	 */
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!shopItems)$")
	public void shopItems(WebSocketSession session, Event event) {
		String commandResponse = shopCommand.getAllShopItems(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!buyPolly)$")
	public void buyPolly(WebSocketSession session, Event event) {
		String commandResponse = shopCommand.buyPollyWantACracker(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!buyName(.*))$")
	public void buyName(WebSocketSession session, Event event) {
		String commandResponse = shopCommand.buyNameChange(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!buyBottle)$")
	public void buyBottle(WebSocketSession session, Event event) {
		reply(session, event, new Message("Command not implemented yet."));
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!buyWfh)$")
	public void buyWfh(WebSocketSession session, Event event) {
		reply(session, event, new Message("Command not implemented yet."));
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!buy2Raffle)$")
	public void buy2Raffle(WebSocketSession session, Event event) {
		reply(session, event, new Message("Command not implemented yet."));
	}
	
	@Controller(events = EventType.DIRECT_MESSAGE, pattern = "(?i)^(!buyIgnoreRaffle)$")
	public void buyIgnoreRaffle(WebSocketSession session, Event event) {
		reply(session, event, new Message("Command not implemented yet."));
	}
	
	/**************************************
	 * 
	 * 		CHALLENGE COMMANDS
	 * 
	 * 
	 **************************************
	 */
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!currentChallenge)$")
	public void currentChallenge(WebSocketSession session, Event event) {
		String commandResponse = ChallengeCommand.getCurrentChallengeCommandResponse(event, challengeService);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!allChallenges)$")
	public void allChallenges(WebSocketSession session, Event event) {
		String commandResponse = ChallengeCommand.getAllChallengesCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!challengeIdeas)$")
	public void challengeIdeas(WebSocketSession session, Event event) {
		String commandResponse = ChallengeCommand.getChallengeIdeasCommandResponse(event);
		
		if (commandResponse != null) {
			reply(session, event, new Message(commandResponse));
		}
	}
	
	/**************************************
	 * 
	 * 		CONVERSATION COMMANDS
	 * 
	 * 
	 **************************************
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

	@Controller(events = EventType.MESSAGE, pattern = "(?i)^(!addBotCommand|!addCommand)$", next = "confirmBotCommand")
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
}
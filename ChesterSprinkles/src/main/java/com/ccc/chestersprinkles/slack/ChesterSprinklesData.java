package com.ccc.chestersprinkles.slack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.ccc.chestersprinkles.model.AllTimePresenter;
import com.ccc.chestersprinkles.model.ChallengeAward;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement (name = "chester_sprinkles")
@XmlAccessorType (XmlAccessType.FIELD)
public class ChesterSprinklesData {
	
	@XmlElement (name = "raffleDateTime")
	private String raffleDateTime;
	
	@XmlElement (name = "currentChallenge")
	private String currentChallenge;
    
	@XmlElement (name = "currentChallengeDate")
	private String currentChallengeDate;

	@XmlElement (name = "currentChallengeLink")
	private String currentChallengeLink;
	
    //@XmlJavaTypeAdapter(SprinklesAdapter.class)
	@XmlElementWrapper(name="raffleWinners")
    @XmlElement(name="raffleWinner")
	private List<String> raffleWinners;
    
    //@XmlJavaTypeAdapter(SprinklesAdapter.class)
	@XmlElementWrapper(name="presenters")
    @XmlElement(name="presenter")
	private List<String> presenters;
	
	@XmlElementWrapper(name="allTimePresenters")
	@XmlElement(name="allTimePresenter")
	private List<AllTimePresenter> allTimePresenters;
    
	@XmlElementWrapper(name="challengeAwards")
	@XmlElement(name="challengeAward")
	private List<ChallengeAward> challengeAwards;
	
    //@XmlJavaTypeAdapter(SprinklesAdapter.class)
	@XmlElementWrapper(name="ideas")
    @XmlElement(name="idea")
	private List<String> ideas;
    
	@XmlElementWrapper(name="commands")
    @XmlElement(name="command")
	private List<String> commands;
	
    public ChesterSprinklesData() {
    	raffleDateTime = "";
    	currentChallenge = "";
    	currentChallengeDate = "";
    	currentChallengeLink = "";
    	raffleWinners = new ArrayList<String>();
    	presenters = new ArrayList<String>();
    	ideas = new ArrayList<String>();
    	commands = new ArrayList<String>();
    }

    public static ChesterSprinklesData getSprinklesData() {
    	ChesterSprinklesData botData = new ChesterSprinklesData();

		try {
			File file = new File("src/chester_sprinkles.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(ChesterSprinklesData.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			botData = (ChesterSprinklesData) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException jaxbe) {
			jaxbe.printStackTrace();
		}
		return botData;
	}

	public static void writeSprinklesData(ChesterSprinklesData bot) {
		try {
			File file = new File("src/chester_sprinkles.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(ChesterSprinklesData.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(bot, file);
			jaxbMarshaller.marshal(bot, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
    
    public String getRaffleDateTime() {
		return raffleDateTime;
	}

	public String getCurrentChallenge() {
		return currentChallenge;
	}

	public String getCurrentChallengeLink() {
		return currentChallengeLink;
	}

	public List<String> getRaffleWinners() {
		return raffleWinners;
	}

	public List<String> getPresenters() {
		return presenters;
	}

	public List<String> getIdeas() {
		return ideas;
	}
	
	public List<String> getCommands() {
		return commands;
	}

	public List<AllTimePresenter> getAllTimePresenters() {
		return allTimePresenters;
	}

	public List<ChallengeAward> getChallengeAwards() {
		return challengeAwards;
	}

	public void setChallengeAwards(List<ChallengeAward> challengeAwards) {
		this.challengeAwards = challengeAwards;
	}

	public void setAllTimePresenters(List<AllTimePresenter> allTimePresenters) {
		this.allTimePresenters = allTimePresenters;
	}

	public void setRaffleDateTime(String raffleDateTime) {
		this.raffleDateTime = raffleDateTime;
	}

	public void setCurrentChallenge(String currentChallenge) {
		this.currentChallenge = currentChallenge;
	}

	public void setCurrentChallengeLink(String currentChallengeLink) {
		this.currentChallengeLink = currentChallengeLink;
	}

	public void setRaffleWinners(List<String> raffleWinners) {
		this.raffleWinners = raffleWinners;
	}

	public void setPresenters(List<String> presenters) {
		this.presenters = presenters;
	}

	public void setIdeas(List<String> ideas) {
		this.ideas = ideas;
	}
	
	public void setCommands(List<String> commands) {
		this.commands = commands;
	}
	
	public String getCurrentChallengeDate() {
		return currentChallengeDate;
	}

	public void setCurrentChallengeDate(String currentChallengeDate) {
		this.currentChallengeDate = currentChallengeDate;
	}
    
}

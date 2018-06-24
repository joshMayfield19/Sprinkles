package com.ccc.chestersprinkles.model;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "pirate_points")
@XmlAccessorType (XmlAccessType.FIELD)
public class PiratePointsData {
	
	@XmlElementWrapper(name="pirateShips")
	@XmlElement(name="pirateShip")
	private List<PirateShip> pirateShips;
    
	@XmlElementWrapper(name="pirates")
	@XmlElement(name="pirate")
	private List<Pirate> pirates;
	
	@XmlElementWrapper(name="upcomingEvents")
	@XmlElement(name="upcomingEvent")
	private List<UpcomingEvent> upcomingEvents;
    
    public PiratePointsData() {
    	
    }

    public static PiratePointsData getPiratePointsData() {
    	PiratePointsData piratePointsData = new PiratePointsData();

		try {
			File file = new File("src/pirate_points.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(PiratePointsData.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			piratePointsData = (PiratePointsData) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException jaxbe) {
			jaxbe.printStackTrace();
		}
		return piratePointsData;
	}

	public static void writePiratePointsData(PiratePointsData piratePointsData) {
		try {
			File file = new File("src/pirate_points.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(PiratePointsData.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(piratePointsData, file);
			jaxbMarshaller.marshal(piratePointsData, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public List<PirateShip> getPirateShips() {
		return pirateShips;
	}

	public List<Pirate> getPirates() {
		return pirates;
	}

	public void setPirateShips(List<PirateShip> pirateShips) {
		this.pirateShips = pirateShips;
	}

	public void setPirates(List<Pirate> pirates) {
		this.pirates = pirates;
	}
	
	public List<UpcomingEvent> getUpcomingEvents() {
		return upcomingEvents;
	}

	public void setUpcomingEvents(List<UpcomingEvent> upcomingEvents) {
		this.upcomingEvents = upcomingEvents;
	}
}

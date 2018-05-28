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

@XmlRootElement (name = "pirate_points_hist")
@XmlAccessorType (XmlAccessType.FIELD)
public class PiratePointsDataHistory {
	
	@XmlElementWrapper(name="pirateHistories")
	@XmlElement(name="pirateHistory")
	private List<PirateHistory> pirateHistories;
    
    public PiratePointsDataHistory() {
    	
    }

    public static PiratePointsDataHistory getPiratePointsDataHistory() {
    	PiratePointsDataHistory piratePointsDataHistory = new PiratePointsDataHistory();

		try {
			File file = new File("src/pirate_points_hist.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(PiratePointsDataHistory.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			piratePointsDataHistory = (PiratePointsDataHistory) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException jaxbe) {
			jaxbe.printStackTrace();
		}
		return piratePointsDataHistory;
	}

	public static void writePiratePointsDataHistory(PiratePointsDataHistory piratePointsDataHistory) {
		try {
			File file = new File("src/pirate_points_hist.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(PiratePointsDataHistory.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(piratePointsDataHistory, file);
			jaxbMarshaller.marshal(piratePointsDataHistory, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public List<PirateHistory> getPirates() {
		return pirateHistories;
	}

	public void setPirates(List<PirateHistory> pirateHistories) {
		this.pirateHistories = pirateHistories;
	}
}

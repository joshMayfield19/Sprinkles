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

@XmlRootElement (name = "parrot_language")
@XmlAccessorType (XmlAccessType.FIELD)
public class ParrotLanguageStorage {
	
	@XmlElementWrapper(name="phrases")
	@XmlElement(name="phrase")
	private List<String> phrases;
    
    public ParrotLanguageStorage() {
    	
    }

    public static ParrotLanguageStorage getParrotLanguageStorage() {
    	ParrotLanguageStorage parrotLanguageStorage = new ParrotLanguageStorage();

		try {
			File file = new File("src/parrot_language.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(ParrotLanguageStorage.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			parrotLanguageStorage = (ParrotLanguageStorage) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException jaxbe) {
			jaxbe.printStackTrace();
		}
		return parrotLanguageStorage;
	}

	public static void writeParrotLanguageStorage(ParrotLanguageStorage parrotLanguageStorage) {
		try {
			File file = new File("src/parrot_language.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(ParrotLanguageStorage.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(parrotLanguageStorage, file);
			jaxbMarshaller.marshal(parrotLanguageStorage, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public List<String> getPhrases() {
		return phrases;
	}

	public void setPhrases(List<String> phrases) {
		this.phrases = phrases;
	}
}

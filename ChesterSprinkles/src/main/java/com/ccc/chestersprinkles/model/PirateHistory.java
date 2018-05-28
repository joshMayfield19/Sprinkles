package com.ccc.chestersprinkles.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pirateHistory")
@XmlAccessorType (XmlAccessType.FIELD)
public class PirateHistory {
	private String realName;
	private List<String> pointEvents;
	
	public PirateHistory () {
		
	}

	public String getRealName() {
		return realName;
	}

	public List<String> getPointEvents() {
		return pointEvents;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setPointEvents(List<String> pointEvents) {
		this.pointEvents = pointEvents;
	}
}

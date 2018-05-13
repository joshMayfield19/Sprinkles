package com.ccc.chestersprinkles.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "allTimePresenter")
@XmlAccessorType (XmlAccessType.FIELD)
public class AllTimePresenter implements Comparable<AllTimePresenter>{
	private String name;
	private int timesPresented;
	
	public String getName() {
		return name;
	}
	public int getTimesPresented() {
		return timesPresented;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTimesPresented(int timesPresented) {
		this.timesPresented = timesPresented;
	}
	
	@Override
	public int compareTo(AllTimePresenter atp) {
		if (timesPresented == atp.timesPresented) {
			return 0;
		}
		else if (timesPresented < atp.timesPresented) {
			return 1;
		}
		else {
			return -1;
		}
	}
}

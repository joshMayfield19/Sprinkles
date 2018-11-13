package com.ccc.chestersprinkles.model;

public class ManagerAssociation {
	private int associationId;
	private int managerPirateId;
	private String managerName;
	private String managerLevel;
	private int directReportPirateId;
	private String directReportName;
	private String directReportLevel;
	
	public ManagerAssociation() {
		
	}

	public int getAssociationId() {
		return associationId;
	}

	public int getManagerPirateId() {
		return managerPirateId;
	}

	public String getManagerName() {
		return managerName;
	}

	public String getManagerLevel() {
		return managerLevel;
	}

	public int getDirectReportPirateId() {
		return directReportPirateId;
	}

	public String getDirectReportName() {
		return directReportName;
	}

	public String getDirectReportLevel() {
		return directReportLevel;
	}

	public void setAssociationId(int associationId) {
		this.associationId = associationId;
	}

	public void setManagerPirateId(int managerPirateId) {
		this.managerPirateId = managerPirateId;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public void setManagerLevel(String managerLevel) {
		this.managerLevel = managerLevel;
	}

	public void setDirectReportPirateId(int directReportPirateId) {
		this.directReportPirateId = directReportPirateId;
	}

	public void setDirectReportName(String directReportName) {
		this.directReportName = directReportName;
	}

	public void setDirectReportLevel(String directReportLevel) {
		this.directReportLevel = directReportLevel;
	}
	
	
}

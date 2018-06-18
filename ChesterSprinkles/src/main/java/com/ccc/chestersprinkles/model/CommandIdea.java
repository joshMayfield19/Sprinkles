package com.ccc.chestersprinkles.model;

public class CommandIdea {
	private int commandIdeaId;
	private int userId;
	private String command;
	private String action;
	
	public CommandIdea() {
		
	}

	public int getCommandIdeaId() {
		return commandIdeaId;
	}

	public int getUserId() {
		return userId;
	}

	public String getCommand() {
		return command;
	}

	public String getAction() {
		return action;
	}

	public void setCommandIdeaId(int commandIdeaId) {
		this.commandIdeaId = commandIdeaId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public void setAction(String action) {
		this.action = action;
	}
}

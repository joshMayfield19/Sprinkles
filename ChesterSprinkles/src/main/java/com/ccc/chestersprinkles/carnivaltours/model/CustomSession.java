package com.ccc.chestersprinkles.carnivaltours.model;

import org.springframework.web.socket.WebSocketSession;

import com.ccc.chestersprinkles.carnivaltours.TextAdventure;

public class CustomSession {
	private String userId;
	private WebSocketSession session;
	private TextAdventure textAdventure;
	
	public CustomSession (String userId, WebSocketSession session, TextAdventure textAdventure) {
		this.userId = userId;
		this.session = session;
		this.textAdventure = textAdventure;
	}
	
	public String getUserId() {
		return userId;
	}
	public WebSocketSession getSession() {
		return session;
	}
	public TextAdventure getTextAdventure() {
		return textAdventure;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setSession(WebSocketSession session) {
		this.session = session;
	}
	public void setTextAdventure(TextAdventure textAdventure) {
		this.textAdventure = textAdventure;
		
	}
}

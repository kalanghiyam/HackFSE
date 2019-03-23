package com.cts.fse.feedback.jwt.message.response;


public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String username;
	private String role;
	private String eventId;
	private String eventStatus;
	private String responded;
	
	public JwtResponse(String token, String username, String role, String eventId,String eventStatus,String responded) {
		super();
		this.token = token;
		this.username = username;
		this.role = role;
		this.eventId = eventId;
		this.eventStatus = eventStatus;
		this.responded = responded;
	}

	
	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}


	public String getResponded() {
		return responded;
	}


	public void setResponded(String responded) {
		this.responded = responded;
	}


	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	
}
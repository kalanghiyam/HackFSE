package com.cts.fse.feedback.dto;


public class UserRoleDTO {

    private Integer associateId;
    
    private String role;
    
    private String associateName;
  
    private String eventId;
    
    private String eventStatus;

    private String responded;
    
    
	public String getResponded() {
		return responded;
	}

	public void setResponded(String responded) {
		this.responded = responded;
	}

	public Integer getAssociateId() {
		return associateId;
	}

	public void setAssociateId(Integer associateId) {
		this.associateId = associateId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAssociateName() {
		return associateName;
	}

	public void setAssociateName(String associateName) {
		this.associateName = associateName;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
    
    
}

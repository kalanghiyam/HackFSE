package com.cts.fse.feedback.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class UserRoleDetails  implements Serializable {

	@Id
    private Integer associateId;
    
    private String role;
    
    private String associateName;
    
    @Transient
    private String eventId;
    
    @Transient
    private String eventStatus;
    
    @Transient
    private String responded;
    
    
	public String getResponded() {
		return responded;
	}
	public void setResponded(String responded) {
		this.responded = responded;
	}
	public String getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getAssociateName() {
		return associateName;
	}
	public void setAssociateName(String associateName) {
		this.associateName = associateName;
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
		this.role = role.toUpperCase();
	}
	@Override
	public String toString() {
		return "UserRoleDetails [associateId=" + associateId + ", role=" + role + ", associateName=" + associateName
				+ ", eventId=" + eventId + ", eventStatus=" + eventStatus + "]";
	}
	
}

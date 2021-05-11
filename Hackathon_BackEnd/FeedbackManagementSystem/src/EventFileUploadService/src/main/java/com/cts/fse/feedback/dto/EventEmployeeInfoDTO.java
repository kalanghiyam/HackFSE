package com.cts.fse.feedback.dto;


public class EventEmployeeInfoDTO {

	 private Integer associateId;
		
	 private String eventId;
	 
	 private String eventStatus;
	 
	 private String responded;

	 private String associateName;
	 
	 private String bu;
	 
	 private boolean select;
	 
	 
	public boolean getSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
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

	public String getResponded() {
		return responded;
	}

	public void setResponded(String responded) {
		this.responded = responded;
	}
	 
	 
}

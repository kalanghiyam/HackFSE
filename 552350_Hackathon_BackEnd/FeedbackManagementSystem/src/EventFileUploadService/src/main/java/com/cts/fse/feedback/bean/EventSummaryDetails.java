package com.cts.fse.feedback.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

@Entity
public class EventSummaryDetails implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    private String eventId;
	@Column(nullable = false)
	private String month;
	@Column(nullable = false)
	private String baseLocation;
	@Column(nullable = false)
	private String beneficiaryName;
	private String venueAddress;
	private String councilName;
	@Column(nullable = false)
	private String project;
	@Column(nullable = false)
	private String eventName;
	@Lob
	private String eventDesc;
	@Column(nullable = false)
	private Date eventDate;
	private BigDecimal noofVolunteer;
	private BigDecimal volunteerhrs;
    private BigDecimal overallVolRingHrs;
    private BigDecimal livesImpacted;
    private String activityType;
    private String status;
    @Column(nullable = false)
    private String pocId;
    @Column(nullable = false)
    private String pocName;
    private int pocContactNo;
    @Transient
    private String eventDateString;
    @Transient
    private String pocContactNoString;
 
	public String getEventDateString() {
		return eventDateString;
	}
	public void setEventDateString(String eventDateString) {
		this.eventDateString = eventDateString;
	}
	public String getPocContactNoString() {
		return pocContactNoString;
	}
	public void setPocContactNoString(String pocContactNoString) {
		this.pocContactNoString = pocContactNoString;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getBaseLocation() {
		return baseLocation;
	}
	public void setBaseLocation(String baseLocation) {
		this.baseLocation = baseLocation;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getVenueAddress() {
		return venueAddress;
	}
	public void setVenueAddress(String venueAddress) {
		this.venueAddress = venueAddress;
	}
	public String getCouncilName() {
		return councilName;
	}
	public void setCouncilName(String councilName) {
		this.councilName = councilName;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventDesc() {
		return eventDesc;
	}
	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public BigDecimal getNoofVolunteer() {
		return noofVolunteer;
	}
	public void setNoofVolunteer(BigDecimal noofVolunteer) {
		this.noofVolunteer = noofVolunteer;
	}
	public BigDecimal getVolunteerhrs() {
		return volunteerhrs;
	}
	public void setVolunteerhrs(BigDecimal volunteerhrs) {
		this.volunteerhrs = volunteerhrs;
	}
	public BigDecimal getOverallVolRingHrs() {
		return overallVolRingHrs;
	}
	public void setOverallVolRingHrs(BigDecimal overallVolRingHrs) {
		this.overallVolRingHrs = overallVolRingHrs;
	}
	public BigDecimal getLivesImpacted() {
		return livesImpacted;
	}
	public void setLivesImpacted(BigDecimal livesImpacted) {
		this.livesImpacted = livesImpacted;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPocId() {
		return pocId;
	}
	public void setPocId(String pocId) {
		this.pocId = pocId;
	}
	public String getPocName() {
		return pocName;
	}
	public void setPocName(String pocName) {
		this.pocName = pocName;
	}
	public int getPocContactNo() {
		return pocContactNo;
	}
	public void setPocContactNo(int pocContactNo) {
		this.pocContactNo = pocContactNo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventSummaryDetails other = (EventSummaryDetails) obj;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		return true;
	}
    
    
}

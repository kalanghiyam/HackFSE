package com.cts.fse.feedback.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class EventEmployeeInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
    private EventEmployeeIdentity eventEmployeeIdentity;
    
    private String associateName;
    
    private String bu;
	    
	@Column(nullable = false)
	private String baseLocation;
    
    @Column(nullable = false)
	private Date eventDate;
  
    private BigDecimal volunteerhrs;
    
    private BigDecimal travelhrs;
    
    private BigDecimal livesImpacted;
    
    private Date emailSendDate;
    
    private int emailCount;
    
	private String beneficiaryName;
	
	private Date loadDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name="eventId",referencedColumnName="eventId",
    insertable=false,updatable=false)})
    private EventSummaryDetails eventSummaryDetails;
	
	@Transient
	private String eventDateString;
	
	@Column(nullable = false)
	private String eventStatus;

	@Column(nullable = false)
	private String responded;
	
	public Date getLoadDate() {
		return loadDate;
	}
	public void setLoadDate(Date loadDate) {
		this.loadDate = loadDate;
	}
	
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public String getEventDateString() {
		return eventDateString;
	}
	public void setEventDateString(String eventDateString) {
		this.eventDateString = eventDateString;
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

    public EventSummaryDetails getEventSummaryDetails() {
		return eventSummaryDetails;
	}
	public void setEventSummaryDetails(EventSummaryDetails eventSummaryDetails) {
		this.eventSummaryDetails = eventSummaryDetails;
	}
	public String getResponded() {
		return responded;
	}
	public void setResponded(String responded) {
		this.responded = responded;
	}
	public int getEmailCount() {
		return emailCount;
	}
	public void setEmailCount(int emailCount) {
		this.emailCount = emailCount;
	}
	public Date getEmailSendDate() {
		return emailSendDate;
	}
	public void setEmailSendDate(Date emailSendDate) {
		this.emailSendDate = emailSendDate;
	}
    
    public EventEmployeeInfo() {
    	this.eventEmployeeIdentity = new EventEmployeeIdentity();
    }
    
	public EventEmployeeInfo(EventEmployeeIdentity eventEmployeeIdentity) {
		super();
		this.eventEmployeeIdentity = eventEmployeeIdentity;
	}
	public EventEmployeeIdentity getEventEmployeeIdentity() {
		return eventEmployeeIdentity;
	}
	public void setEventEmployeeIdentity(EventEmployeeIdentity eventEmployeeIdentity) {
		this.eventEmployeeIdentity = eventEmployeeIdentity;
	}
	public String getAssociateName() {
		return associateName;
	}
	public void setAssociateName(String associateName) {
		this.associateName = associateName;
	}
	
	public String getBu() {
		return bu;
	}
	public void setBu(String bu) {
		this.bu = bu;
	}
	public String getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	public BigDecimal getVolunteerhrs() {
		return volunteerhrs;
	}
	public void setVolunteerhrs(BigDecimal volunteerhrs) {
		this.volunteerhrs = volunteerhrs;
	}
	public BigDecimal getTravelhrs() {
		return travelhrs;
	}
	public void setTravelhrs(BigDecimal travelhrs) {
		this.travelhrs = travelhrs;
	}
	public BigDecimal getLivesImpacted() {
		return livesImpacted;
	}
	public void setLivesImpacted(BigDecimal livesImpacted) {
		this.livesImpacted = livesImpacted;
	}

	
	@Override
	public String toString() {
		return "EventEmployeeInfo [eventEmployeeIdentity=" + eventEmployeeIdentity + ", associateName=" + associateName
				+ ", bu=" + bu + ", eventStatus=" + eventStatus + ", volunteerhrs=" + volunteerhrs + ", travelhrs="
				+ travelhrs + ", livesImpacted=" + livesImpacted + "]";
	}
	
	
}

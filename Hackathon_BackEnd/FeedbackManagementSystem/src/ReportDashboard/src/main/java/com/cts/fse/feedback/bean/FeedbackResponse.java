package com.cts.fse.feedback.bean;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Entity
public class FeedbackResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FeedbackResponseIdentity eventFeedbackResponseIdentity;
	
    private String feedbackResponse;
    
    private int smileyCount;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name="feedbackId",referencedColumnName="id",
    insertable=false,updatable=false)})
    private FeedbackDetails feedbackDetails;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name="eventId",referencedColumnName="eventId",
    insertable=false,updatable=false),
    	@JoinColumn(name="associateId",referencedColumnName="associateId",
        insertable=false,updatable=false)})
    private EventEmployeeInfo eventEmployeeInfo;
	
	public FeedbackDetails getFeedbackDetails() {
		return feedbackDetails;
	}

	public void setFeedbackDetails(FeedbackDetails feedbackDetails) {
		this.feedbackDetails = feedbackDetails;
	}
	
	public int getSmileyCount() {
		return smileyCount;
	}
	public void setSmileyCount(int smileyCount) {
		this.smileyCount = smileyCount;
	}
	public String getFeedbackResponse() {
		return feedbackResponse;
	}
	public void setFeedbackResponse(String feedbackResponse) {
		this.feedbackResponse = feedbackResponse;
	}
	
	public FeedbackResponseIdentity getEventFeedbackResponseIdentity() {
		return eventFeedbackResponseIdentity;
	}
	public void setEventFeedbackResponseIdentity(FeedbackResponseIdentity eventFeedbackResponseIdentity) {
		this.eventFeedbackResponseIdentity = eventFeedbackResponseIdentity;
	}
    
}

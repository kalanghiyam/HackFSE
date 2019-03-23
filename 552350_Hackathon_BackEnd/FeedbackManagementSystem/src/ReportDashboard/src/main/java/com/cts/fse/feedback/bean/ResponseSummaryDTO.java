package com.cts.fse.feedback.bean;

public class ResponseSummaryDTO {
	private String event;
	private String feedbackResponse;
	private String status;
	
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getFeedbackResponse() {
		return feedbackResponse;
	}
	public void setFeedbackResponse(String feedbackResponse) {
		this.feedbackResponse = feedbackResponse;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}

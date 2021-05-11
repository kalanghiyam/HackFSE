package com.cts.fse.feedback.bean;

public class EventResponseDTO {

	private int id;
	
	private String feedbackResponse;
	
	private boolean selected;
	
	private String smileyValue;
	
	private String inputType;

	private String status; 
	
	
	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSmileyValue() {
		return smileyValue;
	}

	public void setSmileyValue(String smileyValue) {
		this.smileyValue = smileyValue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFeedbackResponse() {
		return feedbackResponse;
	}

	public void setFeedbackResponse(String feedbackResponse) {
		this.feedbackResponse = feedbackResponse;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return "EventResponseDTO [id=" + id + ", feedbackResponse=" + feedbackResponse + ", selected=" + selected
				+ ", smileyValue=" + smileyValue + ", inputType=" + inputType + ", status=" + status + "]";
	}

	
}

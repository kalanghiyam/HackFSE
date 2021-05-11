package com.cts.fse.feedback.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FeedbackDetails {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
	private String feedBackDesc;
	private String status;
	private String inputType;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFeedBackDesc() {
		return feedBackDesc;
	}
	public void setFeedBackDesc(String feedBackDesc) {
		this.feedBackDesc = feedBackDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	
}

package com.cts.fse.feedback.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class EmailTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String status;
	@Lob
	private String emailTemplate;
	
	private int emailNotification;
	
	private int notificationInterval;
	
	
	public int getNotificationInterval() {
		return notificationInterval;
	}
	public void setNotificationInterval(int notificationInterval) {
		this.notificationInterval = notificationInterval;
	}
	public int getEmailNotification() {
		return emailNotification;
	}
	public void setEmailNotification(int emailNotification) {
		this.emailNotification = emailNotification;
	}
	@Override
	public String toString() {
		return "EmailTemplate [status=" + status + ", emailTemplate=" + emailTemplate + "]";
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmailTemplate() {
		return emailTemplate;
	}
	public void setEmailTemplate(String emailTemplate) {
		this.emailTemplate = emailTemplate;
	}
	
}
